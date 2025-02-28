package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserResponse {

    @JsonProperty("userID")
    private String userId;
    private String username, code, message;
    private List<String> books;
}
