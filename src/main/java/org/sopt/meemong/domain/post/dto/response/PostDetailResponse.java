package org.sopt.meemong.domain.post.dto.response;

import org.sopt.meemong.domain.post.domain.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record PostDetailResponse(
        Long postId,
        String title,
        List<String> postImageUrls,
        boolean isLiked,
        PriceType priceType,
        BigDecimal discountRate,
        Integer originalPrice,
        Integer displayPrice,
        List<TreatmentType> treatmentTypes,
        List<TreatmentType> signatures,
        List<ShootingRange> shootingRanges,
        List<AvailableTime> availableTimes,
        String shopName,
        Long designerId,
        String designerName,
        String designerProfileImageUrl,
        String region,
        int completedTreatmentCount,
        String content,
        OffsetDateTime createdAt
) {
}
