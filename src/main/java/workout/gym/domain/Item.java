package workout.gym.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
