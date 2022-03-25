package site.hirecruit.hr.domain.anonymous.repository;

import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;

public interface AnonymousCustomRepository {
    AnonymousDto.AnonymousResponseDto findByAnonymousUUID(String UUID);
}
