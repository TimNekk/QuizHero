package timnekk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Category {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("clues_count")
    private int cluesCount;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getCluesCount() {
        return cluesCount;
    }
}