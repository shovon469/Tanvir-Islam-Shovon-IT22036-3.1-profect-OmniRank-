package com.omnirank.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnirank.model.UnifiedProblem;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtCoderService {
    public List<UnifiedProblem> getProblems(String handle) {
        List<UnifiedProblem> problems = new ArrayList<>();
        String url = "https://kenkoooo.com/atcoder/atcoder-api/v3/user/submissions?user=" + handle + "&from_second=0";
        try {
            String json = new RestTemplate().getForObject(url, String.class);
            JsonNode result = new ObjectMapper().readTree(json);
            if (result.isArray()) {
                for (JsonNode sub : result) {
                    if ("AC".equals(sub.get("result").asText())) {
                        // FIX IS HERE: Added ", new ArrayList<>(), 0L"
                        problems.add(new UnifiedProblem(
                                sub.get("problem_id").asText(),
                                "AtCoder",
                                0,
                                "",
                                new ArrayList<>(),
                                0L
                        ));
                    }
                }
            }
        } catch (Exception e) {}
        return problems;
    }
}