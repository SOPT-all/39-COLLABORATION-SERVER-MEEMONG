package org.sopt.meemong.domain.post.repository;

import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.domain.PostType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "designer")
    List<Post> findTop4ByPostTypeOrderByCreatedAtDescIdDesc(PostType postType);
}
