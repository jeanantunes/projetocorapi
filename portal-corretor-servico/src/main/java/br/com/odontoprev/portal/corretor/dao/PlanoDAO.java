package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodPlano;


@org.springframework.stereotype.Repository
public interface PlanoDAO extends Repository<TbodPlano, Long> {

	public TbodPlano findByCdPlano(long cdPlano);
	
	public List<TbodPlano> findAll();

	@Query(value="select plano.cd_plano, plano.nome_plano, venda.cd_venda, plano.valor_anual, plano.valor_mensal from TBOD_PLANO plano " + 
			"    inner join TBOD_VENDA venda on plano.cd_plano = venda.cd_plano and venda.cd_empresa = :cdEmpresa " 
			, nativeQuery=true)
	public List<Object[]> planosByEmpresa(@Param("cdEmpresa") long cdEmpresa);

}
