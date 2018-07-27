package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

@org.springframework.stereotype.Repository
public interface DashboardPropostaDAO extends Repository<TbodVenda, Long> {

    @Query(value = " SELECT emp.CD_EMPRESA, " + // 0
					"  emp.CNPJ, " +			// 1
					"  emp.RAZAO_SOCIAL, " +	// 2
					"  emp.NOME_FANTASIA, " +	// 3
					"  venda.DT_VENDA, " +		// 4
					"  status.DESCRICAO, " +	// 5
					"  SUM(plano.valor_anual + plano.valor_mensal) valor " + // 6
					"FROM tbod_corretora corretora " +
						"INNER JOIN tbod_forca_venda forca " +
						"ON forca.CD_CORRETORA = corretora.CD_CORRETORA " +
						"INNER JOIN tbod_venda venda " +
						"ON venda.cd_forca_vendas = forca.cd_forca_venda " +
						"INNER JOIN tbod_plano plano " +
						"ON plano.cd_plano = venda.cd_plano " +
						"INNER JOIN tbod_empresa emp " +
						"ON venda.cd_empresa = emp.cd_empresa " +
						"INNER JOIN tbod_status_venda status " +
						"ON status.cd_status_venda = venda.cd_status_venda " +
						"INNER JOIN tbod_venda_vida vv " +
						"ON venda.CD_VENDA = vv.CD_VENDA " +
						"INNER JOIN tbod_vida vida " +
					"ON vv.cd_vida        = vida.CD_VIDA " +
					"WHERE  (:cpf                 IS NULL "+
		    		"OR forca.cpf                   = :cpf) "+
		    		"AND (:cnpj                IS NULL "+
		    		"OR corretora.cnpj                  = :cnpj) "+
		    		"AND (:status           = 0 "+
		    		"OR status.cd_status_venda  = :status) "+
					"GROUP BY emp.CD_EMPRESA, " +
					"  emp.CNPJ, " +
					"  emp.RAZAO_SOCIAL, " +
					"  emp.NOME_FANTASIA, " +
					"  venda.DT_VENDA, " +
					"  status.DESCRICAO " +
				"ORDER BY venda.dt_venda DESC ", nativeQuery = true)
    public List<Object[]> findAllDashboardPropostasPMEByStatusCnpjCpf(@Param("status") Long status, @Param("cnpj") String cnpj, @Param("cpf") String cpf);

 /*   @Query(value = "SELECT DISTINCT venda.cd_venda, " +
            "                vida.cpf, " +
            "                venda.proposta_dcms, " +
            "                vida.nome, " +
            "                status.descricao, " +
            "                Sum(p.valor_anual + p.valor_mensal) valor " +
            "FROM   tbod_venda venda, " +
            "       tbod_status_venda status, " +
            "       tbod_venda_vida vv, " +
            "       tbod_vida vida, " +
            "       tbod_forca_venda f, " +
            "       tbod_plano p " +
            "WHERE  p.cd_plano = venda.cd_plano " +
            "       AND venda.cd_venda = vv.cd_venda " +
            "       AND vv.cd_vida = vida.cd_vida " +
            "       AND venda.cd_empresa IS NULL " +
            "       AND venda.cd_venda = vv.cd_venda " +
            "       AND status.cd_status_venda = venda.cd_status_venda " +
            "       and f.cd_forca_venda = venda.CD_FORCA_VENDAS " +
            "       AND f.cpf = :cpf " +
            "GROUP  BY venda.cd_venda, " +
            "          vida.cpf, " +
            "          venda.proposta_dcms, " +
            "          vida.nome, " +
            "          status.descricao "
            , nativeQuery = true)
    public List<Object[]> findAllDashboardPropostasPF(@Param("cpf") String cpf);
    */
    
        

    @Query(value = "SELECT DISTINCT venda.cd_venda, "+
    		"  vida.cpf, "+
    		"  venda.proposta_dcms, "+
    		"  vida.nome, "+
    		"  status.descricao, "+
    		"  SUM(p.valor_anual + p.valor_mensal) valor "+
    		"FROM tbod_venda venda, "+
    		"  tbod_status_venda status, "+
    		"  tbod_venda_vida vv, "+
    		"  tbod_vida vida, "+
    		"  tbod_forca_venda f, "+
    		"  tbod_corretora c, "+
    		"  tbod_plano p "+
    		"WHERE p.cd_plano           = venda.cd_plano "+
    		"AND venda.cd_venda         = vv.cd_venda "+
    		"AND vv.cd_vida             = vida.cd_vida "+
    		"AND venda.cd_empresa      IS NULL "+
    		"AND venda.cd_venda         = vv.cd_venda "+
    		"AND status.cd_status_venda = venda.cd_status_venda "+
    		"AND f.cd_forca_venda       = venda.cd_forca_vendas "+
    		"AND c.cd_corretora         = f.cd_corretora "+
    		"AND (:cpf                 IS NULL "+
    		"OR f.cpf                   = :cpf) "+
    		"AND (:cnpj                IS NULL "+
    		"OR c.cnpj                  = :cnpj) "+
    		"AND (:status           = 0 "+
    		"OR status.cd_status_venda  = :status) "+
    		"GROUP BY venda.cd_venda, "+
    		"  vida.cpf, "+
    		"  venda.proposta_dcms, "+
    		"  vida.nome, "+
    		"  status.descricao ", nativeQuery = true)
    public List<Object[]> findDashboardPropostaPFByStatusCpfCnpj(@Param("status") Long status, @Param("cpf") String cpf, @Param("cnpj") String cnpj);            

    @Query(value = " select * from vwod_cor_critica_pf where cnpj = :cnpj and cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaTodasPorCriticaPF(@Param("cnpj") String cnpj, @Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pf where cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPFporCPF(@Param("cpf") String cpf);
    
    @Query(value = " select ds_erro_registro from vwod_cor_critica_pf where nr_atendimento = :nrAtendimento ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPFporNumeroAtendimento(@Param("nrAtendimento") String nrAtendimento);
    
    @Query(value = " select nr_atendimento, ds_erro_registro from vwod_cor_critica_pf ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPF();

    @Query(value = " select * from vwod_cor_critica_pf where cnpj = :cnpj ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPFporCNPJ(@Param("cnpj") String cnpj);

    @Query(value = " select * from vwod_cor_critica_pme where cnpj = :cnpj and cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaTodasPorCriticaPME(@Param("cnpj") String cnpj, @Param("cpf") String cpf);
    
    @Query(value = " SELECT pme.CD_EMPRESA, " +
    		" st.descricao " + 
    		"FROM   vwod_cor_status_venda_pme pme, " + 
    		"       tbod_status_venda st " + 
    		"WHERE  pme.status = st.cd_status_venda ", nativeQuery = true)
    public List<Object[]> buscarCriticasPME();

    @Query(value = " select * from vwod_cor_critica_pme where cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPMEporCPF(@Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pme where cnpj = :cnpj ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPMEporCNPJ(@Param("cnpj") String cnpj);
}
