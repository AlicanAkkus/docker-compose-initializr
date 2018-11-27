package com.aakkus.dockercomposeinitializr.infra.rest.response;

public class DockerComposeResponse {

    private String version;
    private String composeFileContent;

    public DockerComposeResponse() {
    }

    private DockerComposeResponse(Builder builder) {
        this.version = builder.version;
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

    public String getComposeFileContent() {
        return composeFileContent;
    }

    public void setComposeFileContent(String composeFileContent) {
        this.composeFileContent = composeFileContent;
    }

    public static final class Builder {
        private String version;
        private String composeFileContent;

        private Builder() {
        }

        public DockerComposeResponse build() {
            return new DockerComposeResponse(this);
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder composeFileContent(String composeFileContent) {
            this.composeFileContent = composeFileContent;
            return this;
        }
    }
}
