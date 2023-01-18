package com.yata.backend.domain.image.controller;

import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.image.service.ImageUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.yata.backend.common.request.ResultActionsUtils.*;

@WebMvcTest(ImageController.class)
class ImageControllerTest extends AbstractControllerTest {
    @MockBean
    private ImageUploader imageUploader;

    @Test
    @DisplayName("이미지 업로드")
    @WithMockUser
    void profileImage() throws Exception {
        // given
        String imageUrl = "https://yata-image.s3.ap-northeast-2.amazonaws.com/1.png";
        given(imageUploader.uploadImage(any(), any())).willReturn(imageUrl);
        String fileName = "testCustomerUpload";
        String contentType = "xls";
        String filePath = "src/test/resources/images/images.png";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, fileInputStream);

        // when
        ResultActions resultActions = requestPostMultipart(mockMvc, "/api/v1/images/profile", file);
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(imageUrl));
        resultActions.andDo(document("image-profile",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer access token")
                        ),
                        requestParameters(
                                parameterWithName("file").description("이미지 파일"),
                                parameterWithName("_csrf").description("무시 : csrf token ")
                        ),
                        responseFields(
                                fieldWithPath("data").description("이미지 URL")
                        )
                )
        );

    }


}