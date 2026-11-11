package org.sopt.meemong.domain.post.controller;

import org.sopt.meemong.domain.post.dto.response.PostDetailResponse;
import org.sopt.meemong.domain.post.service.PostDetailService;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostDetailController {
    private final PostDetailService postDetailService;

    public PostDetailController(PostDetailService postDetailService) {
        this.postDetailService = postDetailService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostDetailResponse>> getPostDetail(@PathVariable Long postId) {
        PostDetailResponse response = postDetailService.getPostDetail(postId);

        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
