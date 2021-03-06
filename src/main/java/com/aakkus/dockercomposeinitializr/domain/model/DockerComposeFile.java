package com.aakkus.dockercomposeinitializr.domain.model;

import java.util.List;

public class DockerComposeFile {

    private String version;
    private List<String> services;
    private String composeFileContent;

    public DockerComposeFile() {
    }

    private DockerComposeFile(Builder builder) {
        this.version = builder.version;
        this.services = builder.services;
        this.composeFileContent = builder.composeFileContent;
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

    public String getComposeFileContent() {
        return composeFileContent;
    }

    public void setComposeFileContent(String composeFileContent) {
        this.composeFileContent = composeFileContent;
    }

    public static final class Builder {
        private String version;
        private List<String> services;
        private String composeFileContent;

        private Builder() {
        }

        public DockerComposeFile build() {
            return new DockerComposeFile(this);
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder services(List<String> services) {
            this.services = services;
            return this;
        }

        public Builder composeFileContent(String composeFileContent) {
            this.composeFileContent = composeFileContent;
            return this;
        }
    }
}