package site.hirecruit.hr.domain.anonymous.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity;
import site.hirecruit.hr.domain.anonymous.repository.AnonymousRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AnonymousServiceTest {

    @Autowired
    private AnonymousRepository anonymousRepository;

    @Autowired
    private AnonymousService anonymousService;

    @Test
    @DisplayName("findAnonymousByUUID 비즈니스 로직이 값을 정상 반환 한다.")
    void findAnonymousByUUID_비즈니스_로직_정상이다(){
        // GIVEN:: 익명을 생성한다.
        final String nameOfAnonymous = "jyeonjyan";
        final AnonymousDto.AnonymousRequestDto jyeonjyan = AnonymousDto.AnonymousRequestDto.builder()
                .name(nameOfAnonymous)
                .email("jyeonjyan.dev@gmail.com")
                .build();

        // 익명을 저장한다.
        final AnonymousEntity saveJyeonjyan = anonymousRepository.save(AnonymousDto.AnonymousRequestDto.toEntity(jyeonjyan));
        assertNotNull(saveJyeonjyan);

        // WHEN:: 익명 UUID를 통해 익명 정보를 조회한다.
        final String anonymousUUID = saveJyeonjyan.getAnonymousUUID();
        final AnonymousDto.AnonymousResponseDto anonymousByUUID = anonymousService.findAnonymousByUUID(anonymousUUID);

        // THEN:: 알아낸(actual) 익명의 이름이 *given(expected) 익명의 이름과 같다.
        assertEquals(nameOfAnonymous, anonymousByUUID.getName());
    }
}