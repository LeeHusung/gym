//package workout.gym.domain.file;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.io.File;
//
//@Repository
//public class FileStore {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Value("${file.dir}")
//    private String fileDir;
//
//    public String getFullPath(String fileName) {
//        return fileDir + fileName;
//    }
//
//    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
//        List<UploadFile> storeFileResult = new ArrayList<>();
//        for (MultipartFile multipartFile : multipartFiles) {
//            if (!multipartFile.isEmpty()) {
//                storeFileResult.add(storeFile(multipartFile));
//            }
//        }
//        return storeFileResult;
//    }
//
//    private UploadFile storeFile(MultipartFile multipartFile) throws IOException {
//        if (multipartFile.isEmpty()) {
//            return null;
//        }
//
//        String originalFilename = multipartFile.getOriginalFilename();
//        String storeFileName = createStoreFileName(originalFilename);
//        multipartFile.transferTo(new File(getFullPath(storeFileName)));
//
//        return new UploadFile(originalFilename, storeFileName);
//    }
//
//    private String createStoreFileName(String originalFilename) {
//        String uuid = UUID.randomUUID().toString();
//        String ext = extractExt(originalFilename);
//        return uuid + "." + ext;
//    }
//
//    private String extractExt(String originalFilename) {
//        int pos = originalFilename.lastIndexOf(".");
//        return originalFilename.substring(pos);
//    }
//}
