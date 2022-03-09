package net.woolgens.library.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.woolgens.library.common.http.HttpRequester;
import net.woolgens.library.common.http.HttpResponse;
import net.woolgens.library.common.http.OkHttpRequester;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class HttpRequesterTest {

    private HttpRequester requester = new OkHttpRequester("https://jsonplaceholder.typicode.com/");

    @Test
    public void testGetRequest()  {
        HttpResponse<HttpTestUser> user = requester.get("users/1", HttpTestUser.class);
        assertEquals(user.getBody().getId(), 1);
    }

    @Test
    public void testGetAllRequest()  {
        HttpResponse<List<HttpTestUser>> response = requester.get("users", ArrayList.class);
        assertEquals(response.isSuccess(), true);
    }

    @Test
    public void testPostRequest() {
        HttpTestPost content = new HttpTestPost(1, 2, "Testing", "Testing");
        HttpResponse<HttpTestPost> post = requester.post("posts", HttpTestPost.class, content);
        assertEquals(post.getBody().getTitle(), "Testing");
    }

    @Test
    public void testMapper() {
        HttpRequester requester = new OkHttpRequester("https://testing.url/");
        requester.setMapper(exception -> {
            assertEquals(UnknownHostException.class, exception.getClass());
        });
        requester.get("notworkingurl", HttpTestUser.class);

    }

    @Data
    public class HttpTestUser {

        private int id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class HttpTestPost {

        private int userId;
        private int id;
        private String title;
        private String body;
    }
}
