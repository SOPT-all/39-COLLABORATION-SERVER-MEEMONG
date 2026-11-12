package org.sopt.meemong.domain.shop.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "shop_images")
public class ShopImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "image_url", nullable = false, length = 2048)
    private String imageUrl;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    protected ShopImage() {}

    public Long getId() {
        return id;
    }

    public Shop getShop() {
        return shop;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }
}
