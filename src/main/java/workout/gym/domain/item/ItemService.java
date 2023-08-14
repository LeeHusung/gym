package workout.gym.domain.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.entity.Item;
import workout.gym.domain.entity.UploadFile;
import workout.gym.web.item.form.ItemAddForm;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemFileService itemFileService;

    @Transactional
    public Item save(Item item) {
        itemRepository.save(item);
        return item;
    }

    @Transactional
    public Item save(ItemAddForm itemAddForm) throws IOException {
        Item item = Item.createItem(itemAddForm);
        itemRepository.save(item);
        List<UploadFile> uploadFiles = itemFileService.saveFiles(itemAddForm.getItemImageFiles(), item);
        item.setItemImageFiles(uploadFiles);
        return item;
    }

    @Transactional
    public Item findById(Long id) {
        Item findItem = itemRepository.findById(id);
        return findItem;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
