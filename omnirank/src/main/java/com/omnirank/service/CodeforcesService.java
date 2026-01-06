package com.omnirank.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnirank.model.UnifiedProblem;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeforcesService {
    public List<UnifiedProblem> getProblems(String handle) {
        List<UnifiedProblem> problems = new ArrayList<>();
        String url = "https://codeforces.com/api/user.status?handle=" + handle;
        try {
            String json = new RestTemplate().getForObject(url, String.class);
            JsonNode result = new ObjectMapper().readTree(json).get("result");
            if (result != null) {
                for (JsonNode sub : result) {
                    if ("OK".equals(sub.get("verdict").asText())) {
                        JsonNode p = sub.get("problem");

                        // 1. Extract Tags
                        List<String> tags = new ArrayList<>();
                        if (p.has("tags")) {
                            for (JsonNode tag : p.get("tags")) tags.add(tag.asText());
                        }

                        // 2. Extract Date (for streak)
                        long time = sub.get("creationTimeSeconds").asLong();

                        problems.add(new UnifiedProblem(
                                p.get("name").asText(),
                                "Codeforces",
                                p.has("rating") ? p.get("rating").asInt() : 0,
                                "https://codeforces.com/contest/" + p.get("contestId") + "/problem/" + p.get("index"),
                                tags,
                                time
                        ));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return problems;
    }
}


//package com.omnirank.service;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.omnirank.model.UnifiedProblem;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CodeforcesService {
//    public List<UnifiedProblem> getProblems(String handle) {
//        List<UnifiedProblem> problems = new ArrayList<>();
//        String url = "https://codeforces.com/api/user.status?handle=" + handle;
//        try {
//            String json = new RestTemplate().getForObject(url, String.class);
//            JsonNode result = new ObjectMapper().readTree(json).get("result");
//            if (result != null) {
//                for (JsonNode sub : result) {
//                    if ("OK".equals(sub.get("verdict").asText())) {
//                        JsonNode p = sub.get("problem");
//                        problems.add(new UnifiedProblem(p.get("name").asText(), "Codeforces", p.has("rating") ? p.get("rating").asInt() : 0, ""));
//                    }
//                }
//            }
//        } catch (Exception e) {}
//        return problems;
//    }
//}