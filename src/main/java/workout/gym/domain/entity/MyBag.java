package workout.gym.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyBag extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myBag_id")
    private Long id;

    @OneToOne(mappedBy = "myBag", fetch = LAZY, cascade = ALL)
    @Setter
    private User user;

    //여기서 cascade = ALL 옵션을 설정하면, 부모 엔티티(MyBag)의 변경 사항이 자동으로 자식 엔티티(OrderItem)에 전파
    //즉, MyBag 엔티티를 저장할 때 연관된 OrderItem 엔티티도 자동으로 저장된다. 따라서 OrderItem 엔티티를 따로 저장할 필요가 없다.
    @OneToMany(mappedBy = "myBag", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //연관관계 메서드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setMyBag(this);
    }

    //    //생성 메서드
//    public static MyBag createMyBag(OrderItem... orderItems) {
//        MyBag myBag = new MyBag();
//        for (OrderItem orderItem : orderItems) {
//            myBag.addOrderItem(orderItem);
//        }
//        return myBag;
//    }

}
