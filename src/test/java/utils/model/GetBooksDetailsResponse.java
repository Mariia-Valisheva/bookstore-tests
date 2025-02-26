package utils.model;

import lombok.Data;

@Data
public class GetBooksDetailsResponse {
    private String isbn, title, subTitle, author, publish_date, publisher, pages, description, website;
}
