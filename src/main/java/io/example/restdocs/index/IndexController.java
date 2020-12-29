package io.example.restdocs.index;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RepresentationModel index(){
        RepresentationModel indexRepresentationModel = new RepresentationModel();
        indexRepresentationModel.add(linkTo(IndexController.class).withSelfRel());
        return indexRepresentationModel;
    }
}
