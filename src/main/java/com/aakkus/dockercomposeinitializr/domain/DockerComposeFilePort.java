package com.aakkus.dockercomposeinitializr.domain;

import com.aakkus.dockercomposeinitializr.domain.model.CreateDockerComposeFileCommand;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;

import java.util.List;

public interface DockerComposeFilePort {

    DockerComposeFile create(CreateDockerComposeFileCommand createDockerComposeFileCommand);

    List<DockerComposeVersionDefinition> retrieveDockerComposeVersions();

    List<DockerComposeServiceDefinition> retrieveDockerComposeServices();
}