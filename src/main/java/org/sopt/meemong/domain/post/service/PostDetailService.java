package org.sopt.meemong.domain.post.service;

import org.sopt.meemong.domain.designer.domain.Designer;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.domain.PostImage;
import org.sopt.meemong.domain.post.dto.response.PostDetailResponse;
import org.sopt.meemong.domain.post.repository.PostImageRepository;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.sopt.meemong.domain.shop.domain.Shop;
import org.sopt.meemong.global.exception.BusinessException;
import org.sopt.meemong.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostDetailService {
    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    public PostDetailService(PostRepository postRepository, PostImageRepository postImageRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
    }

    public PostDetailResponse getPostDetail(Long postId) {
        if (postId == null || postId <= 0) {
            throw new BusinessException(ErrorCode.INVALID_POST_ID);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        Designer designer = post.getDesigner();
        Shop shop = designer.getShop();

        List<String> postImageUrls = postImageRepository
                .findByPost_IdOrderByDisplayOrderAsc(postId)
                .stream()
                .map(PostImage::getImageUrl)
                .toList();

        OffsetDateTime createdAt = post.getCreatedAt()
                .atZone(KOREA_ZONE)
                .toOffsetDateTime();

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                postImageUrls,
                post.isLiked(),
                post.getPriceType(),
                post.getDiscountRate(),
                post.getOriginalPrice(),
                post.getDisplayPrice(),
                post.getTreatmentTypes(),
                post.getSignatures(),
                post.getShootingRanges() == null ? List.of() : post.getShootingRanges(),
                post.getAvailableTimes(),
                shop.getName(),
                designer.getId(),
                designer.getName(),
                designer.getProfileImageUrl(),
                designer.getRegion(),
                designer.getCompletedTreatmentCount(),
                post.getContent(),
                createdAt
        );
    }
}
