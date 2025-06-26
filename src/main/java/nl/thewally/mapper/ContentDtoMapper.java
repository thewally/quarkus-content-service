package nl.thewally.mapper;

import nl.thewally.database.dto.ContentDto;
import nl.thewally.resource.dto.PostContentDto;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;

import java.util.Map;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentDtoMapper {
    public static ContentDto fromDynamoItem(Map<String, AttributeValue> item) {
        return new ContentDto(
                item.get("Id").s(),
                item.get("title").s(),
                item.get("content").s(),
                item.get("created").s(),
                item.get("tags") != null ? item.get("tags").ss() : null

        );
    }

    public static Map<String, AttributeValue> toDynamoUpdatedItem(String id, PostContentDto contentDto) {
        return Map.of(
                "Id", AttributeValue.builder().s(id).build(),
                "title", AttributeValue.builder().s(contentDto.title()).build(),
                "content", AttributeValue.builder().s(contentDto.content()).build(),
                "created", AttributeValue.builder().s(java.time.Instant.now().toString()).build(),
                "tags", contentDto.tags() != null ? AttributeValue.builder().ss(contentDto.tags()).build() : null
        );
    }

    public static Map<String, AttributeValueUpdate> toDynamoUpdatedItem(Map<String, AttributeValue> items) {
        return items.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .filter(e -> !e.getKey().equals("Id"))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> AttributeValueUpdate.builder()
                                .value(e.getValue())
                                .action(AttributeAction.PUT)
                                .build()
                ));
    }
}
