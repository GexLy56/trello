package bitlab.trello.service;

import bitlab.trello.model.TaskCategories;
import bitlab.trello.repo.TaskCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    public List<TaskCategories> getCategories() {
        List<TaskCategories> taskCategoriesList = taskCategoryRepository.findAllByOrderByName();
        return taskCategoriesList;
    }

    public TaskCategories findById(Long id) {
        return taskCategoryRepository.findById(id).orElse(null);
    }

    public void addCategory(TaskCategories category) {
        taskCategoryRepository.save(category);
    }

    public void editCategory(TaskCategories category) {
        taskCategoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        taskCategoryRepository.deleteById(id);
    }
}
