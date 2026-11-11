package org.sopt.meemong.domain.designer.service;

import org.sopt.meemong.domain.designer.domain.Designer;
import org.sopt.meemong.domain.designer.domain.PortfolioImage;
import org.sopt.meemong.domain.designer.dto.response.DesignerDetailResponse;
import org.sopt.meemong.domain.designer.repository.PortfolioImageRepository;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.sopt.meemong.domain.shop.domain.Shop;
import org.sopt.meemong.domain.shop.domain.ShopImage;
import org.sopt.meemong.domain.shop.repository.ShopImageRepository;
import org.sopt.meemong.global.exception.BusinessException;
import org.sopt.meemong.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DesignerDetailService {
    private final PostRepository postRepository;
    private final PortfolioImageRepository portfolioImageRepository;
    private final ShopImageRepository shopImageRepository;

    public DesignerDetailService(
            PostRepository postRepository,
            PortfolioImageRepository portfolioImageRepository,
            ShopImageRepository shopImageRepository
    ) {
        this.postRepository = postRepository;
        this.portfolioImageRepository = portfolioImageRepository;
        this.shopImageRepository = shopImageRepository;
    }

    public DesignerDetailResponse getDesignerDetail(Long postId) {
        if (postId == null || postId <= 0) {
            throw new BusinessException(ErrorCode.INVALID_POST_ID);
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        Designer designer = post.getDesigner();
        Shop shop = designer.getShop();

        List<String> portfolioImageUrls = portfolioImageRepository
                .findByDesigner_IdOrderByDisplayOrderAsc(designer.getId())
                .stream()
                .map(PortfolioImage::getImageUrl)
                .toList();

        List<String> shopImageUrls = shopImageRepository
                .findByShop_IdOrderByDisplayOrderAsc(shop.getId())
                .stream()
                .map(ShopImage::getImageUrl)
                .toList();

        return new DesignerDetailResponse(
                portfolioImageUrls,
                shop.getAddress(),
                shopImageUrls
        );
    }
}
