package com.omnirank.controller;

import com.omnirank.service.CsesService;
import com.omnirank.model.UnifiedProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final CsesService csesService;

    // Go to: http://localhost:8080/test-cses?id=300759
    @GetMapping("/test-cses")
    public String testCses(@RequestParam String id) {
        System.out.println("Testing CSES for ID: " + id);

        List<UnifiedProblem> problems = csesService.getProblems(id);

        if (problems.isEmpty()) {
            return "Scraping Failed! Found 0 problems. <br> Check IntelliJ Console for red error messages.";
        }

        return "SUCCESS! Found " + problems.size() + " solved tasks. <br>" +
                "First task detected: " + problems.get(0).getTitle();
    }
}