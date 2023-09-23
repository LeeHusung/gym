package workout.gym.domain.entity;

import lombok.*;
import workout.gym.domain.entity.Address;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.Order;
import workout.gym.web.login.JoinForm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public User(UserRole userRole,
                String username,
                String password,
                String email,
                String realname,
                Address address,
                String nickname,
                String phone) {
        this.userRole = userRole;
        this.username = username;
        this.password =password;
        this.email = email;
        this.realname = realname;
        this.address = address;
        this.nickname = nickname;
        this.phone = phone;
        // MyBag 인스턴스 생성
        MyBag myBag = new MyBag();

        // 연관관계 설정
        setMybag(myBag);
    }

    public void setMybag(MyBag myBag) {
        if(this.myBag != null){
            this.myBag.setUser(null);
        }
        myBag.setUser(this);
        this.myBag= myBag;
    }
}
