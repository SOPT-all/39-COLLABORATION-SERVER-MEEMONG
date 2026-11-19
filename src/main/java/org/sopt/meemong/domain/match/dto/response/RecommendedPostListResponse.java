package org.sopt.meemong.domain.match.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.meemong.domain.post.domain.AvailableTime;
import org.sopt.meemong.domain.post.domain.PriceType;
import org.sopt.meemong.domain.post.domain.TreatmentType;

import java.time.OffsetDateTime;
import java.util.List;

public record RecommendedPostListResponse(List<Card> posts) {

    @Schema(name = "RecommendedPostCard")
    public record Card(
            Long postId,
            String title,
            String thumbnailUrl,
            PriceType priceType,
            Integer displayPrice,
            String region,
            List<AvailableTime> availableTimes,
            List<TreatmentType> treatmentTypes,
            boolean isLiked,
            OffsetDateTime createdAt
    ) {}
}
