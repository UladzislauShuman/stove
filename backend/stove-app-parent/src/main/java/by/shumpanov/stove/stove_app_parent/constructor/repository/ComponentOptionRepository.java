package by.shumpanov.stove.stove_app_parent.constructor.repository;

import by.shumpanov.stove.stove_app_parent.constructor.model.ComponentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentOptionRepository extends JpaRepository<ComponentOption, Long> {
    List<ComponentOption> findByComponentId(Long componentId);
}
