package workout.gym.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import workout.gym.domain.entity.Address;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Embedded
    private Address address;
    private String phone;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private LocalDateTime createDate;
    private String nickname;
    private String realname;
    private String gender;
    private String bio; //자기소개

    @OneToOne
    @JoinColumn(name = "myBag_id")
    private MyBag myBag;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommunityAnswer> communityAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<Recommendation> recommendations = new HashSet<>();
}
