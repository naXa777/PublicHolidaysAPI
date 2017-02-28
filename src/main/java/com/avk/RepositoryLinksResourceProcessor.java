package com.avk;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.avk.controller.CountryController;
import com.avk.controller.CopyrightController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class RepositoryLinksResourceProcessor implements ResourceProcessor<RepositoryLinksResource> 
{
    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) 
    {
        resource.add(linkTo(CountryController.class).withRel("countries"));
        resource.add(linkTo(CopyrightController.class).withRel("copyright"));
        return resource;
    }
}
