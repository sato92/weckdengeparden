package com.gepardec.wdg.client.personio;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "personio")
@Path("/recruiting")
public interface RecruitingApi {

    @Path("/applicant")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response createApplicant(@MultipartForm ApplicationForm applicationForm);

}
