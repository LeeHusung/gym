package workout.gym.domain.entity;

import lombok.*;
import workout.gym.common.exception.NotEnoughStockException;
import workout.gym.web.item.form.ItemAddForm;
import workout.gym.web.item.form.ItemUpdateForm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Item extends BaseEntity {

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
    @Setter
    private List<UploadFile> itemImageFiles = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Item(int itemPrice, String itemName, String itemInfo, Category itemCategory, int itemStock) {
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.itemCategory = itemCategory;
        this.itemStock = itemStock;
    }

    public void updateItem(int itemPrice, String itemName, String itemInfo, Category itemCategory, int itemStock) {
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.itemCategory = itemCategory;
        this.itemStock = itemStock;
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
