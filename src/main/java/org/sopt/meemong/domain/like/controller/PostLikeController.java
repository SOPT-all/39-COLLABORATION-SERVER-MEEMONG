package org.sopt.meemong.domain.like.controller;

import org.sopt.meemong.domain.like.dto.response.PostLikeResponse;
import org.sopt.meemong.domain.like.service.PostLikeService;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PutMapping("/{postId}/likes")
    public ResponseEntity<CommonResponse<PostLikeResponse>> like(
            @PathVariable Long postId
    ) {
        boolean isLiked = postLikeService.like(postId);
        PostLikeResponse response = new PostLikeResponse(isLiked);

        return ResponseEntity.ok(
                CommonResponse.success(response)
        );
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<CommonResponse<PostLikeResponse>> unlike(
            @PathVariable Long postId
    ) {
        boolean isLiked = postLikeService.unlike(postId);
        PostLikeResponse response = new PostLikeResponse(isLiked);

        return ResponseEntity.ok(
                CommonResponse.success(response)
        );
    }
}
