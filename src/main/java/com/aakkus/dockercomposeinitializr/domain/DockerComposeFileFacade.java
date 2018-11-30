package com.aakkus.dockercomposeinitializr.domain;

import com.aakkus.dockercomposeinitializr.domain.model.CreateDockerComposeFileCommand;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DockerComposeFileFacade {

    private final DockerComposeFilePort dockerComposeFilePort;

    public DockerComposeFileFacade(DockerComposeFilePort dockerComposeFilePort) {
        this.dockerComposeFilePort = dockerComposeFilePort;
    }

    public DockerComposeFile create(CreateDockerComposeFileCommand createDockerComposeFileCommand) {
        return dockerComposeFilePort.create(createDockerComposeFileCommand);
    }

    public List<DockerComposeVersionDefinition> retrieveVersions() {
        return dockerComposeFilePort.retrieveDockerComposeVersions();
    }

    public List<DockerComposeServiceDefinition> retrieveServices() {
        return dockerComposeFilePort.retrieveDockerComposeServices();
    }
}