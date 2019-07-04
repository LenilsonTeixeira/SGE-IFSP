package com.ifsp.edu.hto.sge.contratos.entity;

import com.ifsp.edu.hto.sge.contratos.enums.SituacaoContrato;
import com.ifsp.edu.hto.sge.contratos.enums.StatusTermino;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "contrato")
@Setter
@Getter
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "numero_contrato")
    private String numeroContrato;

    @OneToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @NotNull
    @Column(name = "horas_semanais")
    private Integer horasSemanais;

    @NotNull
    @Column(name = "salario")
    private BigDecimal salario;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicial")
    private Date dataInicial;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_final")
    private Date dataFinal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_contrato")
    private SituacaoContrato situacaoContrato;

    @Column(name = "status_termino")
    private StatusTermino statusTermino;
}
