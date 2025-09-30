package org.springclass.onlinebankingsystem.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<Entity> extends JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {
    Optional<Entity> findByIdAndStatusTrue(Long id);
    Optional<List<Entity>> findAllByStatusTrueOrderByIdAsc();
    Optional<List<Entity>> findAllByStatusTrueOrderByIdDesc();
}
