package com.project.ricky.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Column(unique = true)
    private String userEmail;

    @NotBlank
    @Column(nullable = false)
    private String userPassword;

    @NotBlank
    @Column(nullable = false)
    private String userName;

    @NotBlank
    @Column(nullable = false)
    private String userPhone;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "userId"))
    private Set<Authority> authorities;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private User admin;

    @Column(nullable = true)
    private int loginCnt;

    @NotBlank
    @Column(nullable = false, length = 1)
    private String userYn;

//    @Column(nullable = false, length = 1)
//    @ColumnDefault("N")
//    private String lockedYn;

    @Column(nullable = true)
    private String userNickName;

//    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDate;

//    private String modId;

    private LocalDateTime modDate;

    private boolean enabled;

}
