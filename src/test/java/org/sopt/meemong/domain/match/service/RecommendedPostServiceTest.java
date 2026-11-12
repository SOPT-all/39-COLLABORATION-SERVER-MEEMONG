package org.sopt.meemong.domain.match.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.meemong.domain.designer.domain.Designer;
import org.sopt.meemong.domain.match.dto.response.RecommendedPostListResponse;
import org.sopt.meemong.domain.post.domain.AvailableTime;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.domain.PostImage;
import org.sopt.meemong.domain.post.domain.PostType;
import org.sopt.meemong.domain.post.domain.PriceType;
import org.sopt.meemong.domain.post.domain.TreatmentType;
import org.sopt.meemong.domain.post.repository.PostImageRepository;
import org.sopt.meemong.domain.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendedPostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostImageRepository postImageRepository;

    @InjectMocks
    private RecommendedPostService recommendedPostService;

    @Test
    void getRecommendedPostsShouldUseFirstImageAsThumbnail() {
        Post post = mock(Post.class);
        Designer designer = mock(Designer.class);
        PostImage firstImage = mock(PostImage.class);
        PostImage secondImage = mock(PostImage.class);
        String thumbnailUrl = "https://example.com/first.jpg";

        when(postRepository.findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.RECOMMENDED))
                .thenReturn(List.of(post));
        when(postImageRepository.findByPost_IdInOrderByDisplayOrderAscIdAsc(List.of(4L)))
                .thenReturn(List.of(firstImage, secondImage));
        when(firstImage.getPost()).thenReturn(post);
        when(firstImage.getImageUrl()).thenReturn(thumbnailUrl);
        when(secondImage.getPost()).thenReturn(post);
        when(secondImage.getImageUrl()).thenReturn("https://example.com/second.jpg");

        when(post.getId()).thenReturn(4L);
        when(post.getTitle()).thenReturn("C컬, S컬펌 모델 받고 있습니다!💛");
        when(post.getDesigner()).thenReturn(designer);
        when(designer.getRegion()).thenReturn("서울 서초구");
        when(post.getPriceType()).thenReturn(PriceType.NEGOTIABLE);
        when(post.getDisplayPrice()).thenReturn(15000);
        when(post.getAvailableTimes()).thenReturn(List.of(AvailableTime.WEEKDAY_MORNING));
        when(post.getTreatmentTypes()).thenReturn(List.of(TreatmentType.CUT, TreatmentType.PERM));
        when(post.isLiked()).thenReturn(true);
        when(post.getCreatedAt()).thenReturn(LocalDateTime.of(2026, 11, 26, 21, 10));

        RecommendedPostListResponse response = recommendedPostService.getRecommendedPosts();

        assertThat(response.posts()).containsExactly(new RecommendedPostListResponse.Card(
                4L,
                "C컬, S컬펌 모델 받고 있습니다!💛",
                thumbnailUrl,
                PriceType.NEGOTIABLE,
                15000,
                "서울 서초구",
                List.of(AvailableTime.WEEKDAY_MORNING),
                List.of(TreatmentType.CUT, TreatmentType.PERM),
                true,
                OffsetDateTime.parse("2026-11-26T21:10:00+09:00")
        ));
    }

    @Test
    void getRecommendedPostsWhenNoPostsExistShouldReturnEmptyList() {
        when(postRepository.findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.RECOMMENDED))
                .thenReturn(List.of());

        RecommendedPostListResponse response = recommendedPostService.getRecommendedPosts();

        assertThat(response.posts()).isEmpty();
        verifyNoInteractions(postImageRepository);
    }
}
