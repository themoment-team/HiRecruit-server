package site.hirecruit.hr.domain.anonymous.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity;
import site.hirecruit.hr.domain.anonymous.repository.AnonymousRepository;

/**
 * @version 1.0.0
 * @author 전지환
 */
@Service
@RequiredArgsConstructor
public class AnonymousService {

    private final ModelMapper modelMapper;
    private final AnonymousRepository anonymousRepository;

    /**
     * anonymousUUID로 익명 정보 조회하는 비즈니스 로직
     *
     * @param anonymousUUID 찾고자 하는 익명 UUID
     * @return AnonymousResponseDto - 조회된 익명 정보
     */
    @Transactional(readOnly = true)
    public AnonymousDto.AnonymousResponseDto findAnonymousByUUID(String anonymousUUID){
        return anonymousRepository.findByAnonymousUUID(anonymousUUID);
    }

    /**
     * anonymous를 저장하는 비즈니스 로직
     *
     * @param requestDto 저장할 익명 정보
     * @return AnonymousResponseDto
     */
    public AnonymousDto.AnonymousResponseDto createAnonymous(AnonymousDto.AnonymousRequestDto requestDto){
        final AnonymousEntity entity = anonymousRepository.save(AnonymousDto.AnonymousRequestDto.toEntity(requestDto));
        return modelMapper.map(entity, AnonymousDto.AnonymousResponseDto.class);
    }
}
