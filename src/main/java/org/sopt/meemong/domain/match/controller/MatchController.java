package org.sopt.meemong.domain.match.controller;

import org.sopt.meemong.domain.match.dto.response.GeneralPostListResponse;
import org.sopt.meemong.domain.match.dto.response.RecommendedPostListResponse;
import org.sopt.meemong.domain.match.service.GeneralPostService;
import org.sopt.meemong.domain.match.service.RecommendedPostService;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class MatchController {

    private final RecommendedPostService recommendedPostService;
    private final GeneralPostService generalPostService;

    public MatchController(
            RecommendedPostService recommendedPostService,
            GeneralPostService generalPostService
    ) {
        this.recommendedPostService = recommendedPostService;
        this.generalPostService = generalPostService;
    }

    @GetMapping("/recommended")
    public CommonResponse<RecommendedPostListResponse> getRecommendedPosts() {
        return CommonResponse.success(recommendedPostService.getRecommendedPosts());
    }

    @GetMapping("/general")
    public CommonResponse<GeneralPostListResponse> getGeneralPosts() {
        return CommonResponse.success(generalPostService.getGeneralPosts());
    }
}
