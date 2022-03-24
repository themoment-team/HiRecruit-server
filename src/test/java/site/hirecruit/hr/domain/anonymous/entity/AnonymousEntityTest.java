package site.hirecruit.hr.domain.anonymous.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.hirecruit.hr.domain.anonymous.repository.AnonymousRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
class AnonymousEntityTest {

    @Autowired
    private AnonymousRepository anonymousRepository;

    @Test
    @DisplayName("AnonymousEntity 가 정상적으로 생성된다.")
    void anonymousEntity_working_normally(){
        final AnonymousEntity jyeonjyan = AnonymousEntity.builder()
                .anonymousId(null)
                .anonymousUUID(UUID.randomUUID().toString())
                .name("jyeonjyan")
                .email("jyeonjyan.dev@gmail.com")
                .emailCertified(false)
                .build();

        final AnonymousEntity save = anonymousRepository.save(jyeonjyan);

        assertEquals("jyeonjyan", save.getName());
        assertNotNull(save.getAnonymousUUID());
    }
}