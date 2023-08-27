package workout.gym.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.entity.Item;
import workout.gym.domain.item.ItemFileService;
import workout.gym.domain.item.ItemService;
import workout.gym.web.item.form.ItemAddForm;
import workout.gym.web.item.form.ItemUpdateForm;

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
    public String goItems(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "item/itemMain";
    }

    @GetMapping("/items/new")
    public String addItem(@ModelAttribute("itemAddForm") ItemAddForm form) {
        return "item/addItem";
    }

    @PostMapping("/items/new")
    public String saveItem(@Validated @ModelAttribute("itemAddForm") ItemAddForm itemAddForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "item/addItem";
        }

        Item item = itemService.save(itemAddForm);

        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String item(Model model, @PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Item item = itemService.findById(id);
        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("item", item);
        return "item/viewItem";
    }

    @GetMapping("/items/{id}/update")
    public String updateForm(@PathVariable Long id, @ModelAttribute("itemUpdateForm") ItemUpdateForm itemUpdateForm) {
        Item findItem = itemService.findById(id);
        itemUpdateForm.setItemName(findItem.getItemName());
        itemUpdateForm.setItemPrice(findItem.getItemPrice());
        itemUpdateForm.setItemStock(findItem.getItemStock());
        itemUpdateForm.setItemInfo(findItem.getItemInfo());
        itemUpdateForm.setItemCurrentImageFiles(findItem.getItemImageFiles());
        itemUpdateForm.setItemCategory(findItem.getItemCategory());
        return "item/updateItem";
    }

    @PostMapping("/items/{id}/update")
    public String update(@PathVariable Long id, ItemUpdateForm itemUpdateForm, RedirectAttributes redirectAttributes) throws IOException {
        Item item = itemService.updateItem(id, itemUpdateForm);
        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/items/{itemId}";
    }

    @PostMapping("/items/{id}/delete")
    public String delete(@PathVariable Long id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + itemfileService.getFullPath(filename));
    }

}
