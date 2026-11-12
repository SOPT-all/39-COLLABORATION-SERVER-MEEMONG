package org.sopt.meemong.domain.match.dto.response;

import org.sopt.meemong.domain.post.domain.AvailableTime;
import org.sopt.meemong.domain.post.domain.PriceType;
import org.sopt.meemong.domain.post.domain.TreatmentType;

import java.time.OffsetDateTime;
import java.util.List;

public record RecommendedPostListResponse(List<Card> posts) {

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
