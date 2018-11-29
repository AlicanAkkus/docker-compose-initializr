package com.aakkus.dockercomposeinitializr.domain.model;

public class DockerComposeVersionDefinition {

    private String name;
    private String version;

    public DockerComposeVersionDefinition() {
    }

    private DockerComposeVersionDefinition(Builder builder) {
        this.name = builder.name;
        this.version = builder.version;
    }

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {
        private String name;
        private String version;

        private Builder() {
        }

        public DockerComposeVersionDefinition build() {
            return new DockerComposeVersionDefinition(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }
    }
}