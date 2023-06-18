package bitlab.trello.repo;

import bitlab.trello.model.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategories, Long> {
    List<TaskCategories> findAllByOrderByName();
}
