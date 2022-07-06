package site.hirecruit.hr.global.dummy

import net.bytebuddy.utility.RandomString
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import site.hirecruit.hr.global.data.ServerProfile
import javax.annotation.PostConstruct
import kotlin.random.Random


@Configuration
@Profile(ServerProfile.LOCAL, ServerProfile.STAGING)
class SetDummyData(
    private val userRepository: UserRepository,
    private val workerRepository: WorkerRepository,
    private val companyRepository: CompanyRepository
) {

    private fun createUser(githubId: Long?, email: String?, name: String, profileImgUri: String) = userRepository.save(
        UserEntity(
            githubId = githubId ?: Random.nextLong(0, Long.MAX_VALUE),
            githubLoginId = RandomString.make(10),
            email = email ?: "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = name,
            profileImgUri = profileImgUri,
            role = Role.WORKER
        )
    )

    private fun createUser(githubId: Long?, githubLoginId: String, email: String?, name: String, profileImgUri: String) = userRepository.save(
        UserEntity(
            githubId = githubId ?: Random.nextLong(0, Long.MAX_VALUE),
            githubLoginId = githubLoginId,
            email = email ?: "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = name,
            profileImgUri = profileImgUri,
            role = Role.WORKER
        )
    )



    private fun createWorker(introduction: String, position: String, user: UserEntity, company: CompanyEntity) = workerRepository.save(
        WorkerEntity(
            introduction = introduction,
            position = position,
            devYear = 21,
            user = user,
            company = company
        )
    )

    private fun createCompany(name: String, location: String, companyImgUri: String) = companyRepository.save(
        CompanyEntity(
            name = name,
            location = location,
            homepageUri = "https://www.${RandomString.make(6)}.com",
            companyImgUri = companyImgUri
        )
    )

    @PostConstruct
    fun createDummyData(){
        val companyOzu = createCompany("Ozu", "서울 송파구 올림픽로300, 35층", "https://avatars.githubusercontent.com/u/62932968?v=4")
        val companyToss = createCompany("toss", "서울특별시 강남구 테헤란로 142, 12층", "https://image.rocketpunch.com/company/153891/toss-1_logo_1628388572.png?s=400x400&t=inside")
        val companyTheSwing = createCompany("THE SWING", "서울특별시 강남구 선릉로 577, 3층", "https://contents.sixshop.com/uploadedFiles/145168/default/image_1622101035126.gif")
        val companyDRX = createCompany("DRX", "서울시 마포구 독막로7길 59", "https://drx-media.s3.ap-northeast-2.amazonaws.com/image/icon/icon-no-padding.png")
        val companyTM = createCompany("themoment-team", "광주광역시 광산구 상무대로 312", "https://images.velog.io/images/im_chang_1217/post/87073cce-cbea-4079-af78-cca389b17f7a/GSM.png")

        val si_wony = createUser(null,  "siwony", null, "정시원", "https://avatars.githubusercontent.com/u/62932968?v=4")
        val sunwoo0706 = createUser(null, "sunwoo0706", null, "이선우", "https://avatars.githubusercontent.com/u/60869316?v=4")
        val _4yj = createUser(null, null, "나예준", "https://avatars.githubusercontent.com/u/67637706?v=4")
        // 전지환은 실제 githubId를 갖는다.
        val jyeonjyan = createUser(67095821L, "jyeonjyan", "s20062@gsm.hs.kr", "전지환", "https://avatars.githubusercontent.com/u/67095821?v=4")
        val songsihyeon = createUser(null, "songsihyeon", null, "송시현", "https://avatars.githubusercontent.com/u/75899332?v=4")
        // 임창규는 SES sandbox 모드에서 발송할 수 있는 있는 허용된 이메일을 갖는다.
        val imchanggyu = createUser(null, "imchanggyu", "hirecruit@gsm.hs.kr", "임창규", "https://avatars.githubusercontent.com/u/66630940?v=4")

        val worker_si_wony = createWorker("안녕하세유~~~~ 저는 영광 굴비 테크노 킹이에유~~~~~", "테크노킹", si_wony, companyOzu)
        val worker_sunwoo0706 = createWorker("홀리몰리과카몰리투모로우나라의마일스", "서울야스킹", sunwoo0706, companyOzu)
        val worker_4yj = createWorker("mainly webpack ecosystem", "풀스텍 Sr.시니어 개발자", _4yj, companyToss)
        val worker_jyeonjyan = createWorker("mainly JVM ecosystem", "백엔드 개발자", jyeonjyan, companyTheSwing)
        val worker_songsihyeon = createWorker("GOAT 개발자 를 꿈꾸는 송시현입니다.", "프론트엔드 개발자", songsihyeon, companyDRX)
        val woker_imchanggyu = createWorker("앞단 개발자 임창규입니다.", "프론트엔드 개발자", imchanggyu, companyTM)
    }


}