package org.sopt.meemong.domain.match.controller;

import org.sopt.meemong.domain.match.dto.response.RecommendedPostListResponse;
import org.sopt.meemong.domain.match.service.RecommendedPostService;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class MatchController {

    private final RecommendedPostService recommendedPostService;

    public MatchController(RecommendedPostService recommendedPostService) {
        this.recommendedPostService = recommendedPostService;
    }

    @GetMapping("/recommended")
    public CommonResponse<RecommendedPostListResponse> getRecommendedPosts() {
        return CommonResponse.success(recommendedPostService.getRecommendedPosts());
    }
}
