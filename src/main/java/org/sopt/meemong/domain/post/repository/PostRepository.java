package org.sopt.meemong.domain.post.repository;

import org.sopt.meemong.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
