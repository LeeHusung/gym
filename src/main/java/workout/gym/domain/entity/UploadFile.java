package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class UploadFile {

    public UploadFile() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uploadFile_id")
    private Long id;

    private String uploadFileName;

    private String storeFileName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
//
//    //생성 메서드
//    public static UploadFile createUploadFile(Item item, String uploadFileName, String storeFileName) {
//        UploadFile uploadFile = new UploadFile();
//        uploadFile.setItem(item);
//        uploadFile.setUploadFileName(uploadFileName);
//        uploadFile.setStoreFileName(storeFileName);
//        return uploadFile;
//    }
}
