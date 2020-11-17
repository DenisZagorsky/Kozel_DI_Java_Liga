package com.deniszagorsky.socialnetwork.prototype;

import com.deniszagorsky.socialnetwork.domain.basic.Friendship;
import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.domain.embeddable.FriendshipKey;

import java.util.UUID;

import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.user;

public class FriendshipPrototype {

    public static Friendship friendship() {
        User user = user();
        User friend = user();
        String strFriendId = "f5a15e35-6f2b-45b3-88cf-06acc043fc8c";
        friend.setId(UUID.fromString(strFriendId));

        FriendshipKey friendshipKey = new FriendshipKey();
        friendshipKey.setFirstUserId(user.getId());
        friendshipKey.setSecondUserId(friend.getId());

        return new Friendship(friendshipKey, user, friend,
                true, false);
    }
}
