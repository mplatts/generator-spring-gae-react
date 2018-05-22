package threewks.framework.usermanagement.service;

import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class InviteUserRequest {
    @NotBlank
    private String email;
    private List<String> roles = new ArrayList<>();

    public InviteUserRequest() {
    }

    public InviteUserRequest(String email, List<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
