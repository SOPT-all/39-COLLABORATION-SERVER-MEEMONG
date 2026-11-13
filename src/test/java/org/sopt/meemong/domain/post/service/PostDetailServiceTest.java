package org.sopt.meemong.domain.post.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.sopt.meemong.domain.designer.domain.Designer;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.domain.PostImage;
import org.sopt.meemong.domain.post.domain.ShootingRange;
import org.sopt.meemong.domain.post.dto.response.PostDetailResponse;
import org.sopt.meemong.domain.post.repository.PostImageRepository;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.sopt.meemong.domain.shop.domain.Shop;
import org.sopt.meemong.global.exception.BusinessException;
import org.sopt.meemong.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostDetailServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostImageRepository postImageRepository;
    @InjectMocks
    private PostDetailService postDetailService;

    @Test
    void getPostDetailShouldReturnDetail() {
        Post post = createMockPost();

        PostImage firstImage = mock(PostImage.class);
        PostImage secondImage = mock(PostImage.class);

        when(firstImage.getImageUrl())
                .thenReturn("https://example.com/post1.jpg");
        when(secondImage.getImageUrl())
                .thenReturn("https://example.com/post2.jpg");

        when(post.getShootingRanges())
                .thenReturn(List.of(ShootingRange.PHOTO));

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        when(postImageRepository.findByPost_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of(firstImage, secondImage));

        PostDetailResponse response = postDetailService.getPostDetail(1L);

        assertThat(response.postId()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("뭘 해야 할지 모르겠다면?");
        assertThat(response.shopName()).isEqualTo("아벨라움");
        assertThat(response.designerName()).isEqualTo("승환");

        assertThat(response.postImageUrls()).containsExactly(
                "https://example.com/post1.jpg",
                "https://example.com/post2.jpg"
        );

        assertThat(response.shootingRanges())
                .containsExactly(ShootingRange.PHOTO);

        assertThat(response.createdAt().getOffset())
                .isEqualTo(ZoneOffset.ofHours(9));

        verify(postRepository).findById(1L);
        verify(postImageRepository)
                .findByPost_IdOrderByDisplayOrderAsc(1L);

    }

    @Test
    void getPostDetailWithNoImagesShouldReturnEmptyList() {
        Post post = createMockPost();

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        when(postImageRepository.findByPost_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of());

        PostDetailResponse response = postDetailService.getPostDetail(1L);

        assertThat(response.postImageUrls()).isEmpty();
    }

    @Test
    void getPostDetailWithNullShootingRangesShouldReturnEmptyList() {
        Post post = createMockPost();

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        when(postImageRepository.findByPost_IdOrderByDisplayOrderAsc(1L))
                .thenReturn(List.of());

        PostDetailResponse response = postDetailService.getPostDetail(1L);

        assertThat(response.shootingRanges()).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1})
    void getPostDetailWithInvalidIdShouldThrowException(Long postId) {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> postDetailService.getPostDetail(postId)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.INVALID_POST_ID);

        verifyNoInteractions(postRepository, postImageRepository);
    }

    @Test
    void getPostDetailWhenPostNotFoundShouldThrowException() {
        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> postDetailService.getPostDetail(999L)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.POST_NOT_FOUND);

        verify(postRepository).findById(999L);
        verifyNoInteractions(postImageRepository);
    }

    private Post createMockPost() {
        Post post = mock(Post.class);
        Designer designer = mock(Designer.class);
        Shop shop = mock(Shop.class);

        when(post.getId()).thenReturn(1L);
        when(post.getTitle()).thenReturn("뭘 해야 할지 모르겠다면?");
        when(post.getDesigner()).thenReturn(designer);
        when(post.getCreatedAt()).thenReturn(
                LocalDateTime.of(2026, 11, 7, 16, 17)
        );

        when(designer.getShop()).thenReturn(shop);
        when(designer.getId()).thenReturn(1L);
        when(designer.getName()).thenReturn("승환");

        when(shop.getName()).thenReturn("아벨라움");

        return post;
    }
}
