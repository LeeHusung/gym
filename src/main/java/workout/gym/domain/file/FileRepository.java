package workout.gym.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workout.gym.domain.entity.UploadFile;

@Repository
public interface FileRepository extends JpaRepository<UploadFile, Long> {
}