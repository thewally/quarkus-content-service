# quarkus-content-service

## Prerequisites
- Java 17
- Maven
- AWS account
- AWS CLI installed (see https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
- AWS SAM CLI installed (see https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
- Configured AWS CLI (via aws configure) - access & secret key configured for your AWS account


## How to run locally
1. ```docker compose up```
---
2. ```aws dynamodb create-table   --table-name content   --attribute-definitions AttributeName=Id,AttributeType=S   --key-schema AttributeName=Id,KeyType=HASH   --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1   --endpoint-url http://localhost:8000```
---
3. ```mvn package -Dnative -Dquarkus.native.container-build=true```
---
4. ```sam local start-api --docker-network local-dynamodb --skip-pull-image --profile default --parameter-overrides 'ParameterKey=StageName,ParameterValue=local' --template target/sam.native.yaml```
