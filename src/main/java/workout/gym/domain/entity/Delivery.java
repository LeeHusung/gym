package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    private String deliveryName;
    private String deliveryPhone;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String deliveryStartDate;
    private String deliveryCompletionDate;
}
