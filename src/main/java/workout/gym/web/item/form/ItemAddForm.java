package workout.gym.web.item.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.entity.Category;

import java.util.List;

@Data
public class ItemAddForm {

    private Category itemCategory;

//    @NotBlank
    private String itemName;

//    @NotBlank
//    @Min(value = 1000, message = "1000원 이상을 입력해주세요")
    private int itemPrice;

//    @NotBlank
    private String itemInfo;

    private List<MultipartFile> itemImageFiles;

}
