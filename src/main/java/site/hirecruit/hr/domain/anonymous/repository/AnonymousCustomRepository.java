package site.hirecruit.hr.domain.anonymous.repository;

import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;

import java.util.Optional;

public interface AnonymousCustomRepository {
    Optional<AnonymousDto.AnonymousResponseDto> findByAnonymousUUID(String UUID);
}
