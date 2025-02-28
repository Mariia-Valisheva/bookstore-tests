package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private String userId, username, password, token, expires;

    @JsonProperty("created_date")
    private String createdDate;

    private Boolean isActive;

}
