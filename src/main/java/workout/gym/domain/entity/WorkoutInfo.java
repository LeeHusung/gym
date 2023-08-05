package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class WorkoutInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long id;

    private String workoutName;

    private String workoutFileDir;

    private String workoutInfo;

    private String workoutComment;

    private Category workoutCategory;
}
