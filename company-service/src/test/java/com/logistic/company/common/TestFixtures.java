package com.logistic.company.common;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.ArrayList;
import java.util.List;

public class TestFixtures {

  public static Hub createHub(Long hubId) {
    List<String> userIds = List.of("user1", "user2", "user3");
    return new Hub(hubId, "타입", "이름", "도로명", "지번", 30.0, 40.0, userIds);
  }

  public static Gps createGps() {
    return new Gps("road", "jibun", 30.0, 40.0);
  }

  public static User createUser(String userId) {
    return new User(userId, "이름");
  }

  public static List<User> createUserList() {
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      userList.add(createUser("user" + i));
    }
    return userList;
  }

  public static Passport createPassport() {
    return new Passport(createUserInfo(), null);
  }

  public static UserInfo createUserInfo() {
    return new UserInfo("master", "MASTER_ADMIN", null);
  }
}
