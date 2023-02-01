package com.yata.backend.common.request;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;

import java.io.IOException;

import static com.yata.backend.common.token.GeneratedToken.getMockHeaderToken;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResultActionsUtils {
    public static ResultActions postRequest(MockMvc mockMvc, String url, String json) throws Exception {
        return mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .headers(getMockHeaderToken())
                        .with(csrf()))
                .andDo(print());
    }

    public static ResultActions patchRequest(MockMvc mockMvc, String url, String json) throws Exception {
        return mockMvc.perform(patch(url)
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

    public static ResultActions requestMultiPart(MockMvc mockMvc, String url, MultipartFile file) throws Exception {
        return mockMvc.perform(
                        multipart(url)
                                .file("file", file.getBytes())
                                .headers(getMockHeaderToken())
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
}
