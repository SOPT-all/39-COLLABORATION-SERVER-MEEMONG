package org.sopt.meemong.domain.post.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.sopt.meemong.domain.designer.domain.Designer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "designer_id", nullable = false)
    private Designer designer;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type", nullable = false)
    private PostType postType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "treatment_types", nullable = false, columnDefinition = "json")
    private List<TreatmentType> treatmentTypes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", nullable = false)
    private PriceType priceType;

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "display_price")
    private Integer displayPrice;

    @Column(name = "discount_rate", precision = 5, scale = 2)
    private BigDecimal discountRate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "available_times", nullable = false, columnDefinition = "json")
    private List<AvailableTime> availableTimes = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "signatures", nullable = false, columnDefinition = "json")
    private List<TreatmentType> signatures = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "shooting_ranges", columnDefinition = "json")
    private List<ShootingRange> shootingRanges = new ArrayList<>();

    @Column(name = "is_liked", nullable = false)
    private boolean isLiked;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Post() {}

    public Long getId() {
        return id;
    }

    public Designer getDesigner() {
        return designer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public PostType getPostType() {
        return postType;
    }

    public List<TreatmentType> getTreatmentTypes() {
        return treatmentTypes;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public Integer getDisplayPrice() {
        return displayPrice;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public List<TreatmentType> getSignatures() {
        return signatures;
    }

    public List<ShootingRange> getShootingRanges() {
        return shootingRanges;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void like() {
        this.isLiked = true;
    }

    public void unlike() {
        this.isLiked = false;
    }
}
