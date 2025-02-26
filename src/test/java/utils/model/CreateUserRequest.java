package utils.model;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String userName, password;
}
