package nl.thewally.database;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;

import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentRepository {
    private final DynamoDbClient client;
    private final String tableName = "content";

    public ContentRepository(DynamoDbClient client) {
        this.client = client;
    }

    public List<Map<String, AttributeValue>> listAll() {
        return client.scan(b -> b.tableName(tableName))
                .items();
    }

    public Map<String, AttributeValue> getContent(String id) {
        return client.getItem(b -> b
                .tableName(tableName)
                .key(Map.of("Id", AttributeValue.builder().s(id).build()))
        ).item();
    }

    public void updateContent(String id, Map<String, AttributeValueUpdate> updates) {
        client.updateItem(b -> b
                .tableName(tableName)
                .key(Map.of("Id", AttributeValue.builder().s(id).build()))
                .attributeUpdates(updates)
        );
    }

    public boolean existsById(String id) {
        Map<String, AttributeValue> item = client.getItem(b -> b
                .tableName(tableName)
                .key(Map.of("Id", AttributeValue.builder().s(id).build()))
        ).item();
        return item != null && !item.isEmpty();
    }

    public void save(Map<String, AttributeValue> item) {
        client.putItem(b -> b
                .tableName(tableName)
                .item(item)
        );
    }

}
