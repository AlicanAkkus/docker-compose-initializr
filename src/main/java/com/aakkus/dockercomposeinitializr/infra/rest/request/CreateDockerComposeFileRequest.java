package com.aakkus.dockercomposeinitializr.infra.rest.request;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeFile;

import java.util.List;
import java.util.stream.Collectors;

public class CreateDockerComposeFileRequest {

    private String version;
    private List<DockerComposeServiceRequest> services;

    public CreateDockerComposeFileRequest() {
    }

    private CreateDockerComposeFileRequest(Builder builder) {
        this.version = builder.version;
        this.services = builder.services;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DockerComposeServiceRequest> getServices() {
        return services;
    }

    public void setServices(List<DockerComposeServiceRequest> services) {
        this.services = services;
    }

    public DockerComposeFile toModel() {
        return DockerComposeFile.builder()
                .version(version)
                .services(services.stream().map(DockerComposeServiceRequest::toModel).collect(Collectors.toList()))
                .build();
    }

    public static final class Builder {
        private String version;
        private List<DockerComposeServiceRequest> services;

        private Builder() {
        }

        public CreateDockerComposeFileRequest build() {
            return new CreateDockerComposeFileRequest(this);
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder services(List<DockerComposeServiceRequest> services) {
            this.services = services;
            return this;
        }
    }
}