package com.logistic.driver.adapter.out.tsp;

import com.logistic.driver.domain.model.vo.Company;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GreedyTspServiceTest {
  @DisplayName("그리디 알고리즘 테스트")
  @Test
  void greedy_tsp() {
    // given
    List<Company> companyList = createDummyCompanies();
    // when
    List<Company> tspResult = tryTsp(companyList);
    // then
    for (Company c1 : companyList) {
      System.out.println(c1.name());
    }
    System.out.println("------------");
    for (Company c2 : tspResult) {
      System.out.println(c2.name());
    }
  }

  public List<Company> tryTsp(final List<Company> companyList) {
    double startX = 37.4647;
    double startY = 126.8994;

    List<Company> route = new ArrayList<>();
    boolean[] visited = new boolean[companyList.size()];

    route.add(new Company(0L, "Hub", startX, startY));
    visited[0] = true;

    Company currentCompany = new Company(0L, "Hub", startX, startY);

    while (route.size() < companyList.size() + 1) {
      double minDistance = Double.MAX_VALUE;
      int nextIndex = 0;

      for (int i = 0; i < companyList.size(); i++) {
        if (!visited[i]) {
          double dist = calculateDistance(currentCompany.latitude(), currentCompany.longitude(),
              companyList.get(i).latitude(), companyList.get(i).longitude());
          if (dist < minDistance) {
            minDistance = dist;
            nextIndex = i;
          }
        }
      }
      route.add(companyList.get(nextIndex));
      visited[nextIndex] = true;
      currentCompany = companyList.get(nextIndex);
    }

    return route;
  }

  private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    final int R = 6371;
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  private List<Company> createDummyCompanies() {
    List<Company> companyList = new ArrayList<>();
    companyList.add(new Company(1L, "시청", 37.5183, 126.8954)); // 시청
    companyList.add(new Company(2L, "을지로입구", 37.5183, 126.9064)); // 을지로입구
    companyList.add(new Company(3L, "을지로3가", 37.5113, 126.9024)); // 을지로3가
    companyList.add(new Company(4L, "을지로4가", 37.5054, 126.9005)); // 을지로4가
    companyList.add(new Company(5L, "동대문역사문화공원", 37.4979, 126.9076)); // 동대문역사문화공원
    companyList.add(new Company(6L, "신당", 37.4904, 126.9065)); // 신당
    companyList.add(new Company(7L, "상왕십리", 37.4837, 126.8971)); // 상왕십리
    companyList.add(new Company(8L, "왕십리", 37.4756, 126.8980)); // 왕십리
    companyList.add(new Company(9L, "한양대", 37.4647, 126.8994)); // 한양대
    companyList.add(new Company(10L, "성수", 37.4575, 126.9035)); // 성수
    return companyList;
  }
}
