// src/main/java/nl/thewally/service/ContentService.java
package nl.thewally.service;

import nl.thewally.database.ContentRepository;
import nl.thewally.database.dto.ContentDto;
import nl.thewally.resource.dto.PostContentDto;
import nl.thewally.mapper.ContentDtoMapper;

import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ContentService {

    private final ContentRepository repository;

    @Inject
    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContentDto storeContent(PostContentDto postContentDto) {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (repository.existsById(id));
        repository.save(ContentDtoMapper.toDynamoUpdatedItem(id, postContentDto));
        return ContentDtoMapper.fromDynamoItem(repository.getContent(id));
    }
}