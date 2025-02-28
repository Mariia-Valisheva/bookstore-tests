package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetBooksDetailsResponse {
    private String isbn, title, subTitle, author, publisher, pages, description, website;

    @JsonProperty("publish_date")
    private String publishDate;
}
