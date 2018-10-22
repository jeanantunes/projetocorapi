package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodPlanoInfo;

/*201810221655 - esert - COR-932:API - Novo GET /planoinfo */
@org.springframework.stereotype.Repository
public interface PlanoInfoDAO extends Repository<TbodPlanoInfo, Long> {

	@Query("from TbodPlanoInfo where ativo = :Ativo order by tipoSegmento")
	List<TbodPlanoInfo> findByAtivo(@Param("Ativo") String Ativo);
	
}
