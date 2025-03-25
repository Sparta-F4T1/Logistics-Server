package com.logistic.notification.adapter.out.persistence.repository;

import com.logistic.notification.adapter.out.persistence.QSlackMessageEntity;
import com.logistic.notification.adapter.out.persistence.SlackMessageEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackMessageQueryDslRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QSlackMessageEntity slackMessageEntity = QSlackMessageEntity.slackMessageEntity;

  public Optional<SlackMessageEntity> findSlackMessage(Long id) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(slackMessageEntity)
            .where(slackMessageEntity.id.eq(id))
            .where(slackMessageEntity.isSent.eq(false))
            .fetchOne()
    );
  }

  public Page<SlackMessageEntity> searchSlackMessage(String recipient,
                                                     Pageable pageable)
  {
    QueryResults<SlackMessageEntity> results = jpaQueryFactory
        .selectFrom(slackMessageEntity)
        .where(slackMessageEntity.recipient.eq(recipient))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(pageable.getSort().stream()
            .map(order -> order.isAscending() ? slackMessageEntity.id.asc() : slackMessageEntity.id.desc())
            .toArray(OrderSpecifier[]::new))
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

}
