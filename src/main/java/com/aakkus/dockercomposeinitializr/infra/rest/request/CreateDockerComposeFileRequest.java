package com.aakkus.dockercomposeinitializr.infra.rest.request;

import java.util.List;

public class CreateDockerComposeFileRequest {

    private String version;
    private List<String> services;

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
}