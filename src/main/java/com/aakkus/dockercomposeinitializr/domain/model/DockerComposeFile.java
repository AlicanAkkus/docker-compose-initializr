package com.aakkus.dockercomposeinitializr.domain.model;

import java.util.List;

public class DockerComposeFile {

    private String version;
    private List<DockerComposeService> services;

    public DockerComposeFile() {
    }

    private DockerComposeFile(Builder builder) {
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

    public List<DockerComposeService> getServices() {
        return services;
    }

    public void setServices(List<DockerComposeService> services) {
        this.services = services;
    }

    public static final class Builder {
        private String version;
        private List<DockerComposeService> services;

        private Builder() {
        }

        public DockerComposeFile build() {
            return new DockerComposeFile(this);
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder services(List<DockerComposeService> services) {
            this.services = services;
            return this;
        }
    }
}