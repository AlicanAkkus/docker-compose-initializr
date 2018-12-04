package com.aakkus.dockercomposeinitializr.infra.rest;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFileFacade;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.infra.rest.model.DockerComposeServiceDefinitionModel;
import com.aakkus.dockercomposeinitializr.infra.rest.model.DockerComposeVersionDefinitionModel;
import com.aakkus.dockercomposeinitializr.infra.rest.request.CreateDockerComposeFileRequest;
import com.aakkus.dockercomposeinitializr.infra.rest.response.DockerComposeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DockerComposeFile> createDockerComposeFile(@RequestBody CreateDockerComposeFileRequest createDockerComposeFileRequest) {
        DockerComposeFile dockerComposeFile = dockerComposeFileFacade.create(createDockerComposeFileRequest.toCommand());
        return ResponseEntity.ok(dockerComposeFile);
    }
}