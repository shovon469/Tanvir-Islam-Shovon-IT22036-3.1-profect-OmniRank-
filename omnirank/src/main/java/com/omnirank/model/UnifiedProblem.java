package com.omnirank.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifiedProblem {
    private String title;
    private String platform;
    private int difficulty; // Rating for CF, 1/2/3 for LC
    private String url;
    private List<String> tags; // <--- NEW: Stores "DP", "Math"
    private long solvedTimeSeconds; // <--- NEW: For Streak Calculation
}