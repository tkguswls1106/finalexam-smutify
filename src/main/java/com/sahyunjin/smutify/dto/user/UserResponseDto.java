package com.sahyunjin.smutify.dto.user;

import com.sahyunjin.smutify.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
    }
}
