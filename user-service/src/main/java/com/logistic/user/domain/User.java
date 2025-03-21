package com.logistic.user.domain;

import com.logistic.user.domain.command.UserForUpdate;
import com.logistic.user.domain.vo.Email;
import com.logistic.user.domain.vo.Name;
import com.logistic.user.domain.vo.Password;
import com.logistic.user.domain.vo.UserId;
import com.logistic.user.domain.vo.UserRole;
import com.logistic.user.domain.vo.UserStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
  private final UserId userId;
  private Name name;
  private Password password;
  private Email slackAccount;
  private UserRole role;
  private UserStatus status;

  public static User create(
      String userId,
      String name,
      String password,
      String slackAccount,
      Long roleId,
      String roleName) {
    return User.builder()
        .userId(UserId.of(userId))
        .name(Name.of(name))
        .password(Password.ofPlainPassword(password))
        .slackAccount(Email.of(slackAccount))
        .role(UserRole.of(roleId, roleName))
        .status(UserStatus.ACTIVE)
        .build();
  }

  public void update(UserForUpdate userForUpdate) {
    changePassword(userForUpdate.password());
    changeSlackAccount(userForUpdate.slackAccount());
  }

  public void updateHashedPassword(String hashedPassword) {
    this.password = Password.ofHashedPassword(hashedPassword);
  }

  public void changePassword(String newPassword) {
    this.password = newPassword != null ? Password.ofPlainPassword(newPassword) : null;
  }

  public void changeSlackAccount(String newSlackAccount) {
    this.slackAccount = newSlackAccount != null ? new Email(newSlackAccount) : null;
  }

  public void changeStatus(UserStatus newStatus) {
    this.status = UserStatus.transition(this.status, newStatus);
  }

  public void activate() {
    changeStatus(UserStatus.ACTIVE);
  }

  public void deactivate() {
    changeStatus(UserStatus.INACTIVE);
  }

  public void lock() {
    changeStatus(UserStatus.LOCKED);
  }

  public void activateIfPending() {
    if (this.status.isPending()) {
      activate();
    }
  }

  public void changeRole(Long roleId, String roleName) {
    this.role = new UserRole(roleId, roleName);
  }

  public boolean isUsable() {
    return this.status.isUsable();
  }

  public void changeName(String newName) {
    this.name = new Name(newName);
  }

  public boolean isActive() {
    return this.status == UserStatus.ACTIVE;
  }
}