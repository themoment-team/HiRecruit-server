package site.hirecruit.hr.thirdParty.aws.ses.dto

/**
 * aws SES 서비스를 사용할 때 HR Dev팀 내부적으로 Message Data를 Transfer할 객체입니다.
 *
 * @since 1.2.0
 * @author 전지환
 */
open class SesRequestDto {

    /**
     * 기본적으로 ses 메시지를 만들 때 사용하는 ReqeustDto 입니다.
     *
     * @param subjectData 이메일 제목
     * @param bodyText 이메일 내용
     * @param destinationDto 받는 사람 정보
     */
    class BasicSesRequestDto(
        private val subjectData: String,
        private val bodyText: String,
        private val destinationDto: DestinationDto
    )

    /**
     * MinifyHtml을 이메일 내용으로 하고자 할 때 사용하는 RequestDto 입니다.
     *
     * @param subjectData 이메일 제목
     * @param bodyMinifyHtml 이메일 내용 html
     * @param destinationDto 받는 사람 정보
     */
    class HtmlFormSesRequestDto(
        private val subjectData: String,
        private val bodyMinifyHtml: String,
        private val destinationDto: DestinationDto
    )

    /**
     * 미리 만들어진 template을 사용해서 이메일을 보내고자 할 때 사용하는 RequestDto 입니다.
     *
     * @param subjectData 이메일 제목
     * @param templateArn emailTemplate 고유의 Arn
     * @param templateData {{something}} 에 넣을 데이터:: ex) "TemplateData": "{ \"name\":\"Alejandro\", \"favoriteanimal\": \"alligator\" }"
     * @param templateName
     * @param destinationDto 받는 사람 정보
     */
    class TemplateSesRequestDto(
        val templateArn: String,
        val templateData: String,
        val templateName: String,
        val destinationDto: DestinationDto
    )

    /**
     * 이메일의 받는 사람 주소 데이터를 받는 Dto 입니다.
     *
     * @param bccAddress bcc
     * @param ccAddress cc
     * @param toAddress not-null, 받는사람 email
     */
    class DestinationDto(
        val bccAddress: List<String>,
        val ccAddress: List<String>,
        val toAddress: List<String>
    )
}