package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForcaVendaDAO extends CrudRepository<TbodForcaVenda, Long> {

	public List<TbodForcaVenda> findByCpf(String cpf);

	public TbodForcaVenda findByCdForcaVenda(Long cdForcaVenda);
	
	public TbodForcaVenda findByCdForcaVendaAndTbodCorretoraCnpj(Long cdForcaVenda, String cnpj);

	public List<TbodForcaVenda> findByTbodCorretoraCdCorretora(Long cdCorretora);

	public List<TbodForcaVenda> findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(Long cdStatusForcaVenda, Long cdCorretora);
	
	@Query(value=" select venda.dt_venda, " + 
			"      vida.nome, vida.cpf, vida.data_nascimento, vida.nome_mae, vida.celular, vida.email, " +
			"	   endereco.logradouro, endereco.cep, endereco.cidade, endereco.complemento, endereco.bairro, endereco.uf, endereco.numero, " +
			"	   plano.nome_plano, plano.valor_mensal, plano.valor_anual, " +
			"	   tipoPlano.descricao, " +
			"	   forca.nome forca_nome " +
			"      from TBOD_FORCA_VENDA forca  " +				
			"      inner join TBOD_VENDA venda on venda.cd_forca_vendas = forca.cd_forca_venda " +
//			"      inner join TBOD_VENDA_VIDA vendaVida on vendaVida.cd_venda_vida = venda.cd_venda_vida " + //201805301828 - esert - exc - join possivel mas invalido
			"      inner join TBOD_VENDA_VIDA vendaVida on vendaVida.cd_venda = venda.cd_venda " + //201805301828 - esert - exc - join valido
			"	   inner join TBOD_VIDA vida on vida.cd_vida = vendaVida.cd_vida " +
			"      inner join TBOD_ENDERECO endereco on endereco.cd_endereco = vida.cd_endereco " +
			"	   inner join TBOD_PLANO plano on plano.cd_plano = venda.cd_plano	" +
			"	   inner join TBOD_TIPO_PLANO tipoPlano on tipoPlano.cd_tipo_plano = plano.cd_tipo_plano" +	
			"	   where " + //201805301828 - esert - inc
			"	   forca.cd_forca_venda = :cdForcaVenda " //201805301828 - esert - inc
			/*"	   and venda.dt_venda BETWEEN :dtVendaInic and :dtVendaFim " +*/
			/*"	   and venda.dt_venda BETWEEN '03/03/2018' and '03/03/2018' " +*/
			, nativeQuery=true)
	
	public List<Object[]> vendasByForcaVenda(@Param("cdForcaVenda") long cdForcaVenda);
	
	@Query(value=" select forca.cd_forca_venda " + 		
			"      from TBOD_FORCA_VENDA forca inner join TBOD_CORRETORA corretora" +			
			"	   on forca.cd_corretora = corretora.cd_corretora " +
			"	   and corretora.cd_corretora = :cdCorretora " +
			"	   and corretora.ativo = 'S' "	
			, nativeQuery=true)
	
	public List<Object[]> findForcaVendaAtiva(@Param("cdCorretora") long cdCorretora);
		
	//public TbodForcaVenda findByCdForcaVendaAndEmail(Long cdForcaVenda, String email);
	
}
