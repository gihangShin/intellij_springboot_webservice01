package com.hang.book.springboot.config.auth.dto;

import com.hang.book.springboot.domain.user.Role;
import com.hang.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String picture) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;

    }

    //  반환하는 사용자 정보는 Map 이기 때문에 하나하나 변환해야함.
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }


        return ofGoogle(userNameAttributeName, attributes);
    }

    //    Google
    public static OAuthAttributes ofGoogle(String userNameAttirbuteName, Map<String, Object> attributes) {
        System.out.println("OAuthAttribute[GOOGLE] : name = " + (String) attributes.get("name"));
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttirbuteName)
                .build();
    }

    //    Naver
    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println("OAuthAttribute[NAVER] : name = " + (String) response.get("name"));
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //  User 엔티티 생성 (?)
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }

}
