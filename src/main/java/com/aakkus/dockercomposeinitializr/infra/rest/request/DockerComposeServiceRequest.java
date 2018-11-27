package com.aakkus.dockercomposeinitializr.infra.rest.request;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeService;

public class DockerComposeServiceRequest {

    private String serviceName;
    private String containerName;
    private String image;
    private String restartCondition;
    private String[] ports;

    public DockerComposeServiceRequest() {
    }

    private DockerComposeServiceRequest(Builder builder) {
        this.serviceName = builder.serviceName;
        this.containerName = builder.containerName;
        this.image = builder.image;
        this.restartCondition = builder.restartCondition;
        this.ports = builder.ports;
    }

    public static Builder builder() {
        return new Builder();
    }

    DockerComposeService toModel() {
        return DockerComposeService.builder()
                .serviceName(serviceName)
                .containerName(containerName)
                .image(image)
                .restart(restartCondition)
                .ports(ports)
                .build();
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

    public String getRestartCondition() {
        return restartCondition;
    }

    public void setRestartCondition(String restartCondition) {
        this.restartCondition = restartCondition;
    }

    public String[] getPorts() {
        return ports;
    }

    public void setPorts(String[] ports) {
        this.ports = ports;
    }


    public static final class Builder {
        private String serviceName;
        private String containerName;
        private String image;
        private String restartCondition;
        private String[] ports;

        private Builder() {
        }

        public DockerComposeServiceRequest build() {
            return new DockerComposeServiceRequest(this);
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

        public Builder restartCondition(String restartCondition) {
            this.restartCondition = restartCondition;
            return this;
        }

        public Builder ports(String[] ports) {
            this.ports = ports;
            return this;
        }
    }
}