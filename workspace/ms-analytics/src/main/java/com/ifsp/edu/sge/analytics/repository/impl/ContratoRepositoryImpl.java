package com.ifsp.edu.sge.analytics.repository.impl;

import com.ifsp.edu.sge.analytics.model.ContratoMetricInfo;
import com.ifsp.edu.sge.analytics.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class ContratoRepositoryImpl implements ContratoRepository {

    private static final String KEY = "ContratoMetric";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ContratoMetricInfo> hashOperations;

    @Autowired
    public ContratoRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(ContratoMetricInfo contratoMetricInfo) {
        hashOperations.put(KEY,contratoMetricInfo.getId(),contratoMetricInfo);
    }

    @Override
    public ContratoMetricInfo find(String id) {
        return hashOperations.get(KEY, id);
    }

    @Override
    public void update(ContratoMetricInfo contratoMetricInfo) {
        hashOperations.put(KEY,contratoMetricInfo.getId(),contratoMetricInfo);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }
}
