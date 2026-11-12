package org.sopt.meemong.domain.designer.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "portfolio_images")
public class PortfolioImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "designer_id", nullable = false)
    private Designer designer;

    @Column(name = "image_url", nullable = false, length = 2048)
    private String imageUrl;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    protected PortfolioImage() {}

    public Long getId() {
        return id;
    }

    public Designer getDesigner() {
        return designer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }
}
