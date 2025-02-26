package utils.model;

import lombok.Data;

import java.util.List;

@Data
public class AddBooksToUserRequest {
    private String userId;
    private List<CollectionOfIsbnsModel> collectionOfIsbns;
}
