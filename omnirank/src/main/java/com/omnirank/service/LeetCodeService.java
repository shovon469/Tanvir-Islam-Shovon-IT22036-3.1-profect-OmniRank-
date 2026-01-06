package com.omnirank.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnirank.model.UnifiedProblem;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class LeetCodeService {

    public List<UnifiedProblem> getProblems(String handle) {
        List<UnifiedProblem> problems = new ArrayList<>();
        String url = "https://leetcode.com/graphql";
        String query = "query getUserProfile($username: String!) { matchedUser(username: $username) { submitStats: submitStatsGlobal { acSubmissionNum { difficulty count } } } }";

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("query", query);
            body.put("variables", Map.of("username", handle));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(body), headers);

            String response = new RestTemplate().postForObject(url, entity, String.class);
            JsonNode stats = new ObjectMapper().readTree(response).path("data").path("matchedUser").path("submitStats").path("acSubmissionNum");

            if (stats.isArray()) {
                for (JsonNode stat : stats) {
                    String difficultyStr = stat.get("difficulty").asText();
                    int count = stat.get("count").asInt();

                    if (!"All".equals(difficultyStr)) {
                        for (int i = 0; i < count; i++) {
                            // FIX IS HERE: Added ", new ArrayList<>(), 0L"
                            problems.add(new UnifiedProblem(
                                    "LC-" + difficultyStr + "-" + i,
                                    "LeetCode",
                                    0,
                                    "",
                                    new ArrayList<>(), // Empty Tags
                                    0L                 // 0 Timestamp
                            ));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("LeetCode Error: " + e.getMessage());
        }
        return problems;
    }
}