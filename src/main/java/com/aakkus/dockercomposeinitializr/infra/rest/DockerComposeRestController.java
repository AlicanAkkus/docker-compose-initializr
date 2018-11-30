package com.aakkus.dockercomposeinitializr.infra.rest;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFileFacade;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
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

    @GetMapping
    public ResponseEntity<DockerComposeResponse> retrieveDockerComposeMetadata() {
        List<DockerComposeVersionDefinitionModel> versions = dockerComposeFileFacade.retrieveVersions()
                .stream()
                .map(DockerComposeVersionDefinitionModel::toModel)
                .collect(Collectors.toList());

        List<DockerComposeServiceDefinitionModel> services = dockerComposeFileFacade.retrieveServices()
                .stream()
                .map(DockerComposeServiceDefinitionModel::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DockerComposeResponse(versions, services));
    }

    @PostMapping
    public ResponseEntity<Resource> createDockerComposeFile(@RequestBody CreateDockerComposeFileRequest createDockerComposeFileRequest) throws Exception {
        DockerComposeFile dockerComposeFile = dockerComposeFileFacade.create(createDockerComposeFileRequest.toCommand());
        return buildResponse(dockerComposeFile);
    }

    private ResponseEntity<Resource> buildResponse(DockerComposeFile dockerComposeFile) throws IOException {
        File yml = File.createTempFile("docker-compose-", ".yml");
        FileUtils.write(yml, dockerComposeFile.getComposeFileContent(), Charset.defaultCharset());

        Resource resource = new FileSystemResource(yml.getPath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}