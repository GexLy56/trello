package bitlab.trello.controller;

import bitlab.trello.model.Comments;
import bitlab.trello.model.Folders;
import bitlab.trello.model.TaskCategories;
import bitlab.trello.model.Tasks;
import bitlab.trello.service.CommentService;
import bitlab.trello.service.FolderService;
import bitlab.trello.service.TaskCategoryService;
import bitlab.trello.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class HomeController {
    private final TaskService taskService;
    private final FolderService folderService;
    private final TaskCategoryService taskCategoryService;
    private final CommentService commentService;

    @GetMapping(value = "/")
    public String homePage(Model model) {
        model.addAttribute("folders", folderService.getFolders());
        return "index";
    }

    @PostMapping(value = "/addFolder")
    public String addFolder(Folders folder) {
        folderService.addFolder(folder);
        return "redirect:/";
    }

    @GetMapping(value = "/folder/{id}")
    public String detailsFolder(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("folder", folderService.findById(id));
        model.addAttribute("categories", taskCategoryService.getCategories());
        model.addAttribute("tasks", taskService.getTasksByFolderId(id));
        return "folder";
    }

    @PostMapping(value = "/addCategory")
    public String addCategory(@RequestParam(name = "folder_id") Long f_id,
                              @RequestParam(name = "category_id") Long c_id) {
        folderService.addCategory(f_id, c_id);
        return "redirect:/folder/" + f_id;
    }

    @PostMapping(value = "/deleteCategory")
    public String deleteCategory(@RequestParam(name = "folder_id") Long f_id,
                                 @RequestParam(name = "category_id") Long c_id) {
        folderService.deleteCategory(f_id, c_id);
        return "redirect:/folder/" + f_id;
    }

    @PostMapping(value = "/addTask")
    public String addTask(Tasks task) {
        taskService.addTask(task);
        return "redirect:/folder/" + task.getFolder().getId();
    }

    @GetMapping(value = "/task/{id}")
    public String taskDetailsPage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("task", taskService.findTaskById(id));
        Comments comments = commentService.findCommentByTaskId(id);
        if (comments != null) {
            model.addAttribute("comment", commentService.findCommentByTaskId(id));
        }
        return "task";
    }

    @PostMapping(value = "/saveTask")
    public String saveTask(Tasks task, @RequestParam(name = "comment", required = false) String comment) {
        taskService.saveTask(task);
        if(comment!=null) {
            commentService.saveCommentByTaskId(task.getId(), comment);
        }
        return "redirect:/folder/" + task.getFolder().getId();
    }

    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name = "id") Long id, @RequestParam(name = "f_id") Long f_id) {
        commentService.deleteCommentByTaskId(id);
        taskService.deleteTask(id);
        return "redirect:/folder/" + f_id;
    }

    @PostMapping(value = "/deleteFolder")
    public String deleteFolder(@RequestParam(name = "id") Long id) {
        folderService.deleteFolder(id);
        return "redirect:/";
    }

    @PostMapping(value = "/saveFolder")
    public String saveFolder(Folders folder) {
        folderService.saveFolder(folder);
        return "redirect:/folder/" + folder.getId();
    }

    @GetMapping(value = "/categories")
    public String categoriesPage(Model model) {
        model.addAttribute("categories", taskCategoryService.getCategories());
        return "taskcategory";
    }

    @PostMapping(value = "/addTaskCategory")
    public String addCategory(TaskCategories category) {
        taskCategoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping(value = "/category/{id}")
    public String CategoryPage(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("category", taskCategoryService.findById(id));
        return "category";
    }

    @PostMapping(value = "/saveTaskCategory")
    public String editTaskCategory(TaskCategories category) {
        taskCategoryService.editCategory(category);
        return "redirect:/categories";
    }

    @PostMapping(value = "/deleteTaskCategory")
    public String deleteTaskCategory(@RequestParam(name = "id") Long id) {
        taskCategoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
