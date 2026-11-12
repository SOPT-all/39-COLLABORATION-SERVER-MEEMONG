package org.sopt.meemong.domain.like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostLikeResponse(
        @Schema(
                description = "찜 등록 여부",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        boolean isLiked
) {
}
