package nl.thewally.database.dto;

import java.util.List;

public record ContentDto(
        String Id,
        String title,
        String content,
        String created,
        List<String> tags
) {
    public static ContentDto createWithId(String id, String title, String content, String created, List<String> tags) {
        return new ContentDto(id, title, content, created, tags);
    }
}