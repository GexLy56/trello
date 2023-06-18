package bitlab.trello.service;

import bitlab.trello.model.Comments;
import bitlab.trello.repo.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;

    public Comments findCommentByTaskId(Long id) {
        return commentRepository.findCommentsByTask_Id(id);
    }

    public void saveCommentByTaskId(Long id, String commentary) {
        Comments comment = commentRepository.findCommentsByTask_Id(id);
        if (comment!=null) {
            comment.setComment(commentary);
            commentRepository.save(comment);
        } else {
            Comments comment1 = new Comments();
            comment1.setTask(taskService.findTaskById(id));
            comment1.setComment(commentary);
            commentRepository.save(comment1);
        }
    }

    public void deleteCommentByTaskId(Long id) {
        if (findCommentByTaskId(id) != null)
        commentRepository.delete(findCommentByTaskId(id));
    }
}
