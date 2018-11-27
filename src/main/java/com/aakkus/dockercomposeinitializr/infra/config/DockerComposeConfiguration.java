package com.aakkus.dockercomposeinitializr.infra.config;

import com.aakkus.dockercomposeinitializr.domain.model.DockerComposeServiceDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "docker.compose")
public class DockerComposeConfiguration {

    private Set<String> versions;
    private List<DockerComposeServiceDefinition> services;

    public Set<String> getVersions() {
        return versions;
    }

    public void setVersions(Set<String> versions) {
        this.versions = versions;
    }

    public List<DockerComposeServiceDefinition> getServices() {
        return services;
    }

    public void setServices(List<DockerComposeServiceDefinition> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "DockerComposeConfiguration{" +
                "versions=" + versions +
                ", services=" + services +
                '}';
    }
}