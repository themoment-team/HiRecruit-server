package site.hirecruit.hr.domain.auth.dto;

import lombok.*;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;

import java.util.Map;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE) @AllArgsConstructor
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String userNameAttributeName;
    private Long id;
    private String name;
    private String email;
    private String profileUri;

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
                .id(Integer.toUnsignedLong((Integer) attributes.get(userNameAttributeName)))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileUri((String) attributes.get("avatar_url"))
                .build();
    }

    public WorkerEntity toEntity(WorkerEntity.Role role){
        return WorkerEntity.builder()
                .githubId(id)
                .email(email)
                .name(name)
                .profileUri(profileUri)
                .company((String) attributes.get("company"))
                .role(role)
                .build();
    }


}
