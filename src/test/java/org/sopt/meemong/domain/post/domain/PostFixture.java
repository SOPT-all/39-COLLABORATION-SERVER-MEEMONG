package org.sopt.meemong.domain.post.domain;

public final class PostFixture {

    private PostFixture() {
    }

    public static Post unlikedPost() {
        return new Post();
    }

    public static Post likedPost() {
        Post post = new Post();
        post.like();

        return post;
    }
}
