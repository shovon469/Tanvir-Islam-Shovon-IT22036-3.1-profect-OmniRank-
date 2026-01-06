package com.omnirank.service;
import com.omnirank.model.PlatformHandle;
import com.omnirank.model.UnifiedProblem;
import com.omnirank.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AggregatorService {
    private final CodeforcesService cfService;
    private final LeetCodeService lcService;
    private final AtCoderService acService;
    private final CodeChefService ccService;
    private final CsesService csesService;

    public List<UnifiedProblem> getAllProblems(User user) {
        List<UnifiedProblem> allProblems = new ArrayList<>();
        for (PlatformHandle handle : user.getHandles()) {
            String h = handle.getHandle();
            String p = handle.getPlatform();
            if ("Codeforces".equalsIgnoreCase(p)) allProblems.addAll(cfService.getProblems(h));
            else if ("LeetCode".equalsIgnoreCase(p)) allProblems.addAll(lcService.getProblems(h));
            else if ("AtCoder".equalsIgnoreCase(p)) allProblems.addAll(acService.getProblems(h));
            else if ("CodeChef".equalsIgnoreCase(p)) allProblems.addAll(ccService.getProblems(h));
            else if ("CSES".equalsIgnoreCase(p)) allProblems.addAll(csesService.getProblems(h));
        }
        return allProblems;
    }
}