package com.project.tgdiscountservice.model.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerUser {

    private Long id; ///< Unique identifier for this user or bot

    private String firstName; ///< User‘s or bot’s first name

    private Boolean isBot; ///< True, if this user is a bot

    private String lastName; ///< Optional. User‘s or bot’s last name

    private String userName; ///< Optional. User‘s or bot’s username

    private String languageCode; ///< Optional. IETF language tag of the user's language

    private Boolean canJoinGroups; ///< Optional. True, if the bot can be invited to groups. Returned only in getMe.

    private Boolean canReadAllGroupMessages; ///< Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.

    private Boolean supportInlineQueries; ///< Optional. True, if the bot supports inline queries. Returned only in getMe.
}
