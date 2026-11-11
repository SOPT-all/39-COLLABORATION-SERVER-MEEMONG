package org.sopt.meemong.domain.designer.dto.response;

import java.util.List;

public record DesignerDetailResponse(
        List<String> portfolioImageUrls,
        String shopAddress,
        List<String> shopImageUrls
) {
}
