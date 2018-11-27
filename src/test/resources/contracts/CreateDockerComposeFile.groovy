import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should create docker compose file"

    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url "/api/v1/docker-compose"
        body(
                '''
                {
                    "version": "3.0",
                    "services": [
                        {
                            "containerName": "containerName",
                            "serviceName": "serviceName",
                            "image": "test-image",
                            "ports": [
                               "5000:5000"
                            ],
                            "restartCondition": "on-failure"
                        }
                    ]
                }
            '''
        )
    }

    response {
        status CREATED()
        body(
                version: "3.0"
        )
    }
}