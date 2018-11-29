//package com.aakkus.dockercomposeinitializr.infra;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DockerComposeFileCreatorConfiguration {
//
//    @Bean
//    YAMLFactory yamlFactory() {
//        return new YAMLFactory();
//    }
//
//    @Bean
//    @Qualifier("dockerComposeFileMapper")
//    ObjectMapper objectMapper(YAMLFactory yamlFactory) {
//        ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        return objectMapper;
//    }
//}