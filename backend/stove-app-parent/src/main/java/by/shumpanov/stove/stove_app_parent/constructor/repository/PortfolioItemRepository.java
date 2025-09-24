package by.shumpanov.stove.stove_app_parent.constructor.repository;

import by.shumpanov.stove.stove_app_parent.constructor.model.PortfolioItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioItemRepository  extends JpaRepository<PortfolioItem, Long>,
        JpaSpecificationExecutor<PortfolioItem> {
    @Override
    @EntityGraph(attributePaths = {"configuration", "configuration.stoveType"})
    Page<PortfolioItem> findAll(Specification<PortfolioItem> spec, Pageable pageable);
}
