package org.sopt.meemong.domain.match.service;

import org.sopt.meemong.domain.match.dto.response.RecommendedPostListResponse;
import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.domain.PostImage;
import org.sopt.meemong.domain.post.domain.PostType;
import org.sopt.meemong.domain.post.repository.PostImageRepository;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendedPostService {

    private static final ZoneOffset KOREA_OFFSET = ZoneOffset.ofHours(9);

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    public RecommendedPostService(
            PostRepository postRepository,
            PostImageRepository postImageRepository
    ) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
    }

    @Transactional(readOnly = true)
    public RecommendedPostListResponse getRecommendedPosts() {
        List<Post> posts = postRepository.findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType.RECOMMENDED);

        if (posts.isEmpty()) {
            return new RecommendedPostListResponse(List.of());
        }

        List<Long> postIds = posts.stream().map(Post::getId).toList();
        Map<Long, String> thumbnailUrls = new HashMap<>();

        for (PostImage image : postImageRepository.findByPost_IdInOrderByDisplayOrderAscIdAsc(postIds)) {
            thumbnailUrls.putIfAbsent(image.getPost().getId(), image.getImageUrl());
        }

        List<RecommendedPostListResponse.Card> cards = posts.stream()
                .map(post -> new RecommendedPostListResponse.Card(
                        post.getId(),
                        post.getTitle(),
                        thumbnailUrls.get(post.getId()),
                        post.getPriceType(),
                        post.getDisplayPrice(),
                        post.getDesigner().getRegion(),
                        post.getAvailableTimes(),
                        post.getTreatmentTypes(),
                        post.isLiked(),
                        post.getCreatedAt().atOffset(KOREA_OFFSET)
                ))
                .toList();

        return new RecommendedPostListResponse(cards);
    }
}
