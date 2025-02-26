package utils.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserResponse {
    private String userID, username;
    private List<String> books;
}
