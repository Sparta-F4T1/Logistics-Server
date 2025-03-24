package com.logistic.driver.adapter.out.clustering;

import com.logistic.driver.domain.model.vo.Company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import smile.clustering.KMeans;

class DriverClusteringAdapterTest {

  @DisplayName("클러스터링 테스트")
  @Test
  void cluster() {
    List<Company> companies = createDummyCompanies();
    List<String> driverIds = createDummyDrivers();
    createClustering(companies, driverIds);
  }

  public void createClustering(final List<Company> companyList, final List<String> driverList) {
    final int clusterSize = driverList.size();
    double[][] geoPointArray = new double[companyList.size()][2];
    for (int i = 0; i < companyList.size(); i++) {
      Company company = companyList.get(i);
      geoPointArray[i][0] = company.latitude();
      geoPointArray[i][1] = company.longitude();
    }

    Map<String, List<Company>> clusterMap = new HashMap<>();

    KMeans kMeans = KMeans.fit(geoPointArray, clusterSize);
    int[] group = kMeans.y;

    for (int i = 0; i < group.length; i++) {
      int index = group[i];
      String driverId = driverList.get(index);
      clusterMap.computeIfAbsent(driverId, k -> new ArrayList<>())
          .add(companyList.get(i));
    }

    // 각 driverId와 그에 할당된 회사 이름 출력
    clusterMap.forEach((driverId, companies) -> {
      String companyNames = companies.stream()
          .map(Company::name) // Company 클래스에서 name을 가져온다고 가정
          .reduce((name1, name2) -> name1 + "," + name2)
          .orElse("");
      System.out.println(driverId + " : [" + companyNames + "]");
    });
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
    companyList.add(new Company(11L, "건대입구", 37.4495, 126.8996)); // 건대입구
    companyList.add(new Company(12L, "구의", 37.4421, 126.9053)); // 구의
    companyList.add(new Company(13L, "아차산", 37.4342, 126.9195)); // 아차산
    companyList.add(new Company(14L, "강변", 37.4280, 126.9244)); // 강변
    companyList.add(new Company(15L, "잠실나루", 37.4211, 126.9317)); // 잠실나루
    companyList.add(new Company(16L, "잠실", 37.5106, 127.0977)); // 잠실
    companyList.add(new Company(17L, "강남", 37.4979, 127.0276)); // 강남
    companyList.add(new Company(18L, "교대", 37.4905, 127.0415)); // 교대
    companyList.add(new Company(19L, "서초", 37.4845, 127.0552)); // 서초
    companyList.add(new Company(20L, "방배", 37.4756, 127.0649)); // 방배
    companyList.add(new Company(21L, "사당", 37.4702, 127.0711)); // 사당
    companyList.add(new Company(22L, "낙성대", 37.4616, 127.0804)); // 낙성대
    companyList.add(new Company(23L, "서울대입구", 37.4551, 127.0915)); // 서울대입구
    companyList.add(new Company(24L, "신림", 37.4473, 127.0995)); // 신림
    companyList.add(new Company(25L, "서울역", 37.4392, 127.1092)); // 서울역
    companyList.add(new Company(26L, "회기", 37.4320, 127.1220)); // 회기
    companyList.add(new Company(27L, "경희대", 37.4233, 127.1350)); // 경희대
    companyList.add(new Company(28L, "외대앞", 37.4156, 127.1420)); // 외대앞
    companyList.add(new Company(29L, "신이문", 37.4067, 127.1500)); // 신이문
    companyList.add(new Company(30L, "용두", 37.3983, 127.1579)); // 용두
    companyList.add(new Company(31L, "한양대", 37.3898, 127.1647)); // 한양대
    companyList.add(new Company(32L, "청량리", 37.3815, 127.1739)); // 청량리
    companyList.add(new Company(33L, "신촌", 37.3736, 127.1829)); // 신촌
    companyList.add(new Company(34L, "홍대입구", 37.3649, 127.1901)); // 홍대입구
    companyList.add(new Company(35L, "합정", 37.3576, 127.1963)); // 합정
    companyList.add(new Company(36L, "상수", 37.3493, 127.2034)); // 상수
    companyList.add(new Company(37L, "광흥창", 37.3404, 127.2107)); // 광흥창
    companyList.add(new Company(38L, "대흥", 37.3317, 127.2181)); // 대흥
    companyList.add(new Company(39L, "공덕", 37.3224, 127.2255)); // 공덕
    companyList.add(new Company(40L, "마포", 37.3130, 127.2329)); // 마포
    companyList.add(new Company(41L, "홍대입구", 37.3050, 127.2380)); // 홍대입구
    companyList.add(new Company(42L, "서강대", 37.2965, 127.2454)); // 서강대
    companyList.add(new Company(43L, "대림", 37.2874, 127.2528)); // 대림
    companyList.add(new Company(44L, "구로디지털단지", 37.2792, 127.2602)); // 구로디지털단지
    companyList.add(new Company(45L, "신도림", 37.2709, 127.2676)); // 신도림
    companyList.add(new Company(46L, "고척", 37.2626, 127.2750)); // 고척
    companyList.add(new Company(47L, "가산디지털단지", 37.2544, 127.2824)); // 가산디지털단지
    companyList.add(new Company(48L, "문래", 37.2462, 127.2898)); // 문래
    companyList.add(new Company(49L, "당산", 37.2381, 127.2971)); // 당산
    companyList.add(new Company(50L, "영등포", 37.2300, 127.3045)); // 영등포
    companyList.add(new Company(51L, "신길", 37.2219, 127.3119)); // 신길
    return companyList;
  }


  // 더미 배달원 데이터 생성
  private List<String> createDummyDrivers() {
    List<String> driverList = new ArrayList<>();
    driverList.add(("driver1"));
    driverList.add(("driver2"));
    driverList.add(("driver3"));
    driverList.add(("driver4"));
    driverList.add(("driver5"));
    driverList.add(("driver6"));
    driverList.add(("driver7"));
    driverList.add(("driver8"));
    driverList.add(("driver9"));
    driverList.add(("driver10"));
    return driverList;
  }
}