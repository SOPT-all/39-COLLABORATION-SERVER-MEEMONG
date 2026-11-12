package org.sopt.meemong.domain.like.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.sopt.meemong.global.exception.BusinessException;
import org.sopt.meemong.global.exception.ErrorCode;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.sopt.meemong.domain.post.domain.PostFixture.likedPost;
import static org.sopt.meemong.domain.post.domain.PostFixture.unlikedPost;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostLikeService postLikeService;

    @Test
    void likePostShouldReturnTrue() {
        Post post = unlikedPost();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        boolean result = postLikeService.like(1L);

        assertThat(result).isTrue();
        assertThat(post.isLiked()).isTrue();
        verify(postRepository).findById(1L);
    }

    @Test
    void unlikePostShouldReturnFalse() {
        Post post = likedPost();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        boolean result = postLikeService.unlike(1L);

        assertThat(result).isFalse();
        assertThat(post.isLiked()).isFalse();
        verify(postRepository).findById(1L);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1})
    void likePostWithInvalidIdShouldThrowException(Long postId) {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> postLikeService.like(postId)
        );
        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.INVALID_POST_ID);
        verifyNoInteractions(postRepository);
    }

    @Test
    void likePostWhenPostNotFoundShouldThrowException() {
        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> postLikeService.like(999L)
        );
        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.POST_NOT_FOUND);
        verify(postRepository).findById(999L);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1})
    void unlikePostWithInvalidIdShouldThrowException(Long postId) {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> postLikeService.unlike(postId)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.INVALID_POST_ID);
        verifyNoInteractions(postRepository);
    }

    @Test
    void unlikePostWhenPostNotFoundShouldThrowException() {
        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> postLikeService.unlike(999L)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.POST_NOT_FOUND);
        verify(postRepository).findById(999L);
    }

    @Test
    void likePostShouldBeIdempotent() {
        Post post = unlikedPost();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        boolean firstResult = postLikeService.like(1L);
        boolean secondResult = postLikeService.like(1L);

        assertThat(firstResult).isTrue();
        assertThat(secondResult).isTrue();
        assertThat(post.isLiked()).isTrue();
    }

    @Test
    void unlikePostShouldBeIdempotent() {
        Post post = likedPost();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        boolean firstResult = postLikeService.unlike(1L);
        boolean secondResult = postLikeService.unlike(1L);

        assertThat(firstResult).isFalse();
        assertThat(secondResult).isFalse();
        assertThat(post.isLiked()).isFalse();
    }
}
