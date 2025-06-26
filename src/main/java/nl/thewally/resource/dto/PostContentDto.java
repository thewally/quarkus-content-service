package nl.thewally.resource.dto;

import java.util.List;

public record PostContentDto(
        String title,
        String content,
        List<String> tags
) {
}
