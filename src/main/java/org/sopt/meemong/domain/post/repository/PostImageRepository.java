package org.sopt.meemong.domain.post.repository;

import org.sopt.meemong.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost_IdOrderByDisplayOrderAsc(Long postId);

    List<PostImage> findByPost_IdInOrderByDisplayOrderAscIdAsc(
            Collection<Long> postIds
    );
}
