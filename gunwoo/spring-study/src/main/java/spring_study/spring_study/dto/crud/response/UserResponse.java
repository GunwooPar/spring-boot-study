package spring_study.spring_study.dto.crud.response;

import spring_study.spring_study.domain.User;

public record UserResponse(
    Long id,
    String username
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }
}
