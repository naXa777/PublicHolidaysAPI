package com.avk;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import com.avk.controller.CountryController;
import com.avk.controller.CopyrightController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RepositoryLinksResourceProcessor implements RepresentationModelProcessor<RepositoryLinksResource> 
{
    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) 
    {
        resource.add(linkTo(CountryController.class).withRel("countries"));
        resource.add(linkTo(CopyrightController.class).withRel("copyright"));
        return resource;
    }
}
