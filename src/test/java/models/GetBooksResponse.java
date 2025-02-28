package models;

import lombok.Data;

import java.util.List;

@Data
public class GetBooksResponse {
    public List<GetBooksDetailsResponse> books;
}
