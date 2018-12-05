package com.aakkus.dockercomposeinitializr.infra.rest;

import com.aakkus.dockercomposeinitializr.IT;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.infra.rest.request.CreateDockerComposeFileRequest;
import com.aakkus.dockercomposeinitializr.infra.rest.response.DockerComposeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
class DockerComposeRestControllerIT {

    @LocalServerPort
    private Integer port;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    void should_retrieveDockerComposeMetadata() {
        //when
        ResponseEntity<DockerComposeResponse> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/api/v1/docker-compose", DockerComposeResponse.class);

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getVersions()).hasSize(5);
        assertThat(responseEntity.getBody().getServices()).hasSize(40);
    }

    @Test
    void should_createDockerComposeFile() {
        //given
        CreateDockerComposeFileRequest createDockerComposeFileRequest = new CreateDockerComposeFileRequest();
        createDockerComposeFileRequest.setVersion("3.0");
        createDockerComposeFileRequest.setServices(List.of("redis"));

        //when
        ResponseEntity<DockerComposeFile> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/docker-compose", createDockerComposeFileRequest, DockerComposeFile.class);

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getVersion()).isEqualTo("3.0");
        assertThat(responseEntity.getBody().getServices()).isEqualTo(List.of("redis"));
        assertThat(responseEntity.getBody().getComposeFileContent()).isNotNull().contains("docker-compose-initializr-redis");
    }
}