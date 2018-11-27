package com.aakkus.dockercomposeinitializr.domain.model;

import java.util.Map;

public class DockerComposeServiceDefinition {

    private String name;
    private String image;
    private String restartCondition;
    private String[] ports;
    private Map<String, String> environments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, String> getEnvironments() {
        return environments;
    }

    public void setEnvironments(Map<String, String> environments) {
        this.environments = environments;
    }
}