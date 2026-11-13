package org.sopt.meemong.domain.shop.repository;

import org.sopt.meemong.domain.shop.domain.ShopImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopImageRepository extends JpaRepository<ShopImage, Long> {
    List<ShopImage> findByShop_IdOrderByDisplayOrderAsc(Long shopId);
}
