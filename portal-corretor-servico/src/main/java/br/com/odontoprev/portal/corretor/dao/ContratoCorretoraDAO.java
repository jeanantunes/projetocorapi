package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;

@Repository
public interface ContratoCorretoraDAO extends CrudRepository<TbodContratoCorretora, Long>{

	public List<TbodContratoCorretora> findByTbodCorretoraCdCorretora(Long cdCorretora);

	public List<TbodContratoCorretora> findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModelo(Long cdCorretora, Long cdContratoModelo); //201809111820 - esert - apos COR-752 - alter TBOD_CONTRATO_CORRETORA este find passa retornar lista
	
	public List<TbodContratoCorretora> findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(Long cdCorretora, Long cdContratoModelo1, Long cdContratoModelo2); //201809111820 - esert - apos COR-752 - alter TBOD_CONTRATO_CORRETORA este find passa retornar lista
	
}
