package site.hirecruit.hr.domain.auth.dto;

import lombok.*;

import java.util.Map;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE) @AllArgsConstructor
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String userNameAttributeName;
    private Long id;
    private String name;
    private String email;
    private String pictureUri;

    public static OAuthAttributes of(final String registrationId,
                                     final String userNameAttributeName,
                                     final Map<String, Object> attributes){
        return ofGithub(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGithub(final String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .id(Long.getLong((String) attributes.get(userNameAttributeName)))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .pictureUri((String) attributes.get("picture"))
                .build();
    }


}
