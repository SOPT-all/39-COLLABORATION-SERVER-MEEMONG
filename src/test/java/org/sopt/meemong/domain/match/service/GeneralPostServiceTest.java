package org.sopt.meemong.domain.match.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.meemong.domain.designer.domain.Designer;
import org.sopt.meemong.domain.match.dto.response.GeneralPostListResponse;
import org.sopt.meemong.domain.post.domain.AvailableTime;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.domain.PostType;
import org.sopt.meemong.domain.post.domain.PriceType;
import org.sopt.meemong.domain.post.domain.TreatmentType;
import org.sopt.meemong.domain.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneralPostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GeneralPostService generalPostService;

    @Test
    void getGeneralPostsShouldReturnPostWithDesignerInformation() {
        Post post = mock(Post.class);
        Designer designer = mock(Designer.class);
        String profileImageUrl = "https://example.com/designer.png";

        when(postRepository.findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.GENERAL))
                .thenReturn(List.of(post));
        when(post.getId()).thenReturn(8L);
        when(post.getTitle()).thenReturn("레이어드 시술합니다!");
        when(post.getDesigner()).thenReturn(designer);
        when(designer.getName()).thenReturn("아크미서하");
        when(designer.getProfileImageUrl()).thenReturn(profileImageUrl);
        when(designer.getRegion()).thenReturn("서울 강남구");
        when(post.getPriceType()).thenReturn(PriceType.FREE);
        when(post.getDisplayPrice()).thenReturn(null);
        when(post.getAvailableTimes()).thenReturn(List.of(AvailableTime.WEEKDAY_MORNING));
        when(post.getTreatmentTypes()).thenReturn(List.of(TreatmentType.CUT));
        when(post.isLiked()).thenReturn(true);
        when(post.getCreatedAt()).thenReturn(LocalDateTime.of(2026, 11, 26, 18, 0));

        GeneralPostListResponse response = generalPostService.getGeneralPosts();

        assertThat(response.posts()).containsExactly(new GeneralPostListResponse.Card(
                8L,
                "레이어드 시술합니다!",
                "아크미서하",
                profileImageUrl,
                PriceType.FREE,
                null,
                "서울 강남구",
                List.of(AvailableTime.WEEKDAY_MORNING),
                List.of(TreatmentType.CUT),
                true,
                OffsetDateTime.parse("2026-11-26T18:00:00+09:00")
        ));
        verify(postRepository).findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.GENERAL);
    }

    @Test
    void getGeneralPostsWhenNoPostsExistShouldReturnEmptyList() {
        when(postRepository.findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.GENERAL))
                .thenReturn(List.of());

        GeneralPostListResponse response = generalPostService.getGeneralPosts();

        assertThat(response.posts()).isEmpty();
    }
}
