package models;

import lombok.Data;

import java.util.List;

@Data
public class GetUserInfoResponse {
    private String userId, username;
    private List<GetBooksDetailsResponse> books;
}
