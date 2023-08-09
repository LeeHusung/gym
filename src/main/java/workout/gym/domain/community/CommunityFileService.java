package workout.gym.domain.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.file.FileRepository;
import workout.gym.domain.file.UploadFile;
import workout.gym.domain.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CommunityFileService {

    private final FileRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles, Community community) throws IOException {
        List<UploadFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                files.add(saveFile(multipartFile, community)); //파일들을 업로드하고 해당 파일들과 community 객체 간의 연관관계를 설정하는 역할을 하는 메서드.
            }
        }
//        community.setCommunityImageFiles(files); //community과 파일들 간의 연관관계 설정 //이거는 community가 연관관계 주인이 아닌데 왜 이렇게 설정하지?
        fileRepository.saveAll(files);
        return files;
    }

    public UploadFile saveFile(MultipartFile multipartFile, Community community) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        UploadFile uploadFile = new UploadFile(originalFilename, storeFileName);
        uploadFile.setCommunity(community); // 파일과 community 객체 간의 연관관계 설정. 이로써 파일이 어떤 파일과 연결되어 있는지를 알 수 있게 됨.
        return uploadFile;
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
