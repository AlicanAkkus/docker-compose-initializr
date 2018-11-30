package com.aakkus.dockercomposeinitializr.domain.model;

import java.util.List;

public class CreateDockerComposeFileCommand {

    private String version;
    private List<String> services;

    public CreateDockerComposeFileCommand() {
    }

    private CreateDockerComposeFileCommand(Builder builder) {
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

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public static final class Builder {
        private String version;
        private List<String> services;

        private Builder() {
        }

        public CreateDockerComposeFileCommand build() {
            return new CreateDockerComposeFileCommand(this);
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder services(List<String> services) {
            this.services = services;
            return this;
        }
    }
}