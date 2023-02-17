package io.simpolor.boardcomment.model;

import io.simpolor.boardcomment.repository.entity.User;
import lombok.Getter;
import lombok.Setter;

public class UserDto {

    @Getter
    @Setter
    public static class UserSummary {

        Long id;
        String nickname;

        public static UserSummary of(User user){
            UserSummary userSummary = new UserSummary();
            userSummary.setId(user.getUserId());
            userSummary.setNickname(user.getNickname());

            return userSummary;
        }
    }
}
