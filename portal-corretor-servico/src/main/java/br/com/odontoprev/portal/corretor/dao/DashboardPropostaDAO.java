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
			"where emp.CNPJ = :cnpj order by venda.DT_VENDA desc" 
			, nativeQuery=true)
	public List<Object[]> findAllDashboardPropostasPME(@Param("cnpj") String cnpj);

	@Query(value="select distinct emp.CD_EMPRESA, emp.CNPJ, emp.NOME_FANTASIA, venda.DT_VENDA, status.DESCRICAO " + 
			"from TBOD_VENDA venda inner join TBOD_EMPRESA emp on venda.CD_EMPRESA = emp.CD_EMPRESA " + 
			"inner join  TBOD_STATUS_VENDA status on status.CD_STATUS_VENDA = venda.CD_STATUS_VENDA " +
			"where emp.CNPJ = :cnpj and status.CD_STATUS_VENDA = :status order by venda.DT_VENDA desc" 
			, nativeQuery=true)
	public List<Object[]> findDashboardPropostaPMEByStatus(@Param("status") Long status, @Param("cnpj") String cnpj);

	@Query(value="SELECT distinct venda.cd_venda, vida.cpf, venda.proposta_dcms, vida.nome, status.descricao " + 
			"FROM   tbod_venda venda, tbod_status_venda status, tbod_venda_vida vv, tbod_vida vida, tbod_forca_venda forca " + 
			"WHERE  1 = 1 AND venda.cd_venda = vv.cd_venda AND vv.cd_vida = vida.cd_vida AND vida.cd_titular IS NULL " + 
			"AND venda.cd_empresa IS NULL AND venda.cd_venda = vv.cd_venda AND status.cd_status_venda = venda.cd_status_venda " +
			"AND venda.CD_FORCA_VENDAS = forca.CD_FORCA_VENDA AND forca.CPF = :cpf " + 
			"GROUP  BY venda.cd_venda, vida.cpf, venda.proposta_dcms, vida.nome, status.descricao" 
			, nativeQuery=true)
	public List<Object[]> findAllDashboardPropostasPF(@Param("cpf") String cpf);
	
	@Query(value="SELECT distinct venda.cd_venda, vida.cpf, venda.proposta_dcms, vida.nome, status.descricao " + 
			"FROM   tbod_venda venda, tbod_status_venda status, tbod_venda_vida vv, tbod_vida vida, tbod_forca_venda forca " + 
			"WHERE  1 = 1 AND venda.cd_venda = vv.cd_venda AND vv.cd_vida = vida.cd_vida AND vida.cd_titular IS NULL " + 
			"AND venda.cd_empresa IS NULL AND venda.cd_venda = vv.cd_venda AND status.cd_status_venda = venda.cd_status_venda " +
			"AND venda.CD_FORCA_VENDAS = forca.CD_FORCA_VENDA AND status.cd_status_venda = :status AND forca.CPF = :cpf " + 
			"GROUP  BY venda.cd_venda, vida.cpf, venda.proposta_dcms, vida.nome, status.descricao" 
			, nativeQuery=true)
	public List<Object[]> findDashboardPropostaPFByStatus(@Param("status") Long status, @Param("cpf") String cpf);
	
	
}
