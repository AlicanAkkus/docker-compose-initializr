package com.aakkus.dockercomposeinitializr.contract;

import com.aakkus.dockercomposeinitializr.contract.ComposeFileCreateBaseContract;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.RestAssured.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;

public class ContractVerifierContractTest extends ComposeFileCreateBaseContract {

	@Test
	public void validate_createDockerComposeFile() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"services\":[{\"containerName\":\"containerName\",\"image\":\"test-image\",\"ports\":[\"5000:5000\"],\"restartCondition\":\"on-failure\",\"serviceName\":\"serviceName\"}],\"version\":\"3.0\"}");

		// when:
			Response response = given().spec(request)
					.post("/api/v1/docker-compose");

		// then:
			assertThat(response.statusCode()).isEqualTo(201);
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['version']").isEqualTo("3.0");
	}

}
