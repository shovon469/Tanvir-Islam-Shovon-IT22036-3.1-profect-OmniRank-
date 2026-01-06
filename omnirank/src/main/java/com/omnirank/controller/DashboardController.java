package com.omnirank.controller;
import com.omnirank.model.UnifiedProblem;
import com.omnirank.model.User;
import com.omnirank.repository.UserRepository;
import com.omnirank.service.AggregatorService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final UserRepository userRepository;
    private final AggregatorService aggregatorService;

    @GetMapping("/platform/{platformName}")
    public String viewPlatform(@PathVariable String platformName, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        User user = userRepository.findById(sessionUser.getId()).orElse(sessionUser);
        List<UnifiedProblem> allProblems = aggregatorService.getAllProblems(user);

        // 1. Filter for the specific platform (e.g., Codeforces)
        List<UnifiedProblem> filtered = allProblems.stream()
                .filter(p -> p.getPlatform().equalsIgnoreCase(platformName))
                .sorted((a, b) -> Long.compare(b.getSolvedTimeSeconds(), a.getSolvedTimeSeconds())) // Newest first
                .collect(Collectors.toList());

        // 2. Calculate Streak (Simple version)
        int streak = 0;
        Set<String> daysSolved = new HashSet<>();
        for (UnifiedProblem p : filtered) {
            String day = Instant.ofEpochSecond(p.getSolvedTimeSeconds()).toString().substring(0, 10); // YYYY-MM-DD
            daysSolved.add(day);
        }
        streak = daysSolved.size(); // Total unique days active (Simplified streak)

        // 3. AI Recommendation (Random logic for now)
        // In a real app, you would fetch UNsolved problems. Here we pick a random topic from solved ones.
        String recommendation = "Try a 1200 rated DP problem on Codeforces!";

        model.addAttribute("platform", platformName);
        model.addAttribute("problems", filtered);
        model.addAttribute("total", filtered.size());
        model.addAttribute("streak", streak);
        model.addAttribute("recommendation", recommendation);
        model.addAttribute("userName", user.getName());

        return "platform_view";
    }

    @GetMapping("/")
    public String root(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "home";

        User user = userRepository.findById(sessionUser.getId()).orElse(sessionUser);
        List<UnifiedProblem> problems = aggregatorService.getAllProblems(user);

        model.addAttribute("userName", user.getName());
        model.addAttribute("totalSolved", problems.size());
        model.addAttribute("cfCount", problems.stream().filter(p -> p.getPlatform().equals("Codeforces")).count());
        model.addAttribute("lcCount", problems.stream().filter(p -> p.getPlatform().equals("LeetCode")).count());
        model.addAttribute("acCount", problems.stream().filter(p -> p.getPlatform().equals("AtCoder")).count());
        model.addAttribute("ccCount", problems.stream().filter(p -> p.getPlatform().equals("CodeChef")).count());
        model.addAttribute("csesCount", problems.stream().filter(p -> p.getPlatform().equals("CSES")).count());

        return "dashboard";
    }
}