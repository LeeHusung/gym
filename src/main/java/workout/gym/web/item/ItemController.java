package workout.gym.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.domain.file.UploadFile;
import workout.gym.domain.item.Item;
import workout.gym.domain.item.ItemFileService;
import workout.gym.domain.item.ItemService;
import workout.gym.web.item.form.ItemAddForm;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemFileService itemfileService;
    private final ItemService itemService;

    @GetMapping("/items")
    public String goItems() {
        return "item/itemMain";
    }

    @GetMapping("/items/new")
    public String addItem(@ModelAttribute ItemAddForm form) {
        return "item/addItem";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemAddForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {

//        List<UploadFile> storeImageFiles = fileService.saveFiles(form.getItemImageFiles());

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setItemPrice(form.getItemPrice());
        item.setItemInfo(form.getItemInfo());
        item.setItemCategory(form.getItemCategory());
        // 상품 저장 //왜 저장먼저?
        itemService.save(item);

        //save안하고 saveFiles()호출하면 아직 db에 반영되지 않은 껍데기 item이 넘어가기 때문에 연관관계가 설정될 수 없음.
        //jpa는 트랜잭션을 통해 commit을 해야만 db에 반영이됨. 이걸 행하는 코드가 54 line임
        //해결방법이 바로 Item객체를 먼저 db에 저장하고 그 후 UploadFile과 연관관계를 설정하는 것.
        List<UploadFile> storeImageFiles = itemfileService.saveFiles(form.getItemImageFiles(), item); //?

        // 상품에 연관된 파일들을 설정
        item.setItemImageFiles(storeImageFiles);
//        itemService.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String item(Model model, @PathVariable Long id) {
        Item item = itemService.findById(id);

        model.addAttribute("item", item);
        return "item/viewItem";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + itemfileService.getFullPath(filename));
    }
}
