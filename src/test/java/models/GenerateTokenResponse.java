package models;

import lombok.Data;

@Data
public class GenerateTokenResponse {
    private String token, expires, status, result;
}
