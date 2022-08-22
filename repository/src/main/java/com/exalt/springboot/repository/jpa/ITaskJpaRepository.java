package com.exalt.springboot.repository.jpa;

import com.exalt.springboot.repository.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskJpaRepository extends JpaRepository<TaskEntity,Integer> {
    @Query("select t from TaskEntity t where t.user.id = :userId")
    List<TaskEntity> findTasksByUserId(@Param("userId") int userId);

    @Query("select t from TaskEntity t where t.user.id = :userId")
    Page<TaskEntity> findTasksByUserIdWithPagination(@Param("userId") int userId, Pageable pageable);
}
