package com.sahyunjin.smutify.dto.user;

import com.sahyunjin.smutify.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDto {

    private String username;

    @Builder
    public UserLoginRequestDto(String username) {
        this.username = username;
    }

    public User toEntity() {
        return User.UserAuthBuilder()
                .username(username)
                .build();
    }
}
