package com.project.ricky.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

import static java.lang.String.format;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="TB_OAUTH2_USER")
public class Oauth2User {

    public static enum OAuth2Provider {
        google {
            public Oauth2User convert(OAuth2User user){
                return Oauth2User.builder()
                        .oauth2UserId(format("%s_%s", name(), user.getAttribute("sub")))
                        .provider(google)
                        .email(user.getAttribute("email"))
                        .name(user.getAttribute("name"))
                        .created(LocalDateTime.now())
                        .build();
            }
        },
        naver {
            public Oauth2User convert(OAuth2User user){
                Map<String, Object> resp = user.getAttribute("response");
                return Oauth2User.builder()
                        .oauth2UserId(format("%s_%s", name(), resp.get("id")))
                        .provider(naver)
                        .email(""+resp.get("email"))
                        .name(""+resp.get("name"))
                        .build();
            }
        };
        public abstract Oauth2User convert(OAuth2User user);
    }

    @Id
    private String oauth2UserId;
    private Long userId;
    private String name;
    private String email;
    private OAuth2Provider provider;
    private LocalDateTime created;


}
