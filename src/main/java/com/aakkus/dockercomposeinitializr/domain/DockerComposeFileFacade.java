package com.aakkus.dockercomposeinitializr.domain;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeService;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.infra.config.DockerComposeConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DockerComposeFileFacade {

    private final DockerComposeFileCreatePort dockerComposeFileCreatePort;
    private final DockerComposeConfiguration dockerComposeConfiguration;

    public DockerComposeFileFacade(DockerComposeFileCreatePort dockerComposeFileCreatePort, DockerComposeConfiguration dockerComposeConfiguration) {
        this.dockerComposeFileCreatePort = dockerComposeFileCreatePort;
        this.dockerComposeConfiguration = dockerComposeConfiguration;
    }

    public String create(DockerComposeFile dockerComposeFile) throws Exception {
        Map<String, Object> serviceMap = dockerComposeFile.getServices()
                .stream()
                .collect(Collectors.toMap(DockerComposeService::getServiceName, Function.identity()));

        Map composeFileMap = Map.of("version", dockerComposeFile.getVersion(), "services", serviceMap);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.writeValueAsString(composeFileMap);
    }

    public String createOnlySpecificServices(List<String> services) throws Exception {
        List<DockerComposeService> dockerComposeServices = services.stream()
                .map(service -> dockerComposeConfiguration.getServices().stream().filter(s -> service.equalsIgnoreCase(s.getName())).findFirst())
                .map(Optional::get)
                .map(this::convertToDockerComposeService)
                .collect(Collectors.toList());

        Map<String, Object> serviceMap = dockerComposeServices
                .stream()
                .collect(Collectors.toMap(DockerComposeService::getServiceName, Function.identity()));

        Map composeFileMap = Map.of("version", "3.0", "services", serviceMap);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.writeValueAsString(composeFileMap);
    }

    private DockerComposeService convertToDockerComposeService(DockerComposeServiceDefinition dockerComposeServiceDefinition) {
        return DockerComposeService.builder()
                .serviceName(dockerComposeServiceDefinition.getName())
                .containerName(dockerComposeServiceDefinition.getName())
                .restart(dockerComposeServiceDefinition.getRestartCondition())
                .ports(dockerComposeServiceDefinition.getPorts())
                .image(dockerComposeServiceDefinition.getImage())
                .environment(dockerComposeServiceDefinition.getEnvironments())
                .build();
    }
}