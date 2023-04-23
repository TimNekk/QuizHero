package timnekk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Question {
    @JsonProperty("id")
    private int id;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("question")
    private String value;

    @JsonProperty("value")
    private int difficulty;

    @JsonProperty("airdate")
    private String airDate;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("category_id")
    private int categoryId;

    @JsonProperty("game_id")
    private int gameId;

    @JsonProperty("invalid_count")
    private Integer invalidCount;

    @JsonProperty("category")
    private Category category;

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer.replace("<i>", "").replace("</i>", "");
    }

    public String getValue() {
        return value;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getGameId() {
        return gameId;
    }

    public Integer getInvalidCount() {
        return invalidCount;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Question [id=");
        builder.append(id);
        builder.append(", value=");
        builder.append(value);
        builder.append(", answer=");
        builder.append(answer);
        builder.append("]");
        return builder.toString();
    }
}