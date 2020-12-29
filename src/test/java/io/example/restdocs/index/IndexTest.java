package io.example.restdocs.index;

import io.example.restdocs.config.RestDocsConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.example.restdocs.index.docs.IndexDocumentGenerator.createIndexDocument;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class IndexTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    // Junit 5
    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension ();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("https")
                        .withHost("api.com")
                        .withPort(443)
                )
                .build();
    }

    @Test
    @DisplayName("Index API : API 목차 목록 조회")
    public void indexApi() throws Exception {
        // Given
        String urlTemplate = "/api/index";

        // When
        ResultActions resultActions = this.mockMvc.perform(get(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
        );

        // Then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.CONTENT_TYPE))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(createIndexDocument())
                ;
    }
}