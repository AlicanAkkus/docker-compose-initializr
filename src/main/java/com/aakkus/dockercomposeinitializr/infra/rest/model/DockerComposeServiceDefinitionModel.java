package com.aakkus.dockercomposeinitializr.infra.rest.model;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DockerComposeServiceDefinitionModel {

    @JsonProperty("value")
    private String name;

    public DockerComposeServiceDefinitionModel() {
    }

    private DockerComposeServiceDefinitionModel(String name) {
        this.name = name;
    }

    public static DockerComposeServiceDefinitionModel toModel(DockerComposeServiceDefinition definition) {
        return new DockerComposeServiceDefinitionModel(definition.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}