package nl.thewally.resource;

import nl.thewally.database.ContentRepository;
import nl.thewally.database.dto.ContentDto;
import nl.thewally.mapper.ContentDtoMapper;
import nl.thewally.resource.dto.PostContentDto;
import nl.thewally.service.ContentService;
import org.jboss.logging.Logger;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/content")
public class ContentResource {
    private static final Logger logger = Logger.getLogger(ContentResource.class);

    @Inject
    ContentRepository contentRepository;

    @Inject
    ContentService contentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContentDto> getContent() {
        logger.info("Received request to list all content");
        return contentRepository.listAll().stream()
                .map(ContentDtoMapper::fromDynamoItem)
                .toList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ContentDto postContent(PostContentDto postContentDto) {
        logger.info("Received request to post content: " + postContentDto);
        return contentService.storeContent(postContentDto);
    }
}
