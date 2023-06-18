package bitlab.trello.service;

import bitlab.trello.model.Tasks;
import bitlab.trello.repo.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Tasks> getTasksByFolderId(Long id) {
        List<Tasks> tasksList = taskRepository.findTasksByFolder_IdOrderById(id);
        return tasksList;
    }

    public Tasks findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void addTask(Tasks task) {
        taskRepository.save(task);
    }

    public void saveTask(Tasks task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
