package api;

import io.restassured.response.Response;
import utils.model.CreateUserRequest;
import utils.model.CreateUserResponse;
import utils.testdata.BookStoreTestData;

public class GetResponseOfUserAndBooks {
    CreateUserResponse createUserResponse = new CreateUserResponse();
    BookStoreTestData bookStoreTestData = new BookStoreTestData();
    CreateUserRequest createUserRequest = new CreateUserRequest();



    public Response getNewUserResponse() {
        return createUserResponse
    }

}
