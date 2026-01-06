package com.omnirank.service;

import com.omnirank.model.UnifiedProblem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsesService {
    public List<UnifiedProblem> getProblems(String id) {
        List<UnifiedProblem> problems = new ArrayList<>();
        String url = "https://cses.fi/user/" + id;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(5000)
                    .ignoreHttpErrors(true)
                    .execute();

            if (response.statusCode() == 200) {
                Document doc = response.parse();
                Elements solvedTasks = doc.select(".task-score.full");
                if (solvedTasks.isEmpty()) solvedTasks = doc.select("a.task-score.icon.full");

                solvedTasks.forEach(element -> {
                    String title = element.attr("title");
                    if (title.isEmpty()) title = "CSES Task";

                    // FIX IS HERE: Added ", new ArrayList<>(), 0L"
                    problems.add(new UnifiedProblem(
                            title,
                            "CSES",
                            0,
                            "",
                            new ArrayList<>(),
                            0L
                    ));
                });
            }
        } catch (Exception e) {
            System.err.println("CSES Failed: " + e.getMessage());
        }
        return problems;
    }
}