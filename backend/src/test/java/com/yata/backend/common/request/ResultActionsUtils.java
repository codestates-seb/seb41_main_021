package com.yata.backend.common.request;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import static com.yata.backend.common.token.GeneratedToken.getMockHeaderToken;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ResultActionsUtils {
    public static ResultActions postRequest(MockMvc mockMvc, String url, String json) throws Exception {
        return mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .headers(getMockHeaderToken())
                        .with(csrf()))
                .andDo(print());
    }

    public static ResultActions getRequest(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getMockHeaderToken())
                        .with(csrf()))
                .andDo(print());
    }
    public static ResultActions requestPostMultipart(MockMvc mockMvc, String url, MultipartFile file) throws Exception {
        return mockMvc.perform(post(url)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .headers(getMockHeaderToken())
                        .with(csrf())
                        .param("file", file.getName()))
                .andDo(print());

    }
}
