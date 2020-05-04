package com.health.point.repository.search;

import com.health.point.domain.Bloodpressure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Bloodpressure} entity.
 */
public interface BloodpressureSearchRepository extends ElasticsearchRepository<Bloodpressure, Long> {
}
