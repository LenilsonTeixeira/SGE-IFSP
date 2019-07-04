package com.ifsp.edu.hto.sge.atividades.repository;

import com.ifsp.edu.hto.sge.atividades.entity.AtividadeContabilizada;

import java.util.List;

public interface AtividadeRepositoryCustom {
	List<AtividadeContabilizada> aggregate();
}
