package tests.api;

import org.junit.jupiter.api.Test;
import utils.model.AddBooksToUserRequest;
import utils.model.CreateUserResponse;
import utils.model.GetBooksResponse;


public class AddBooksToUserTest {

    @Test
    void addBooksToUserTest() {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        GetBooksResponse getBooksResponse = new GetBooksResponse();

        AddBooksToUserRequest addBooksToUserRequest = new AddBooksToUserRequest();
        addBooksToUserRequest.setUserId(createUserResponse.getUserID());
       // addBooksToUserRequest.setCollectionOfIsbns(getBooksResponse.getBooks().get(0).getIsbn());

    }
}
