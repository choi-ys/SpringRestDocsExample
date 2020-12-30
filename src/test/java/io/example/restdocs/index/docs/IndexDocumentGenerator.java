package io.example.restdocs.index.docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static io.example.restdocs.common.constants.DocsConstants.SELF;
import static io.example.restdocs.config.ApiDocumentUtils.getDocumentRequest;
import static io.example.restdocs.config.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

public class IndexDocumentGenerator {

    public static RestDocumentationResultHandler createIndexDocument(){
        return document("{class-name}/{method-name}",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel(SELF).description("link to self")
                )
        );
    }
}

