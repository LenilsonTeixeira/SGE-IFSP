package com.ifsp.edu.sge.analytics.repository;

import com.ifsp.edu.sge.analytics.model.ContratoMetricInfo;

public interface ContratoRepository {
    void save(ContratoMetricInfo contratoMetricInfo);
    ContratoMetricInfo find(String id);
    void update(ContratoMetricInfo contratoMetricInfo);
    void delete(String id);
}
