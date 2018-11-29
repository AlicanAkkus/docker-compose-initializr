package com.aakkus.dockercomposeinitializr.infra.rest;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFileFacade;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;
import com.aakkus.dockercomposeinitializr.infra.rest.model.DockerComposeServiceDefinitionModel;
import com.aakkus.dockercomposeinitializr.infra.rest.model.DockerComposeVersionDefinitionModel;
import com.aakkus.dockercomposeinitializr.infra.rest.request.CreateDockerComposeFileRequest;
import com.aakkus.dockercomposeinitializr.infra.rest.response.DockerComposeResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/docker-compose")
public class DockerComposeRestController {

    private final DockerComposeFileFacade dockerComposeFileFacade;

    public DockerComposeRestController(DockerComposeFileFacade dockerComposeFileFacade) {
        this.dockerComposeFileFacade = dockerComposeFileFacade;
    }

    //TODO we will use this later for user create to own services.
//    @PostMapping
//    public ResponseEntity<Resource> create(@Valid @RequestBody CreateDockerComposeFileRequest createDockerComposeFileRequest) throws Exception {
//        String composeFileContent = dockerComposeFileFacade.create(createDockerComposeFileRequest.toModel());
//
//        return buildResponse(composeFileContent);
//    }

    @GetMapping
    public ResponseEntity<DockerComposeResponse> retrieveDockerComposeMetadata() {
        List<DockerComposeVersionDefinitionModel> versions = dockerComposeFileFacade.retrieveVersions()
                .stream()
                .map(DockerComposeVersionDefinitionModel::fromDefinition)
                .collect(Collectors.toList());

        List<DockerComposeServiceDefinitionModel> services = dockerComposeFileFacade.retrieveServices()
                .stream()
                .map(DockerComposeServiceDefinitionModel::fromDefinition)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DockerComposeResponse(versions, services));
    }

    @PostMapping("/build")
    public ResponseEntity<Resource> createOnlySpecificServices(@RequestBody CreateDockerComposeFileRequest createDockerComposeFileRequest) throws Exception {
        String composeFileContent = dockerComposeFileFacade.createOnlySpecificServices(createDockerComposeFileRequest);

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