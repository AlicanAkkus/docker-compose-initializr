//package com.aakkus.dockercomposeinitializr.contract;
////
////import io.restassured.RestAssured;
////import io.restassured.module.mockmvc.RestAssuredMockMvc;
////import org.junit.Before;
////import org.junit.runner.RunWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.boot.web.server.LocalServerPort;
////import org.springframework.test.context.junit4.SpringRunner;
////import org.springframework.web.context.WebApplicationContext;
////
////@RunWith(SpringRunner.class)
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////public class ComposeFileCreateBaseContract {
////
////    @Autowired
////    private WebApplicationContext context;
////
////    @LocalServerPort
////    private Integer port;
////
////    @Before
////    public void doBefore() {
////        RestAssured.baseURI = "http://localhost:" + this.port;
////        RestAssuredMockMvc.webAppContextSetup(this.context);
////    }
////}