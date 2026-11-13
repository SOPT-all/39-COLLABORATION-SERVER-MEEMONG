package org.sopt.meemong.domain.designer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DesignerDetailServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private PortfolioImageRepository portfolioImageRepository;
    @Mock
    private ShopImageRepository shopImageRepository;
    @InjectMocks
    private DesignerDetailService designerDetailService;

    @Test
    void getDesignerDetailShouldReturnDetail() {
        mockPostWithDesignerAndShop();

        PortfolioImage portfolio1 = mock(PortfolioImage.class);
        PortfolioImage portfolio2 = mock(PortfolioImage.class);
        ShopImage shopImage1 = mock(ShopImage.class);
        ShopImage shopImage2 = mock(ShopImage.class);

        when(portfolio1.getImageUrl())
                .thenReturn("https://example.com/portfolio1.jpg");
        when(portfolio2.getImageUrl())
                .thenReturn("https://example.com/portfolio2.jpg");
        when(shopImage1.getImageUrl())
                .thenReturn("https://example.com/shop1.jpg");
        when(shopImage2.getImageUrl())
                .thenReturn("https://example.com/shop2.jpg");

        when(portfolioImageRepository
                .findByDesigner_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of(portfolio1, portfolio2));

        when(shopImageRepository
                .findByShop_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of(shopImage1, shopImage2));

        DesignerDetailResponse response =
                designerDetailService.getDesignerDetail(1L);

        assertThat(response.portfolioImageUrls()).containsExactly(
                "https://example.com/portfolio1.jpg",
                "https://example.com/portfolio2.jpg"
        );

        assertThat(response.shopAddress())
                .isEqualTo("경기 고양시 덕성로 123");

        assertThat(response.shopImageUrls()).containsExactly(
                "https://example.com/shop1.jpg",
                "https://example.com/shop2.jpg"
        );

        verify(postRepository).findById(1L);
        verify(portfolioImageRepository)
                .findByDesigner_IdOrderByDisplayOrderAsc(1L);
        verify(shopImageRepository)
                .findByShop_IdOrderByDisplayOrderAsc(1L);
    }

    @Test
    void getDesignerDetailWithNoPortfolioImagesShouldReturnEmptyList() {
        mockPostWithDesignerAndShop();

        when(portfolioImageRepository
                .findByDesigner_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of());

        DesignerDetailResponse response =
                designerDetailService.getDesignerDetail(1L);

        assertThat(response.portfolioImageUrls()).isEmpty();
    }

    @Test
    void getDesignerDetailWithNoShopImagesShouldReturnEmptyList() {
        mockPostWithDesignerAndShop();

        when(shopImageRepository
                .findByShop_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of());

        DesignerDetailResponse response =
                designerDetailService.getDesignerDetail(1L);

        assertThat(response.shopImageUrls()).isEmpty();
    }

    @Test
    void getDesignerDetailWithNoImagesShouldReturnEmptyLists() {
        mockPostWithDesignerAndShop();

        DesignerDetailResponse response =
                designerDetailService.getDesignerDetail(1L);

        assertThat(response.portfolioImageUrls()).isEmpty();
        assertThat(response.shopImageUrls()).isEmpty();
        assertThat(response.shopAddress())
                .isEqualTo("경기 고양시 덕성로 123");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1})
    void getDesignerDetailWithInvalidIdShouldThrowException(Long postId) {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> designerDetailService.getDesignerDetail(postId)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.INVALID_POST_ID);

        verifyNoInteractions(
                postRepository,
                portfolioImageRepository,
                shopImageRepository
        );
    }

    @Test
    void getDesignerDetailWhenPostNotFoundShouldThrowException() {
        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> designerDetailService.getDesignerDetail(999L)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.POST_NOT_FOUND);

        verify(postRepository).findById(999L);
        verifyNoInteractions(
                portfolioImageRepository,
                shopImageRepository
        );
    }

    private void mockPostWithDesignerAndShop() {
        Post post = mock(Post.class);
        Designer designer = mock(Designer.class);
        Shop shop = mock(Shop.class);

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        when(post.getDesigner()).thenReturn(designer);
        when(designer.getShop()).thenReturn(shop);
        when(designer.getId()).thenReturn(1L);

        when(shop.getId()).thenReturn(1L);
        when(shop.getAddress())
                .thenReturn("경기 고양시 덕성로 123");
    }
}
