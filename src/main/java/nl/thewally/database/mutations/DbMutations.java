package nl.thewally.database.mutations;

import io.quarkus.runtime.StartupEvent;
import nl.thewally.database.ContentRepository;
import nl.thewally.mapper.ContentDtoMapper;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class DbMutations {

    private static final Logger log = Logger.getLogger(DbMutations.class);

    @Inject
    ContentRepository repository;

    void init(@Observes StartupEvent ev) {
        // add created field to all items that do not have it
        repository.listAll().forEach(item -> {
            if (!item.containsKey("created")) {
                log.info("Mutate item, because missing value: " + item);
                String id = item.get("Id").s();
                Map<String, AttributeValue> updatedItem = new HashMap<>(item);
                updatedItem.put("created", AttributeValue.builder().s(java.time.Instant.now().toString()).build());
                repository.updateContent(id, ContentDtoMapper.toDynamoUpdatedItem(updatedItem));
            }
        });
    }
}
