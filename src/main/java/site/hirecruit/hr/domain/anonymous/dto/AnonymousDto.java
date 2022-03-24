package site.hirecruit.hr.domain.anonymous.dto;

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
    static class AnonymousRequestDto {

        @NotBlank
        private String name;

        @NotBlank
        private String email;

        @Builder
        public AnonymousRequestDto(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public AnonymousEntity toEntity(AnonymousRequestDto anonymousRequestDto){
            return AnonymousEntity.builder()
                    .anonymousUUID(UUID.randomUUID().toString())
                    .name(anonymousRequestDto.getName())
                    .email(anonymousRequestDto.getEmail())
                    .emailCertified(false)
                    .build();
        }
    }

}
