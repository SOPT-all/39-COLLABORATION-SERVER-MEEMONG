package org.sopt.meemong.domain.designer.repository;

import org.sopt.meemong.domain.designer.domain.PortfolioImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioImageRepository extends JpaRepository<PortfolioImage, Long> {
    List<PortfolioImage> findByDesigner_IdOrderByDisplayOrderAsc(Long designerId);
}
