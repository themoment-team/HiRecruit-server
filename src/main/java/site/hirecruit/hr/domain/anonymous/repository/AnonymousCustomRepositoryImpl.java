package site.hirecruit.hr.domain.anonymous.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.dto.QAnonymousDto_AnonymousResponseDto;

import static site.hirecruit.hr.domain.anonymous.entity.QAnonymousEntity.anonymousEntity;

/**
 * @version 1.0.0
 * @author 전지환
 */
@RequiredArgsConstructor
public class AnonymousCustomRepositoryImpl implements AnonymousCustomRepository{

    private final JPQLQueryFactory queryFactory;


    /**
     * 익명 UUID로 익명 정보 조회하는 쿼리 메소드 <br>
     * Expressions.constantAs() 를 사용하여 파라미터 값을 조회 결과에 그대로 대치 함.
     *
     * @param UUID
     * @return AnonymousResponseDto
     */
    @Override
    public AnonymousDto.AnonymousResponseDto findByAnonymousUUID(String UUID) {
        return queryFactory
                .select(new QAnonymousDto_AnonymousResponseDto(
                        anonymousEntity.anonymousId,
                        Expressions.constantAs(UUID, anonymousEntity.anonymousUUID),
                        anonymousEntity.name,
                        anonymousEntity.email,
                        anonymousEntity.emailCertified
                ))
                .from(anonymousEntity)
                .where(anonymousEntity.anonymousUUID.eq(UUID))
                .fetchOne();
    }
}
