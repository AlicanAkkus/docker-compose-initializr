package com.aakkus.dockercomposeinitializr.infra.adapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DockerComposeService {

    @JsonIgnore
    private String serviceName;

    @JsonProperty("container_name")
    private String containerName;
    private String image;
    private String command;
    private String restart;
    private String[] ports;
    private Map<String, String> environment;

    public DockerComposeService() {
    }

    private DockerComposeService(Builder builder) {
        this.serviceName = builder.serviceName;
        this.containerName = builder.containerName;
        this.image = builder.image;
        this.command = builder.command;
        this.restart = builder.restart;
        this.ports = builder.ports;
        this.environment = builder.environment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRestart() {
        return restart;
    }

    public void setRestart(String restart) {
        this.restart = restart;
    }

    public String[] getPorts() {
        return ports;
    }

    public void setPorts(String[] ports) {
        this.ports = ports;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public static final class Builder {
        private String serviceName;
        private String containerName;
        private String image;
        private String command;
        private String restart;
        private String[] ports;
        private Map<String, String> environment;

        private Builder() {
        }

        public DockerComposeService build() {
            return new DockerComposeService(this);
        }

        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder containerName(String containerName) {
            this.containerName = containerName;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }


        public Builder command(String command) {
            this.command = command;
            return this;
        }

        public Builder restart(String restart) {
            this.restart = restart;
            return this;
        }

        public Builder ports(String[] ports) {
            this.ports = ports;
            return this;
        }

        public Builder environment(Map<String, String> environment) {
            this.environment = environment;
            return this;
        }
    }
}