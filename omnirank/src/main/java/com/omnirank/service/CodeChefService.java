package com.omnirank.service;

import com.omnirank.model.UnifiedProblem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeChefService {
    public List<UnifiedProblem> getProblems(String handle) {
        List<UnifiedProblem> problems = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.codechef.com/users/" + handle)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

            doc.select(".rating-data-section.problems-solved article a").forEach(link -> {
                // FIX IS HERE: Added ", new ArrayList<>(), 0L"
                problems.add(new UnifiedProblem(
                        link.text(),
                        "CodeChef",
                        0,
                        "",
                        new ArrayList<>(),
                        0L
                ));
            });
        } catch (Exception e) {
            System.err.println("CodeChef Failed: " + e.getMessage());
        }
        return problems;
    }
}