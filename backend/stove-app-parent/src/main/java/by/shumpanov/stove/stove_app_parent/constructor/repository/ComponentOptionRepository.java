package by.shumpanov.stove.stove_app_parent.constructor.repository;

import by.shumpanov.stove.stove_app_parent.constructor.model.ComponentOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentOptionRepository extends JpaRepository<ComponentOption, Long> {
    List<ComponentOption> findByComponentId(Long componentId);
}
