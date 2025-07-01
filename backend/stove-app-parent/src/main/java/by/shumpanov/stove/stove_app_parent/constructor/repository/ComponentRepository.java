package by.shumpanov.stove.stove_app_parent.constructor.repository;

import by.shumpanov.stove.stove_app_parent.constructor.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByStoveTypeId(Long stoveTypeId);
}
