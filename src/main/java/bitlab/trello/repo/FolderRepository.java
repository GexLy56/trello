package bitlab.trello.repo;

import bitlab.trello.model.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folders, Long> {
}
