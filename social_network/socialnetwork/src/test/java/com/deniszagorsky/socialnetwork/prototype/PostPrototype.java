package com.deniszagorsky.socialnetwork.prototype;

import com.deniszagorsky.socialnetwork.domain.basic.Post;
import com.deniszagorsky.socialnetwork.dto.PostCreationDto;
import com.deniszagorsky.socialnetwork.dto.PostEditDto;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.deniszagorsky.socialnetwork.prototype.UserPrototype.user;
import static java.util.UUID.fromString;

public class PostPrototype {

    public static PostEditDto postEditDto() {
        return new PostEditDto("What a wonderful day!");
    }

    public static Post post() {
        PostCreationDto postCreationDto = postCreationDto();

        String strId = "03900221-665a-4727-b527-bd6ae4ae6038";
        UUID id = fromString(strId);

        Post post = new Post();
        post.setBody(postCreationDto.getBody());
        post.setUser(user());
        post.setDatePublished(LocalDateTime.now());
        post.setId(id);

        return post;
    }

    public static PostCreationDto postCreationDto() { return new PostCreationDto("What a wonderful day!"); }

}
