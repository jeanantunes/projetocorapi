package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

@org.springframework.stereotype.Repository
public interface DashboardPropostaDAO extends Repository<TbodVenda, Long> {

    @Query(value = " SELECT emp.CD_EMPRESA, " + //0
					"  emp.CNPJ, " + //1
					"  emp.RAZAO_SOCIAL, " + //2
					"  emp.NOME_FANTASIA, " + //3 // [COR-488] yalm-201807271234
					"  venda.DT_VENDA, " + //4
					"  status.DESCRICAO, " + //5
					"  status.CD_STATUS_VENDA, " + //6 // yalm - 201808012050 - COR-508
					"  SUM(plano.valor_anual + plano.valor_mensal) valor " + //7
					"FROM tbod_venda venda " + //201807301937 - esert - COR-496 - alt
						"INNER JOIN tbod_corretora corretora ON corretora.cd_corretora = venda.cd_corretora " + //201807301937 - esert - COR-496 - alt
						//permite ver vendas do forca_venda apenas enquanto esta na corretora
						//"INNER JOIN tbod_forca_venda forca ON forca.cd_forca_venda = venda.cd_forca_vendas and forca.cd_corretora = corretora.cd_corretora " + //201807301937 - esert - COR-496 - alt
						//permite ver vendas do forca_venda antes de sair da corretora 
						"INNER JOIN tbod_forca_venda forca ON forca.cd_forca_venda = venda.cd_forca_vendas " + //201807301937 - esert - COR-496 - alt 
						"INNER JOIN tbod_plano plano ON plano.cd_plano = venda.cd_plano " +
						"INNER JOIN tbod_empresa emp ON venda.cd_empresa = emp.cd_empresa " +
						"INNER JOIN tbod_status_venda status ON status.cd_status_venda = venda.cd_status_venda " +
						"INNER JOIN tbod_venda_vida vv ON venda.CD_VENDA = vv.CD_VENDA " +
						"INNER JOIN tbod_vida vida ON vv.cd_vida = vida.CD_VIDA " +
					"WHERE (:cpf IS NULL OR forca.cpf = :cpf) "+
		    		"AND (:cnpj IS NULL OR corretora.cnpj = :cnpj) "+
		    		"AND (:status = 0 OR status.cd_status_venda  = :status) "+
					"GROUP BY emp.CD_EMPRESA, " +
					"  emp.CNPJ, " +
					"  emp.RAZAO_SOCIAL, " +
					"  emp.NOME_FANTASIA, " +
					"  venda.DT_VENDA, " +
					"  status.DESCRICAO, " +
					"  status.CD_STATUS_VENDA " + // yalm - 201808012050 - COR-508
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
    
        

    @Query(value = "SELECT DISTINCT venda.cd_venda, "+ //0
    		"  vida.cpf, "+ //1
    		"  venda.proposta_dcms, "+ //2
    		"  vida.nome, "+ //3
    		"  status.descricao, "+ //4
    		"  status.cd_status_venda, " + //5 // yalm - 201808012050 - COR-508
    		"  SUM(p.valor_anual + p.valor_mensal) valor "+ //6
    		"FROM tbod_venda venda "+ //201807301937 - esert - COR-496 - alt
    		"JOIN tbod_corretora c ON c.cd_corretora = venda.cd_corretora "+ //201807301937 - esert - COR-496 - alt
			//permite ver vendas do forca_venda apenas enquanto esta na corretora
    		//"JOIN tbod_forca_venda f ON f.cd_forca_venda = venda.cd_forca_vendas AND f.cd_corretora = c.cd_corretora "+ //201807301937 - esert - COR-496 - alt
			//permite ver vendas do forca_venda mesmo apos sair da corretora
    		"JOIN tbod_forca_venda f ON f.cd_forca_venda = venda.cd_forca_vendas "+ //201808012337 - esert - COR-496 - alt
    		"JOIN tbod_status_venda status ON status.cd_status_venda = venda.cd_status_venda "+
    		"JOIN tbod_venda_vida vv ON vv.cd_venda = venda.cd_venda "+
    		"JOIN tbod_vida vida ON vida.cd_vida = vv.cd_vida "+
    		"JOIN tbod_plano p ON p.cd_plano = venda.cd_plano "+
    		"WHERE venda.cd_empresa IS NULL "+
    		"AND (:cpf IS NULL OR f.cpf = :cpf) "+
    		"AND (:cnpj IS NULL OR c.cnpj = :cnpj) "+
    		"AND (:status = 0 OR status.cd_status_venda = :status) "+
    		"GROUP BY venda.cd_venda, "+
    		"  vida.cpf, "+
    		"  venda.proposta_dcms, "+
    		"  vida.nome, "+
    		"  status.descricao, " +
    		"  status.cd_status_venda " // yalm - 201808012050 - COR-508

    		, nativeQuery = true)
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
