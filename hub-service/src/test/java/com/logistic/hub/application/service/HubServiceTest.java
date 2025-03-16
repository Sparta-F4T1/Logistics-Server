package com.logistic.hub.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.logistic.hub.adaptor.in.web.response.HubHistoryListResponse;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.HubType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HubServiceTest {

  @Autowired
  private HubService hubService;

  @Test
  @DisplayName("허브 생성")
  void createHub() {

    HubCreateCommand hubCreateCommand = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");

    Hub hub = hubService.createHub(hubCreateCommand);

    assertEquals(HubType.CENTRAL, hub.getHubType());
    assertEquals("도로명주소", hub.getAddress().getRoad());
    assertEquals("지번주소", hub.getAddress().getJibun());

  }

  @Test
  @DisplayName("허브 목록 조회")
  void getHubList() {
    // given
    HubCreateCommand hubCreateCommand1 = new HubCreateCommand("REGIONAL", "부산", "부산 도로명주소", "부산 지번주소");
    HubCreateCommand hubCreateCommand2 = new HubCreateCommand("CENTRAL", "경기남부", "경기남부 도로명주소", "경기남부 지번주소");
    hubService.createHub(hubCreateCommand1);
    hubService.createHub(hubCreateCommand2);

    // when
    HubHistoryListResponse hubs = hubService.getHubList(0, 10, null, null);

    // then
    assertEquals(2, hubs.content().size());
    assertEquals("경기남부", hubs.content().get(0).hubName());

  }

  @Test
  @DisplayName("허브 상세 조회")
  void getHubDetails() {
    // given
    HubCreateCommand hubCreateCommand = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    Hub hub = hubService.createHub(hubCreateCommand);

    // when
    Hub foundHub = hubService.getHubDetails(hub.getId());

    // then
    assertEquals(HubType.CENTRAL, hub.getHubType());
    assertEquals("도로명주소", hub.getAddress().getRoad());
    assertEquals("지번주소", hub.getAddress().getJibun());
  }

  @Test
  @DisplayName("허브 수정")
  void updateHub() {
    // given
    HubCreateCommand hubCreateCommand = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    Hub hub = hubService.createHub(hubCreateCommand);
    HubUpdateCommand updateCommand = new HubUpdateCommand("REGIONAL", "경기북부", "새 도로명주소", "새 지번주소");

    // when
    hubService.updateHub(hub.getId(), updateCommand);
    Hub updatedHub = hubService.getHubDetails(hub.getId());
    // then
    assertEquals(HubType.REGIONAL, updatedHub.getHubType());
    assertEquals("새 도로명주소", updatedHub.getAddress().getRoad());
    assertEquals("새 지번주소", updatedHub.getAddress().getJibun());
  }

  @Test
  @DisplayName("허브 삭제")
  void deleteHub() {
    // given
    HubCreateCommand hubCreateCommand = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    Hub hub = hubService.createHub(hubCreateCommand);

    // when
    hubService.deleteHub(hub.getId());

    // then
    assertThrows(IllegalArgumentException.class, () -> hubService.getHubDetails(hub.getId()));
  }
}