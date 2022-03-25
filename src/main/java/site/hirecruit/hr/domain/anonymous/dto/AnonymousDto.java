package site.hirecruit.hr.domain.anonymous.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@NoArgsConstructor
public class AnonymousDto {

    @Getter
    @NoArgsConstructor
    public static class AnonymousRequestDto {

        @NotBlank
        private String name;

        @NotBlank
        private String email;

        @Builder
        public AnonymousRequestDto(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public static AnonymousEntity toEntity(AnonymousRequestDto anonymousRequestDto){
            return AnonymousEntity.builder()
                    .anonymousUUID(UUID.randomUUID().toString())
                    .name(anonymousRequestDto.getName())
                    .email(anonymousRequestDto.getEmail())
                    .emailCertified(false)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class AnonymousResponseDto{
        @NotBlank
        private Long anonymousId;
        @NotBlank
        private String anonymousUUID;
        @NotBlank
        private String name;
        @NotBlank
        private String email;
        @NotBlank
        private boolean emailCertified;

        @QueryProjection
        public AnonymousResponseDto(Long anonymousId, String anonymousUUID, String name, String email, boolean emailCertified) {
            this.anonymousId = anonymousId;
            this.anonymousUUID = anonymousUUID;
            this.name = name;
            this.email = email;
            this.emailCertified = emailCertified;
        }
    }

}
