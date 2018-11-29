package com.aakkus.dockercomposeinitializr.infra.config;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeVersionDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "docker.compose")
public class DockerComposeConfiguration {

    private List<DockerComposeVersionDefinition> versions;
    private List<DockerComposeServiceDefinition> services;

    public List<DockerComposeVersionDefinition> getVersions() {
        return versions;
    }

    public void setVersions(List<DockerComposeVersionDefinition> versions) {
        this.versions = versions;
    }

    public List<DockerComposeServiceDefinition> getServices() {
        return services;
    }

    public void setServices(List<DockerComposeServiceDefinition> services) {
        this.services = services;
    }
}