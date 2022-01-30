package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.inner.InnerUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtil {

    public static List<InnerUser> fromDtos(List<User> users) {
        log.info("UserUtil fromDtos - {}", users);
        return users.stream()
                .map(UserUtil::fromDto)
                .collect(Collectors.toList());
    }

    public static InnerUser fromDto(User user) {
        log.info("UserUtil fromDto - {}", user);
        var innerUser = new InnerUser();
        if (user != null) {
            innerUser.setId(user.getId());
            innerUser.setFirstName(user.getFirstName());
            innerUser.setIsBot(user.getIsBot());
            innerUser.setLastName(user.getLastName());
            innerUser.setUserName(user.getUserName());
            innerUser.setLanguageCode(user.getLanguageCode());
            innerUser.setCanJoinGroups(user.getCanJoinGroups());
            innerUser.setCanReadAllGroupMessages(user.getCanReadAllGroupMessages());
            innerUser.setSupportInlineQueries(user.getSupportInlineQueries());
        }
        return innerUser;
    }
}
