package by.shumpanov.stove.stove_app_parent.constructor.repository;

import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    @Query("SELECT c FROM Configuration c " +
            "LEFT JOIN FETCH c.stoveType " +
            "LEFT JOIN FETCH c.author " +
            "LEFT JOIN FETCH c.choices ch " +
            "LEFT JOIN FETCH ch.option opt " +
            "LEFT JOIN FETCH opt.component " +
            "WHERE c.id = :id")
    Optional<Configuration> findByIdWithDetails(@Param("id") Long id);
}
