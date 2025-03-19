package com.logistic.delivery.adaptor.out.persistence.mapper;

import com.logistic.delivery.adaptor.out.persistence.DeliveryEntity;
import com.logistic.delivery.adaptor.out.persistence.value.DistanceValue;
import com.logistic.delivery.adaptor.out.persistence.value.TimeValue;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.vo.Distance;
import com.logistic.delivery.domain.vo.Time;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryPersistenceMapper {
  DeliveryEntity toEntity(Delivery domain);

  default TimeValue map(Time time) {
    return new TimeValue(time.getExpected(), time.getActual());
  }

  default Time map(TimeValue timeValue) {
    return new Time(timeValue.getExpectedTime(), timeValue.getActualTime());
  }

  default DistanceValue map(Distance distance) {
    return new DistanceValue(distance.getExpected(), distance.getActual());
  }

  default Distance map(DistanceValue distanceValue) {
    return new Distance(distanceValue.getExpectedDist(), distanceValue.getActualDist());
  }

  Delivery toDomain(DeliveryEntity entity);

  default Optional<Delivery> toDomain(Optional<DeliveryEntity> entity) {
    return entity.map(this::toDomain);
  }


}