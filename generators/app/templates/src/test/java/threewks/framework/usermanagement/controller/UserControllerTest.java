package threewks.framework.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.framework.usermanagement.model.User;
import threewks.testinfra.BaseControllerTest;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static threewks.framework.usermanagement.model.User.byEmail;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void user_WillGetUserByName() throws Exception {
        given(userService.get("bob")).willReturn(of(byEmail("bob@email.com", "password")));

        UserDetails userDetails = new AuthUser("id", "bob", "password", emptyList());

        mvc.perform(
            get("/api/users/me").contentType(APPLICATION_JSON).with(user(userDetails)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is("bob@email.com")));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void user_WillGetUserByUserId() throws Exception {
        given(userService.getById("userId")).willReturn(of(byEmail("bob@email.com", "password")));

        mvc.perform(
            get("/api/users/userId"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is("bob@email.com")));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void saveUser_WillUpdateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        User user = byEmail("email", "password");

        given(userService.update(eq("user-id"), refEq(request))).willReturn(user);

        mvc.perform(
            put("/api/users/user-id")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists());

        verify(userService).update(eq("user-id"), refEq(request));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void list_WillListUsers() throws Exception {
        given(userService.list()).willReturn(asList(byEmail("email", "password")));

        mvc.perform(
            get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }
}
