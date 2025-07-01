package by.shumpanov.stove.stove_app_parent.constructor.repository;

import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> { }
