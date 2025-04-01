package com.logistic.driver.adapter.out.optimization.tsp;

import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Hub;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GreedyTspService {

  public List<Company> tryTsp(final List<Company> companyList, final Hub hub) {
    double startX = hub.latitude();
    double startY = hub.longitude();

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
}
