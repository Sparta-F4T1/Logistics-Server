package com.logistic.hub.application.port.out.persistence;

import com.logistic.hub.adapter.in.web.response.HubHistoryResponse;
import com.logistic.hub.domain.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubPersistencePort {
  Hub save(Hub hub);

  Page<HubHistoryResponse> findAllBySearch(String search, Pageable pageable);

  Hub findById(Long hubId);

  void delete(Hub hub);

  boolean existsHub(Long hubId);
}
