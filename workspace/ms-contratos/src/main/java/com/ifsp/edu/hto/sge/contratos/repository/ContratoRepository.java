package com.ifsp.edu.hto.sge.contratos.repository;

import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
