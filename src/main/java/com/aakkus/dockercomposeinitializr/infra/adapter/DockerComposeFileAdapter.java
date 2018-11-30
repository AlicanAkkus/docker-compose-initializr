package com.aakkus.dockercomposeinitializr.infra.adapter;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFilePort;
import com.aakkus.dockercomposeinitializr.domain.model.*;
import com.aakkus.dockercomposeinitializr.infra.config.DockerComposeConfiguration;
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
public class DockerComposeFileAdapter implements DockerComposeFilePort {

    private final ObjectMapper objectMapper;
    private final DockerComposeConfiguration dockerComposeConfiguration;

    public DockerComposeFileAdapter(DockerComposeConfiguration dockerComposeConfiguration) {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.dockerComposeConfiguration = dockerComposeConfiguration;
    }


    @Override
    public DockerComposeFile create(CreateDockerComposeFileCommand createDockerComposeFileCommand) {
        Map<String, DockerComposeService> serviceMap = createServiceMap(createDockerComposeFileCommand);
        DockerComposeVersionDefinition versionDefinition = createServiceDefinition(createDockerComposeFileCommand);

        Map composeFileMap = Map.of("services", serviceMap, "version", versionDefinition.getVersion());
        String composeFileContent = createDockerComposeFileContent(composeFileMap);

        return DockerComposeFile.builder()
                .services(createDockerComposeFileCommand.getServices())
                .version(versionDefinition.getVersion())
                .composeFileContent(composeFileContent)
                .build();
    }

    @Override
    public List<DockerComposeVersionDefinition> retrieveDockerComposeVersions() {
        return dockerComposeConfiguration.getVersions();
    }

    @Override
    public List<DockerComposeServiceDefinition> retrieveDockerComposeServices() {
        return dockerComposeConfiguration.getServices();
    }

    private Map<String, DockerComposeService> createServiceMap(CreateDockerComposeFileCommand createDockerComposeFileCommand) {
        List<DockerComposeService> dockerComposeServices = createDockerComposeFileCommand.getServices().stream()
                .map(service -> dockerComposeConfiguration.getServices().stream().filter(s -> service.equalsIgnoreCase(s.getName())).findFirst())
                .map(this::convertToDockerComposeService)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return dockerComposeServices
                .stream()
                .collect(Collectors.toMap(DockerComposeService::getServiceName, Function.identity()));
    }

    private DockerComposeVersionDefinition createServiceDefinition(CreateDockerComposeFileCommand createDockerComposeFileCommand) {
        return dockerComposeConfiguration.getVersions()
                .stream()
                .filter(service -> createDockerComposeFileCommand.getVersion().equalsIgnoreCase(service.getVersion()))
                .findAny()
                .orElse(dockerComposeConfiguration.getVersions().get(0));
    }

    private String createDockerComposeFileContent(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
}