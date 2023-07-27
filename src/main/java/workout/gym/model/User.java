package workout.gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String userName;
    private String userPw;
    private String userEmail;
    private String userAddr;
    private String userPhone;
    private String userNickname;
    private String realName;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

}
