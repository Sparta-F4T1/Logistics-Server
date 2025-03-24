package com.logistic.hub.application.port.out.persistence;

import com.logistic.hub.application.service.dto.HubHistoryDto;
import com.logistic.hub.domain.Hub;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubPersistencePort {
  Hub save(Hub hub);

  Page<HubHistoryDto> findAllBySearch(String search, Pageable pageable);

  Hub findById(Long hubId);

  void delete(Hub hub, String userId);

  boolean existsHub(Long hubId);

  List<Hub> findAll(List<Long> ids);
}
