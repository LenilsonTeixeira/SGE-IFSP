package com.ifsp.edu.sge.analytics.model;

import com.ifsp.edu.sge.analytics.dto.ContratoMetricInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash("ContratoInfo")
public class ContratoMetricInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private ContratoMetricInfoDto contratoMetricInfoDto;
}
