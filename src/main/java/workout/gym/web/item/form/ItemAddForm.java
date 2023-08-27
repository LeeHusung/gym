package workout.gym.web.item.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.entity.Category;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ItemAddForm {

    private Category itemCategory;

    @NotBlank
    private String itemName;

    @Min(value = 1000, message = "1000원 이상을 입력해주세요")
    private int itemPrice;

    @Max(value = 1000000000, message = "알맞은 수량을 입력하세요.")
    private int itemCount;

    @NotBlank
    private String itemInfo;

    private List<MultipartFile> itemImageFiles;

}
