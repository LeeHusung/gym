package workout.gym.domain.file;

import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.entity.UploadFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public abstract class FileService {

    String getFullPath(String fileName) {
        return null;
    }

    List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        return null;
    }

    public UploadFile saveFile(MultipartFile multipartFile) throws IOException {
        return null;
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos);
    }
}
