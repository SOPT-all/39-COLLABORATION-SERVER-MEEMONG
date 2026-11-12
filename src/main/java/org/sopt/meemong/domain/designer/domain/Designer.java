package org.sopt.meemong.domain.designer.domain;

import jakarta.persistence.*;
import org.sopt.meemong.domain.shop.domain.Shop;

@Entity
@Table(name = "designers")
public class Designer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "profile_image_url", nullable = false, length = 2048)
    private String profileImageUrl;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "completed_treatment_count", nullable = false)
    private int completedTreatmentCount;

    protected Designer() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Shop getShop() {
        return shop;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getRegion() {
        return region;
    }

    public int getCompletedTreatmentCount() {
        return completedTreatmentCount;
    }
}
