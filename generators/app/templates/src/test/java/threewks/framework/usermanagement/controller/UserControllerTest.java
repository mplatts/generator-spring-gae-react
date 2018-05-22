package threewks.framework.usermanagement.controller;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.service.UserService;
import threewks.testinfra.BaseControllerIntegrationTest;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.when;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static threewks.framework.usermanagement.model.User.byEmail;

public class UserControllerTest extends BaseControllerIntegrationTest {

    @MockBean
    private UserService userService;

    @Test
    public void user_WillGetUserByCurrentPrincipal() throws Exception {
        when(userService.getById("id")).thenReturn(of(byEmail("bob@email.com", "password")));
        UserDetails userDetails = new AuthUser("id", "bob", "password", emptyList());

        mvc.perform(
            get("/api/users/me").contentType(APPLICATION_JSON).with(user(userDetails)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("email", is("bob@email.com")));
    }

    @Test
    public void user_thenReturnNoContentWhenNoAuthedUser() throws Exception {
        mvc.perform(
            get("/api/users/me").contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void saveUser_WillUpdateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest()
            .setName("First Last")
            .setEmail("email@email.org");
        User user = byEmail("email", "password");

        when(userService.update(eq("user-id"), refEq(request))).thenReturn(user);

        mvc.perform(
            put("/api/users/user-id")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists());

        verify(userService).update(eq("user-id"), refEq(request));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void saveUser_WillFail_whenMinimumFieldsNotSupplied() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();

        mvc.perform(
            put("/api/users/user-id")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("error").value("Invalid request"))
            .andExpect(jsonPath("messages", containsInAnyOrder(
                "email may not be empty",
                "name may not be empty"
            )));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void saveUser_WillFail_whenInvalidEmail() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest()
            .setName("First Last")
            .setEmail("THIS IS NOT AN EMAIL");

        mvc.perform(
            put("/api/users/user-id")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("error").value("Invalid request"))
            .andExpect(jsonPath("messages", containsInAnyOrder(
                "email not a well-formed email address"
            )));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void list_WillListUsers() throws Exception {
        when(userService.list()).thenReturn(asList(byEmail("email", "password")));

        mvc.perform(
            get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }
}
