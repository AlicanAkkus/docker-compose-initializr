package com.aakkus.dockercomposeinitializr.domain;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;

public interface DockerComposeFileCreatePort {

    String create(DockerComposeFile dockerComposeFile);
}