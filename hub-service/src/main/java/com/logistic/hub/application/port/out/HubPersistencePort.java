package com.logistic.hub.application.port.out;

import com.logistic.hub.adaptor.in.web.response.HubHistoryResponse;
import com.logistic.hub.domain.Hub;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubPersistencePort {
  Optional<Hub> save(Hub hub);

  Page<HubHistoryResponse> findAllBySearch(String search, Pageable pageable);

  Optional<Hub> findById(Long hubId);

  void delete(Hub hub);

}
