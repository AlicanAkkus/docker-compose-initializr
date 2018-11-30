package com.aakkus.dockercomposeinitializr.infra.rest.model;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;

public class DockerComposeVersionDefinitionModel {

    private String name;
    private String version;

    public DockerComposeVersionDefinitionModel() {
    }

    private DockerComposeVersionDefinitionModel(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static DockerComposeVersionDefinitionModel toModel(DockerComposeVersionDefinition definition) {
        return new DockerComposeVersionDefinitionModel(definition.getName(), definition.getVersion());
    }
}