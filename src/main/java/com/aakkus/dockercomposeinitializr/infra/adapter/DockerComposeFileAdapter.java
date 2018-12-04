package com.aakkus.dockercomposeinitializr.infra.adapter;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFilePort;
import com.aakkus.dockercomposeinitializr.domain.model.CreateDockerComposeFileCommand;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;
import com.aakkus.dockercomposeinitializr.infra.adapter.model.DockerComposeService;
import com.aakkus.dockercomposeinitializr.infra.config.DockerComposeConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DockerComposeFileAdapter implements DockerComposeFilePort {

    private static final Logger logger = LoggerFactory.getLogger(DockerComposeFileAdapter.class);

    private final ObjectMapper objectMapper;
    private final DockerComposeConfiguration dockerComposeConfiguration;
    private final String containerNamePrefix;

    public DockerComposeFileAdapter(DockerComposeConfiguration dockerComposeConfiguration, @Value("${spring.application.name}") String containerNamePrefix) {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
        this.dockerComposeConfiguration = dockerComposeConfiguration;
        this.containerNamePrefix = containerNamePrefix.concat("-");
    }

    @Override
    public DockerComposeFile create(CreateDockerComposeFileCommand createDockerComposeFileCommand) {
        Map<String, DockerComposeService> serviceMap = createServiceMap(createDockerComposeFileCommand);
        DockerComposeVersionDefinition versionDefinition = createServiceDefinition(createDockerComposeFileCommand);

        Map composeFileMap = Map.of("version", versionDefinition.getVersion(), "services", serviceMap);
        String composeFileContent = createDockerComposeFileContent(composeFileMap);

        logger.info("Docker compose file created successfully. Version: {} and Services: {}", versionDefinition.getVersion(), createDockerComposeFileCommand.getServices());
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
        return dockerComposeConfiguration.getServices()
                .stream()
                .sorted(Comparator.comparing(DockerComposeServiceDefinition::getName))
                .collect(Collectors.toList());
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
            return objectMapper.writeValueAsString(map).replaceFirst("---\\n","");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DockerComposeService convertToDockerComposeService(Optional<DockerComposeServiceDefinition> dockerComposeServiceDefinition) {
        return dockerComposeServiceDefinition
                .map(definition -> DockerComposeService.builder()
                        .serviceName(definition.getName())
                        .containerName(containerNamePrefix.concat(definition.getName()))
                        .restart(definition.getRestartCondition())
                        .ports(definition.getPorts())
                        .image(definition.getImage())
                        .command(definition.getCommand())
                        .environment(definition.getEnvironments())
                        .build())
                .orElse(null);
    }
}