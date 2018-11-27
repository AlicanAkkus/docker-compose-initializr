package com.aakkus.dockercomposeinitializr.infra.rest;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFileFacade;
import com.aakkus.dockercomposeinitializr.infra.rest.request.CreateDockerComposeFileRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/api/v1/docker-compose")
public class DockerComposeRestController {

    private final DockerComposeFileFacade dockerComposeFileFacade;

    public DockerComposeRestController(DockerComposeFileFacade dockerComposeFileFacade) {
        this.dockerComposeFileFacade = dockerComposeFileFacade;
    }

    @PostMapping
    public ResponseEntity<Resource> create(@Valid @RequestBody CreateDockerComposeFileRequest createDockerComposeFileRequest) throws Exception {
        String composeFileContent = dockerComposeFileFacade.create(createDockerComposeFileRequest.toModel());

        return buildResponse(composeFileContent);
    }

    @PostMapping("/build")
    public ResponseEntity<Resource> createOnlySpecificServices(@NotEmpty @RequestBody List<String> services) throws Exception {
        String composeFileContent = dockerComposeFileFacade.createOnlySpecificServices(services);

        return buildResponse(composeFileContent);
    }

    private ResponseEntity<Resource> buildResponse(String composeFileContent) throws IOException {
        File yml = File.createTempFile("docker-compose-", ".yml");
        FileUtils.write(yml, composeFileContent, Charset.defaultCharset());

        Resource resource = new FileSystemResource(yml.getPath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}