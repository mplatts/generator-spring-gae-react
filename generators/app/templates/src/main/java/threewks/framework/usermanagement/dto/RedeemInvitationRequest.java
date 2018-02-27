package threewks.framework.usermanagement.dto;

public class RedeemInvitationRequest extends threewks.util.ValueObject {
    private String name;
    private String password;

    public RedeemInvitationRequest() {
    }

    public RedeemInvitationRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
