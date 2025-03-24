package com.logistic.driver.adapter.out.optimization.clustering;

import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.vo.Company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import smile.clustering.KMeans;

@Service
public class KMeansClusteringService {

  public Map<String, List<Company>> createClustering(final List<Company> companyList, final List<Driver> driverList) {
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
      int driverIndex = group[i];
      Driver driver = driverList.get(driverIndex);
      String driverId = driver.getId();
      clusterMap.computeIfAbsent(driverId, k -> new ArrayList<>())
          .add(companyList.get(i));
    }

    return clusterMap;
  }
}
