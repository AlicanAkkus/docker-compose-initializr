package com.aakkus.dockercomposeinitializr.domain;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeService;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;
import com.aakkus.dockercomposeinitializr.infra.config.DockerComposeConfiguration;
import com.aakkus.dockercomposeinitializr.infra.rest.request.CreateDockerComposeFileRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DockerComposeFileFacade {

    private final DockerComposeFileCreatePort dockerComposeFileCreatePort;
    private final DockerComposeConfiguration dockerComposeConfiguration;
    private final ObjectMapper objectMapper;

    public DockerComposeFileFacade(DockerComposeFileCreatePort dockerComposeFileCreatePort, DockerComposeConfiguration dockerComposeConfiguration) {
        this.dockerComposeFileCreatePort = dockerComposeFileCreatePort;
        this.dockerComposeConfiguration = dockerComposeConfiguration;

        this.objectMapper = new ObjectMapper(new YAMLFactory());
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public String create(DockerComposeFile dockerComposeFile) throws Exception {
        return dockerComposeFileCreatePort.create(dockerComposeFile);
    }

    public String createOnlySpecificServices(CreateDockerComposeFileRequest createDockerComposeFileRequest) throws Exception {
        List<DockerComposeService> dockerComposeServices = createDockerComposeFileRequest.getServices().stream()
                .map(service -> dockerComposeConfiguration.getServices().stream().filter(s -> service.equalsIgnoreCase(s.getName())).findFirst())
                .map(this::convertToDockerComposeService)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, DockerComposeService> serviceMap = dockerComposeServices
                .stream()
                .collect(Collectors.toMap(DockerComposeService::getServiceName, Function.identity()));


        DockerComposeVersionDefinition versionDefinition = dockerComposeConfiguration.getVersions()
                .stream()
                .filter(service -> createDockerComposeFileRequest.getVersion().equalsIgnoreCase(service.getVersion()))
                .findAny()
                .orElse(dockerComposeConfiguration.getVersions().get(0));

        Map composeFileMap = Map.of("services", serviceMap, "version", versionDefinition.getVersion());

        return objectMapper.writeValueAsString(composeFileMap);
    }

    private DockerComposeService convertToDockerComposeService(Optional<DockerComposeServiceDefinition> dockerComposeServiceDefinition) {
        return dockerComposeServiceDefinition
                .map(definition -> DockerComposeService.builder()
                        .serviceName(definition.getName())
                        .containerName(definition.getName())
                        .restart(definition.getRestartCondition())
                        .ports(definition.getPorts())
                        .image(definition.getImage())
                        .command(definition.getCommand())
                        .environment(definition.getEnvironments())
                        .build())
                .orElse(null);
    }

    public List<DockerComposeVersionDefinition> retrieveVersions() {
        return dockerComposeConfiguration.getVersions();
    }

    public List<DockerComposeServiceDefinition> retrieveServices() {
        return dockerComposeConfiguration.getServices();
    }
}