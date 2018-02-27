package threewks.framework.usermanagement.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import threewks.framework.usermanagement.Role;
import threewks.util.DateTimeUtils;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Set;

@Entity
public class UserInviteLink {

    public static String hash(String code) {
        return DigestUtils.sha256Hex(code);
    }

    @Id
    private String hashedCode;
    @Index
    private String email;
    private Set<Role> roles;
    private Ref<User> issuedBy;
    private OffsetDateTime created;
    private OffsetDateTime expires;
    private boolean redeemed;

    private UserInviteLink() {
    }

    public UserInviteLink(String code, User issuer, String email, Set<Role> roles) {
        this.hashedCode = hash(code);
        this.email = email;
        this.roles = roles == null ? Collections.emptySet() : roles;
        this.issuedBy = Ref.create(issuer);
        this.redeemed = false;
        this.created = DateTimeUtils.now();
        this.expires = created.plusDays(2);
    }

    /**
     * Return a SHA256 hex hash of the code. We don't store the original since this is basically a
     * password.
     *
     * @return the hashed code
     */
    public String getHashedCode() {
        return hashedCode;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User getIssuedBy() {
        return issuedBy != null ? issuedBy.get() : null;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public boolean hasExpired() {
        return expires.isBefore(DateTimeUtils.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserInviteLink that = (UserInviteLink) o;

        return new EqualsBuilder()
            .append(hashedCode, that.hashedCode)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(hashedCode)
            .toHashCode();
    }
}
