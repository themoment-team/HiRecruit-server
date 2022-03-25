package site.hirecruit.hr.domain.anonymous.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.repository.AnonymousRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AnonymousService {

    private final AnonymousRepository anonymousRepository;

    /**
     * anonymousUUID로 익명 정보 조회하는 비즈니스 로직
     *
     * @param anonymousUUID 찾고자 하는 익명 UUID
     * @return AnonymousResponseDto - 조회된 익명 정보
     */
    public AnonymousDto.AnonymousResponseDto findAnonymousByUUID(String anonymousUUID){
        return anonymousRepository.findByAnonymousUUID(anonymousUUID);
    }
}
