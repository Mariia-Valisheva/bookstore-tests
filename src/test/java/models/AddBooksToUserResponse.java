package models;

import lombok.Data;

import java.util.List;

@Data
public class AddBooksToUserResponse {
    private List<CollectionOfIsbnsModel> books;
}
