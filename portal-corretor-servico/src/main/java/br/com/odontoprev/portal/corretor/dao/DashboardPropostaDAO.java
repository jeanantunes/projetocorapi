package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

@org.springframework.stereotype.Repository
public interface DashboardPropostaDAO extends Repository<TbodVenda, Long> {

    @Query(value = "SELECT emp.CD_EMPRESA, emp.CNPJ, emp.NOME_FANTASIA, venda.DT_VENDA, status.DESCRICAO, SUM(plano.valor_anual + plano.valor_mensal) valor\n" +
            "from tbod_corretora corretora\n" +
            "inner join tbod_forca_venda forca on forca.CD_CORRETORA = corretora.CD_CORRETORA\n" +
            "inner join tbod_venda venda on venda.cd_forca_vendas = forca.cd_forca_venda\n" +
            "inner join tbod_plano plano on plano.cd_plano = venda.cd_plano\n" +
            "inner JOIN tbod_empresa emp ON venda.cd_empresa = emp.cd_empresa\n" +
            "inner JOIN tbod_status_venda status ON status.cd_status_venda = venda.cd_status_venda \n" +
            "where corretora.cnpj = :cnpj \n" +
            "GROUP BY emp.CD_EMPRESA, emp.CNPJ, emp.NOME_FANTASIA, venda.DT_VENDA, status.DESCRICAO\n" +
            "ORDER BY venda.dt_venda DESC", nativeQuery = true)
    public List<Object[]> findAllDashboardPropostasPME(@Param("cnpj") String cnpj);

    @Query(value = "SELECT emp.cd_empresa, \n" +
            "       emp.cnpj, \n" +
            "       emp.nome_fantasia, \n" +
            "       venda.dt_venda, \n" +
            "       status.descricao, \n" +
            "       Sum(plano.valor_anual + plano.valor_mensal) valor \n" +
            "FROM   tbod_corretora corretora \n" +
            "       INNER JOIN tbod_forca_venda forca \n" +
            "               ON forca.cd_corretora = corretora.cd_corretora \n" +
            "       INNER JOIN tbod_venda venda \n" +
            "               ON venda.cd_forca_vendas = forca.cd_forca_venda \n" +
            "       INNER JOIN tbod_plano plano \n" +
            "               ON plano.cd_plano = venda.cd_plano \n" +
            "       INNER JOIN tbod_empresa emp \n" +
            "               ON venda.cd_empresa = emp.cd_empresa \n" +
            "       INNER JOIN tbod_status_venda status \n" +
            "               ON status.cd_status_venda = venda.cd_status_venda \n" +
            "WHERE  corretora.cnpj = :cnpj \n" +
            "       AND status.cd_status_venda = :status \n" +
            "GROUP  BY emp.cd_empresa, \n" +
            "          emp.cnpj, \n" +
            "          emp.nome_fantasia, \n" +
            "          venda.dt_venda, \n" +
            "          status.descricao \n" +
            "ORDER  BY venda.dt_venda DESC"
            , nativeQuery = true)
    public List<Object[]> findDashboardPropostaPMEByStatus(@Param("status") Long status, @Param("cnpj") String cnpj);

    @Query(value = "SELECT DISTINCT venda.cd_venda, \n" +
            "                vida.cpf, \n" +
            "                venda.proposta_dcms, \n" +
            "                vida.nome, \n" +
            "                status.descricao,\n" +
            "                Sum(p.valor_anual + p.valor_mensal) valor\n" +
            "FROM   tbod_venda venda, \n" +
            "       tbod_status_venda status, \n" +
            "       tbod_venda_vida vv, \n" +
            "       tbod_vida vida,\n" +
            "       tbod_forca_venda f,\n" +
            "       tbod_plano p\n" +
            "WHERE  p.cd_plano = venda.cd_plano\n" +
            "       AND venda.cd_venda = vv.cd_venda \n" +
            "       AND vv.cd_vida = vida.cd_vida \n" +
            "       AND venda.cd_empresa IS NULL \n" +
            "       AND venda.cd_venda = vv.cd_venda \n" +
            "       AND status.cd_status_venda = venda.cd_status_venda\n" +
            "       and f.cd_forca_venda = venda.CD_FORCA_VENDAS\n" +
            "       AND f.cpf = :cpf\n" +
            "GROUP  BY venda.cd_venda, \n" +
            "          vida.cpf, \n" +
            "          venda.proposta_dcms, \n" +
            "          vida.nome, \n" +
            "          status.descricao; "
            , nativeQuery = true)
    public List<Object[]> findAllDashboardPropostasPF(@Param("cpf") String cpf);

    @Query(value = " ELECT DISTINCT venda.cd_venda, \n" +
            "                vida.cpf, \n" +
            "                venda.proposta_dcms, \n" +
            "                vida.nome, \n" +
            "                status.descricao,\n" +
            "                Sum(p.valor_anual + p.valor_mensal) valor\n" +
            "FROM   tbod_venda venda, \n" +
            "       tbod_status_venda status, \n" +
            "       tbod_venda_vida vv, \n" +
            "       tbod_vida vida,\n" +
            "       tbod_forca_venda f,\n" +
            "       tbod_plano p\n" +
            "WHERE  p.cd_plano = venda.cd_plano\n" +
            "       AND venda.cd_venda = vv.cd_venda \n" +
            "       AND vv.cd_vida = vida.cd_vida \n" +
            "       AND venda.cd_empresa IS NULL \n" +
            "       AND venda.cd_venda = vv.cd_venda \n" +
            "       AND status.cd_status_venda = venda.cd_status_venda\n" +
            "       and f.cd_forca_venda = venda.CD_FORCA_VENDAS\n" +
            "       AND f.cpf = :cpf\n" +
            "       AND status.cd_status_venda = :status\n" +
            "GROUP  BY venda.cd_venda, \n" +
            "          vida.cpf, \n" +
            "          venda.proposta_dcms, \n" +
            "          vida.nome, \n" +
            "          status.descricao;", nativeQuery = true)
    public List<Object[]> findDashboardPropostaPFByStatus(@Param("status") Long status, @Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pf where cnpj = :cnpj and cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaTodasPorCriticaPF(@Param("cnpj") String cnpj, @Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pf where cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPFporCPF(@Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pf where cnpj = :cnpj ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPFporCNPJ(@Param("cnpj") String cnpj);

    @Query(value = " select * from vwod_cor_critica_pme where cnpj = :cnpj and cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaTodasPorCriticaPME(@Param("cnpj") String cnpj, @Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pme where cpf = :cpf ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPMEporCPF(@Param("cpf") String cpf);

    @Query(value = " select * from vwod_cor_critica_pme where cnpj = :cnpj ", nativeQuery = true)
    public List<Object[]> buscaPorCriticaPMEporCNPJ(@Param("cnpj") String cnpj);
}
