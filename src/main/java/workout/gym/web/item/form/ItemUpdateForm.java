package workout.gym.web.item.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.entity.Category;
import workout.gym.domain.entity.UploadFile;

import java.util.List;

@Data
public class ItemUpdateForm {

    private String itemName;

    private int itemPrice;

    private String itemInfo;

    private Category itemCategory;

    private int itemStock;

    private List<UploadFile> itemCurrentImageFiles;
    private List<MultipartFile> itemUpdateImageFiles;
}
