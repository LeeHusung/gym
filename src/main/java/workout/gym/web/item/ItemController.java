package workout.gym.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.domain.entity.Item;
import workout.gym.domain.item.ItemFileService;
import workout.gym.domain.item.ItemService;
import workout.gym.web.item.form.ItemAddForm;

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
    public String saveItem(@Validated @ModelAttribute ItemAddForm itemAddForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "item/addItem";
        }

        Item item = itemService.save(itemAddForm);

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
