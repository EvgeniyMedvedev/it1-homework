package org.it1.task.repo;

import org.it1.task.entity.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE task", nativeQuery = true)
    void truncate();
}
