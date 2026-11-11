package org.sopt.meemong.domain.match.service;

import org.sopt.meemong.domain.designer.domain.Designer;
import org.sopt.meemong.domain.match.dto.response.GeneralPostListResponse;
import org.sopt.meemong.domain.post.domain.PostType;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;

@Service
public class GeneralPostService {

    private static final ZoneOffset KOREA_OFFSET = ZoneOffset.ofHours(9);

    private final PostRepository postRepository;

    public GeneralPostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public GeneralPostListResponse getGeneralPosts() {
        List<GeneralPostListResponse.Card> cards = postRepository
                .findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.GENERAL)
                .stream()
                .map(post -> {
                    Designer designer = post.getDesigner();
                    return new GeneralPostListResponse.Card(
                            post.getId(),
                            post.getTitle(),
                            designer.getName(),
                            designer.getProfileImageUrl(),
                            post.getPriceType(),
                            post.getDisplayPrice(),
                            designer.getRegion(),
                            post.getAvailableTimes(),
                            post.getTreatmentTypes(),
                            post.isLiked(),
                            post.getCreatedAt().atOffset(KOREA_OFFSET)
                    );
                })
                .toList();

        return new GeneralPostListResponse(cards);
    }
}
