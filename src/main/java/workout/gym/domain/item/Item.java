package workout.gym.domain.item;

import lombok.Getter;
import lombok.Setter;
import workout.gym.domain.entity.Category;
import workout.gym.domain.file.UploadFile;
import workout.gym.domain.order.OrderDetail;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private int itemPrice;

    private String itemName;

    private String itemInfo;

    @Enumerated(EnumType.STRING)
    private Category itemCategory;

    private int itemStock;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<UploadFile> itemImageFiles = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetail = new ArrayList<>();

}
