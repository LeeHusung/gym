package workout.gym.domain.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item save(Item item) {
        itemRepository.save(item);
        return item;
    }

    @Transactional
    public Item findById(Long id) {
        Item findItem = itemRepository.findById(id);
        return findItem;
    }
}
