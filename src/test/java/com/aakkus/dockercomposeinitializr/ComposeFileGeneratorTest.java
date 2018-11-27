package com.aakkus.dockercomposeinitializr;

import com.aakkus.dockercomposeinitializr.infra.rest.request.CreateDockerComposeFileRequest;
import com.aakkus.dockercomposeinitializr.infra.rest.request.DockerComposeServiceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ComposeFileGeneratorTest {

    @Test
    void should_createWithJackson() throws Exception {
        CreateDockerComposeFileRequest createDockerComposeFileRequest = new CreateDockerComposeFileRequest();
        createDockerComposeFileRequest.setVersion("3.0");
        createDockerComposeFileRequest.setServices(List.of(DockerComposeServiceRequest.builder().serviceName("test").image("image").build()));

        Map<String, Object> services = Map.of("services", Map.of("test", createDockerComposeFileRequest.getServices().get(0)), "version", createDockerComposeFileRequest.getVersion());


        YAMLFactory jf = new YAMLFactory();

        ObjectMapper objectMapper = new ObjectMapper(jf);
        String value = objectMapper.writeValueAsString(services);

        System.out.println(value);
        assertThat(value).isNotNull();
    }
}