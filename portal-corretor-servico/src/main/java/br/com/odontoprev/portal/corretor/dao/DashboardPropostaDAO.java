package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

@org.springframework.stereotype.Repository
public interface DashboardPropostaDAO extends Repository<TbodVenda, Long> {

	@Query(value="select distinct emp.CD_EMPRESA, emp.CNPJ, emp.NOME_FANTASIA, venda.DT_VENDA, status.DESCRICAO " + 
			"from TBOD_VENDA venda inner join TBOD_EMPRESA emp on venda.CD_EMPRESA = emp.CD_EMPRESA " + 
			"inner join  TBOD_STATUS_VENDA status on status.CD_STATUS_VENDA = venda.CD_STATUS_VENDA " + 
			"and status.CD_STATUS_VENDA = :status order by venda.DT_VENDA desc" 
			, nativeQuery=true)
	public List<Object[]> dashboardPropostaByStatus(@Param("status") Long status);
	
	
	
}
