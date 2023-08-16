package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;
import workout.gym.common.exception.NotEnoughStockException;
import workout.gym.web.item.form.ItemAddForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public class Item extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private int itemPrice;

    private String itemName;

    private String itemInfo;

    @Enumerated(EnumType.STRING)
    private Category itemCategory;

    private int itemStock;

    @OneToMany(mappedBy = "item", cascade = ALL)
    private List<UploadFile> itemImageFiles = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //생성 메서드
    public static Item createItem(ItemAddForm itemAddForm) {
        Item item = new Item();
        item.setItemPrice(itemAddForm.getItemPrice());
        item.setItemName(itemAddForm.getItemName());
        item.setItemInfo(itemAddForm.getItemInfo());
        item.setItemCategory(itemAddForm.getItemCategory());
        item.setItemStock(itemAddForm.getItemCount());
        return item;
    }

    public void addStock(int count) {
        this.itemStock += count;
    }

    public void removeStock(int count) {
        int restStock = this.itemStock - count;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.itemStock = restStock;
    }
}
