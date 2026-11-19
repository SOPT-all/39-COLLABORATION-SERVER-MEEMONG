package org.sopt.meemong.domain.match.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.meemong.domain.post.domain.AvailableTime;
import org.sopt.meemong.domain.post.domain.PriceType;
import org.sopt.meemong.domain.post.domain.TreatmentType;

import java.time.OffsetDateTime;
import java.util.List;

public record GeneralPostListResponse(List<Card> posts) {

    @Schema(name = "GeneralPostCard")
    public record Card(
            Long postId,
            String title,
            String designerName,
            String designerProfileImageUrl,
            PriceType priceType,
            Integer displayPrice,
            String region,
            List<AvailableTime> availableTimes,
            List<TreatmentType> treatmentTypes,
            boolean isLiked,
            OffsetDateTime createdAt
    ) {}
}
