package workout.gym.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Equipment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long id;

    private String equipmentName;

    private String equipmentFileDir;

    private String equipmentInfo;

    private Category equipmentCategory;
}
