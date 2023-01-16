package com.yata.backend.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public abstract class AbstractControllerTest {
   @Autowired
   public MockMvc mockMvc;

}