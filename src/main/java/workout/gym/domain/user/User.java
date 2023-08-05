package workout.gym.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import workout.gym.domain.entity.Address;
import workout.gym.domain.community.Community;
import workout.gym.domain.order.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    private String password;
    private String email;
    private Address address;
    private String phone;
    private String profileImageUrl;
    private String role;
    private LocalDateTime createDate;
    private String nickname;
    private String realname;
    private String gender;
    private String bio; //자기소개

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Community> communities = new ArrayList<>();
}
