package com.aakkus.dockercomposeinitializr.infra.adapter;

import com.aakkus.dockercomposeinitializr.domain.DockerComposeFileCreatePort;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DockerComposeFileCreateAdapter implements DockerComposeFileCreatePort {

    private final ObjectMapper objectMapper;

    public DockerComposeFileCreateAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String create(DockerComposeFile dockerComposeFile) {
        Map<String, Object> serviceMap = dockerComposeFile.getServices()
                .stream()
                .collect(Collectors.toMap(DockerComposeService::getServiceName, Function.identity()));

        Map composeFileMap = Map.of("version", dockerComposeFile.getVersion(), "services", serviceMap);

        return createFileContent(composeFileMap);
    }

    private String createFileContent(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
