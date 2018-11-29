package com.aakkus.dockercomposeinitializr.infra.rest.response;

import com.aakkus.dockercomposeinitializr.infra.rest.model.DockerComposeServiceDefinitionModel;
import com.aakkus.dockercomposeinitializr.infra.rest.model.DockerComposeVersionDefinitionModel;

import java.util.List;

public class DockerComposeResponse {

    private List<DockerComposeVersionDefinitionModel> versions;
    private List<DockerComposeServiceDefinitionModel> services;

    public DockerComposeResponse() {
    }

    public DockerComposeResponse(List<DockerComposeVersionDefinitionModel> versions, List<DockerComposeServiceDefinitionModel> services) {
        this.versions = versions;
        this.services = services;
    }

    public List<DockerComposeVersionDefinitionModel> getVersions() {
        return versions;
    }

    public void setVersions(List<DockerComposeVersionDefinitionModel> versions) {
        this.versions = versions;
    }

    public List<DockerComposeServiceDefinitionModel> getServices() {
        return services;
    }

    public void setServices(List<DockerComposeServiceDefinitionModel> services) {
        this.services = services;
    }
}