package bitlab.trello.service;

import bitlab.trello.model.Folders;
import bitlab.trello.model.TaskCategories;
import bitlab.trello.model.Tasks;
import bitlab.trello.repo.FolderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final TaskCategoryService taskCategoryService;
    private final TaskService taskService;
    private final CommentService commentService;
    public List<Folders> getFolders() {
        List<Folders> foldersList = folderRepository.findAll();
        return foldersList;
    }

    public void addFolder(Folders folder) {
        folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(Long id) {
        List<Tasks> tasks = taskService.getTasksByFolderId(id);
        for (Tasks task : tasks) {
            commentService.deleteCommentByTaskId(task.getId());
            taskService.deleteTask(task.getId());
        }
        folderRepository.deleteById(id);
    }

    public void saveFolder(Folders folder) {
        folderRepository.save(folder);
    }
    public Folders findById(Long id) {
        return folderRepository.findById(id).orElse(null);
    }

    public void addCategory(Long f_id, Long c_id) {
        Folders folder = findById(f_id);
        TaskCategories category = taskCategoryService.findById(c_id);

        if (folder.getCategories()!=null && folder.getCategories().size()>0) {
            if (!folder.getCategories().contains(category)) {
                folder.getCategories().add(category);
            }
        } else {
            List<TaskCategories> categories = new ArrayList<>();
            categories.add(category);
            folder.setCategories(categories);
        }

        folderRepository.save(folder);
    }

    public void deleteCategory(Long f_id, Long c_id) {
        Folders folder = findById(f_id);
        TaskCategories category = taskCategoryService.findById(c_id);

        if (folder.getCategories()!=null && folder.getCategories().size()>0) {
            folder.getCategories().remove(category);
        }
        folderRepository.save(folder);
    }

}
