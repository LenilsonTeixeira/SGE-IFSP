package com.ifsp.edu.hto.sge.atividades.repository;

import com.ifsp.edu.hto.sge.atividades.entity.Atividade;
import com.ifsp.edu.hto.sge.atividades.entity.AtividadeContabilizada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

public class AtividadeRepositoryImpl implements AtividadeRepositoryCustom {


	private final MongoTemplate mongoTemplate;

	@Autowired
	public AtividadeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<AtividadeContabilizada> aggregate() {
		GroupOperation groupOperation = getGroupOperation();
		ProjectionOperation projectOperation = getProjectOperation();
		return mongoTemplate
				.aggregate(Aggregation.newAggregation(groupOperation,projectOperation),
						Atividade.class, AtividadeContabilizada.class)
				.getMappedResults();
	}

	private GroupOperation getGroupOperation(){
		return group("nome")
				.last("nome").as("nome")
				.count().as("total");
	}

	private ProjectionOperation getProjectOperation(){
		return project("nome", "total")
				.and("atividadeContabilizada").previousOperation();
	}

}
