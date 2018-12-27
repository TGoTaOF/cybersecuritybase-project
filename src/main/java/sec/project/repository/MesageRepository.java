package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Message;

public interface MesageRepository extends JpaRepository<Message, Long> {

}
