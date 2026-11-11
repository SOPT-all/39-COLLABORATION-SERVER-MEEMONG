package org.sopt.meemong.domain.designer.controller;

import org.sopt.meemong.domain.designer.dto.response.DesignerDetailResponse;
import org.sopt.meemong.domain.designer.service.DesignerDetailService;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class DesignerDetailController {
    private final DesignerDetailService designerDetailService;

    public DesignerDetailController(DesignerDetailService designerDetailService) {
        this.designerDetailService = designerDetailService;
    }

    @GetMapping("/{postId}/designer")
    public ResponseEntity<CommonResponse<DesignerDetailResponse>> getDesignerDetail(@PathVariable Long postId) {
        DesignerDetailResponse response = designerDetailService.getDesignerDetail(postId);

        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
