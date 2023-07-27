package workout.gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Equipment {

    @Id @GeneratedValue
    private Long id;

    private String equipmentName;

    private String equipmentFileDir;

    private String equipmentInfo;

    private Category equipmentCategory;
}
