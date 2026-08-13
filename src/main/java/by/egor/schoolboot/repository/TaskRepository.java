package by.egor.schoolboot.repository;

import by.egor.schoolboot.entity.TaskEntity;
import by.egor.schoolboot.entity.TaskPriority;
import by.egor.schoolboot.entity.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("""
           select count(t) from TaskEntity t 
                where t.assignedUserId = :userId 
                and t.status = :taskStatus
           """)
    long countTasks(@Param("userId") Long userId, @Param("taskStatus") TaskStatus taskStatus);

    @Query("""
           select t from TaskEntity t
                where (:creatorId is null or t.creatorId = :creatorId)
                and (:assignedUserId is null or t.assignedUserId = :assignedUserId)
                and (:status is null or t.status = :status)
                and (:priority is null or t.priority = :priority)
           """)
    List<TaskEntity> searchAllByFilter(
            @Param("creatorId") Long creatorId,
            @Param("assignedUserId") Long assignedUserId,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable
    );
}