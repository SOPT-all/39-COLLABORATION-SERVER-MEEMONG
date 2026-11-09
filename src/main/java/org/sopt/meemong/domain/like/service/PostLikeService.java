package org.sopt.meemong.domain.like.service;

import org.sopt.meemong.domain.post.domain.Post;
import org.sopt.meemong.domain.post.repository.PostRepository;
import org.sopt.meemong.global.exception.BusinessException;
import org.sopt.meemong.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {
    private final PostRepository postRepository;

    public PostLikeService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public boolean like(Long postId) {
        validateId(postId);
        Post post = getPost(postId);
        post.like();

        return post.isLiked();
    }

    @Transactional
    public boolean unlike(Long postId) {
        validateId(postId);
        Post post = getPost(postId);
        post.unlike();

        return post.isLiked();
    }

    private void validateId(Long postId) {
        if (postId == null || postId <= 0) {
            throw new BusinessException(ErrorCode.INVALID_POST_ID);
        }
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }
}
