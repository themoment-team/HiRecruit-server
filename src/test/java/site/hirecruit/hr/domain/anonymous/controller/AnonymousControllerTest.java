package site.hirecruit.hr.domain.anonymous.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.service.AnonymousService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnonymousControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnonymousService anonymousService;

    @MockBean
    private AnonymousDto.AnonymousResponseDto anonymousResponseDto;

    @Test
    @DisplayName("[GET]/api/v1/anonymous/?uuid= 가 정상적으로 작동한다.")
    void findAnonymousByUUID_controller_test() throws Exception {
        // GIVEN :: mock anonymous uuid
        String mockUUID = "adfd-asdfajyeonjyan-siwony-sisi";

        // GIVEN :: mock anonymousResponseDto
        final AnonymousDto.AnonymousResponseDto mockAnonymousResponseDto
                = new AnonymousDto.AnonymousResponseDto(1L, mockUUID, "jyeonjyan", "jyeonjyan.dev@gmail.com", false);

        // GIVEN :: request param 에 넣을 value(s)
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("uuid", mockUUID);

        // WHEN :: findAnonymousByUUID() 비지니스 로직 호출시, *given 37 를 리턴한다.
        when(anonymousService.findAnonymousByUUID(any())).thenReturn(mockAnonymousResponseDto);

        // THEN :: isOk
        final ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/v1/anonymous/")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
    }

    @Test
    @Disabled
    @DisplayName("[POST]/api/v1/anonymous/ 가 정상적으로 작동한다.")
    void createAnonymous_controller_test() throws Exception {
        // GIVEN :: mock anonymous uuid
        String mockUUID = "adfd-asdfajyeonjyan-siwony-sisi";

        // GIVEN :: mock anonymousRequestDto
        final AnonymousDto.AnonymousRequestDto anonymousRequestDto = AnonymousDto.AnonymousRequestDto.builder()
                .name("jyeonjyan")
                .email("jyeonjyan.dev@gmail.com")
                .build();

        // GIVEN :: mock anonymousResponseDto
        final AnonymousDto.AnonymousResponseDto mockAnonymousResponseDto
                = new AnonymousDto.AnonymousResponseDto(1L, mockUUID, "jyeonjyan", "jyeonjyan.dev@gmail.com", false);

        Mockito.when(anonymousService.createAnonymous(anonymousRequestDto)).thenReturn(mockAnonymousResponseDto);
        Mockito.when(anonymousResponseDto.getAnonymousId()).thenReturn(mockAnonymousResponseDto.getAnonymousId());

        final ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/v1/anonymous/")
                        .header("location", "/api/v1/anonymous/" + mockAnonymousResponseDto.getAnonymousId())
                        .content(objectMapper.writeValueAsString(anonymousRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

}