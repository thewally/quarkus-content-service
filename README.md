# quarkus-content-service

## How to run locally
1. ```docker compose up```
---
2. ```aws dynamodb create-table   --table-name content   --attribute-definitions AttributeName=Id,AttributeType=S   --key-schema AttributeName=Id,KeyType=HASH   --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1   --endpoint-url http://localhost:8000```
---
3. ```mvn package -Dnative -Dquarkus.native.container-build=true```
---
4. ```sam local start-api --docker-network local-dynamodb --skip-pull-image --profile default --parameter-overrides 'ParameterKey=StageName,ParameterValue=local' --template target/sam.native.yaml```
