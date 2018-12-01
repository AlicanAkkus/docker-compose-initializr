package com.aakkus.dockercomposeinitializr.infra.adapter;

import com.aakkus.dockercomposeinitializr.IT;
import com.aakkus.dockercomposeinitializr.domain.DockerComposeFilePort;
import com.aakkus.dockercomposeinitializr.domain.model.CreateDockerComposeFileCommand;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@IT
class DockerComposeFileCreateAdapterIT {

    @Autowired
    private DockerComposeFilePort dockerComposeFilePort;

    @Test
    void should_createDockerComposeFile() {
        //given
        CreateDockerComposeFileCommand createDockerComposeFileCommand = CreateDockerComposeFileCommand.builder()
                .services(List.of("redis"))
                .version("3.0")
                .build();

        //when
        DockerComposeFile dockerComposeFile = dockerComposeFilePort.create(createDockerComposeFileCommand);

        //then
        assertThat(dockerComposeFile)
                .isNotNull()
                .hasFieldOrPropertyWithValue("version", "3.0")
                .hasFieldOrPropertyWithValue("services", List.of("redis"))
                .hasFieldOrProperty("composeFileContent")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void should_createDockerComposeFile_evenServiceNotFoundInDefinitions() {
        //given
        CreateDockerComposeFileCommand createDockerComposeFileCommand = CreateDockerComposeFileCommand.builder()
                .services(List.of("redis", "unknown-services"))
                .version("3.0")
                .build();

        //when
        DockerComposeFile dockerComposeFile = dockerComposeFilePort.create(createDockerComposeFileCommand);

        //then
        assertThat(dockerComposeFile)
                .isNotNull()
                .hasFieldOrPropertyWithValue("version", "3.0")
                .hasFieldOrPropertyWithValue("services", List.of("redis", "unknown-services"))
                .hasFieldOrProperty("composeFileContent")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void should_retrieveDockerComposeVersions() {
        //when
        List<DockerComposeVersionDefinition> versionDefinitionList = dockerComposeFilePort.retrieveDockerComposeVersions();

        //then
        assertThat(versionDefinitionList)
                .isNotEmpty()
                .hasSize(5)
                .extracting("version", "name")
                .containsOnly(
                        tuple("3.0", "Version 3.0 for Docker 1.13.0+"),
                        tuple("3.1", "Version 3.1 for Docker 1.13.1+"),
                        tuple("3.5", "Version 3.5 for Docker 17.12.0+"),
                        tuple("3.6", "Version 3.6 for Docker 18.02.0+"),
                        tuple("3.7", "Version 3.7 for Docker 18.06.0+")
                );
    }

    @Test
    void should_retrieveDockerComposeServices() {
        //when
        List<DockerComposeServiceDefinition> composeServiceDefinitionList = dockerComposeFilePort.retrieveDockerComposeServices();

        //then
        assertThat(composeServiceDefinitionList)
                .isNotEmpty()
                .hasSize(39);
    }
}