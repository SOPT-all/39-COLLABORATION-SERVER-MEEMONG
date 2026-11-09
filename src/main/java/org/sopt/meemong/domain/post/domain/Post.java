package org.sopt.meemong.domain.post.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isLiked;

    protected Post() {}

    public void like() {
        this.isLiked = true;
    }

    public void unlike() {
        this.isLiked = false;
    }

    public boolean isLiked() {
        return isLiked;
    }
}
