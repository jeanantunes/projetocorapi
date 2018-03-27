package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;

@Repository
public interface ForcaVendaDAO extends CrudRepository<TbodForcaVenda, Long> {

	public List<TbodForcaVenda> findByCpf(String cpf);
	
	public TbodForcaVenda findByCdForcaVendaAndTbodCorretoraCnpj(Long cdForcaVenda, String cnpj);

	public List<TbodForcaVenda> findByTbodCorretoraCdCorretora(Long cdCorretora);

	public List<TbodForcaVenda> findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(Long cdStatusForcaVenda, Long cdCorretora);
	
	@Query(value=" select venda.dt_venda, " + 
			"      vida.nome, vida.cpf, vida.data_nascimento, vida.nome_mae, vida.celular, vida.email, " +
			"	   endereco.logradouro, endereco.cep, endereco.cidade, endereco.complemento, endereco.bairro, endereco.uf, endereco.numero, " +
			"	   plano.nome_plano, plano.valor_mensal, plano.valor_anual, " +
			"	   tipoPlano.descricao " +
			"      from TBOD_FORCA_VENDA forca  " +				
			"      inner join TBOD_VENDA venda on venda.cd_forca_vendas = forca.cd_forca_venda and venda.cd_forca_vendas = :cdForcaVenda " +
			"      inner join TBOD_VENDA_VIDA vendaVida on vendaVida.cd_venda_vida = venda.cd_venda_vida " +
			"	   inner join TBOD_VIDA vida on vida.cd_vida = vendaVida.cd_vida " +
			"      inner join TBOD_ENDERECO endereco on endereco.cd_endereco = vida.cd_endereco " +
			"	   inner join TBOD_PLANO plano on plano.cd_plano = venda.cd_plano	" +
			"	   inner join TBOD_TIPO_PLANO tipoPlano on tipoPlano.cd_tipo_plano = plano.cd_tipo_plano"	
			, nativeQuery=true)
	
	public List<Object[]> vendasByForcaVenda(@Param("cdForcaVenda") long cdForcaVenda);
}
