package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoCorretoraDAO extends CrudRepository<TbodContratoCorretora, Long>{

	public List<TbodContratoCorretora> findByTbodCorretoraCdCorretora(Long cdCorretora);

	public List<TbodContratoCorretora> findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModelo(Long cdCorretora, Long cdContratoModelo); //201809111820 - esert - apos COR-752 - alter TBOD_CONTRATO_CORRETORA este find passa retornar lista
	
	//public List<TbodContratoCorretora> findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(Long cdCorretora, Long cdContratoModelo1, Long cdContratoModelo2); //201809111820 - esert - apos COR-752 - alter TBOD_CONTRATO_CORRETORA este find passa retornar lista
	
	public List<TbodContratoCorretora> findByCdContratoCorretoraAndTbodCorretoraCdCorretora(Long cdContratoCorretora, Long cdCorretora); //201809122219 - esert - apos COR-752 - alter TBOD_CONTRATO_CORRETORA este find passa retornar lista

	@Query("SELECT contrato FROM TbodContratoCorretora contrato " +
			"WHERE contrato.tbodCorretora.cdCorretora = :cdCorretora " +
			"AND (contrato.tbodContratoModelo.cdContratoModelo = :cdContratoModelo1 " +
			"OR contrato.tbodContratoModelo.cdContratoModelo = :cdContratoModelo2)")
	public List<TbodContratoCorretora> findContratoCdCorretoraAndCdContratoModelo2Or1(@Param("cdCorretora") Long cdCorretora, @Param("cdContratoModelo1") Long cdContratoModelo1, @Param("cdContratoModelo2") Long cdContratoModelo2);



}
