package br.com.odontoprev.portal.corretor.dao.impl;

import br.com.odontoprev.portal.corretor.dao.VwodCorretoraTotalVendasDAO;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPF;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPME;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

//201806081613 - esert - relatorio vendas
@Transactional
@Repository
public class VwodCorretoraTotalVendasDAOImpl implements VwodCorretoraTotalVendasDAO {

    private static final Log log = LogFactory.getLog(VwodCorretoraTotalVendasDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    //201806081613 - esert - relatorio vendas pme
    @Override
    public List<VwodCorretoraTotalVidasPME> vwodCorretoraTotalVidasPMEByFiltro(Date dtVendaInicio,
                                                                               Date dtVendaFim,
                                                                               String cnpjCorretora) {
        log.info("vwodCorretoraTotalVidasPMEByFiltro - ini");

        String stringQuery = "from VwodCorretoraTotalVidasPME v where 1=1 ";

        if (dtVendaInicio != null) {
            stringQuery += " AND v.dtVenda >= :dtVendaInicio";
        }

        if (dtVendaFim != null) {
            stringQuery += " AND v.dtVenda < :dtVendaFim "; //201808022040 - esert - COR-529 - apenas operador < (menor) para nao deve pegar zer0h0ra do D+1
        }

        if (cnpjCorretora != null) {
            stringQuery += " AND v.cnpj_corretora = :cnpjCorretora ";
        }

        Query query = entityManager.createQuery(stringQuery, VwodCorretoraTotalVidasPME.class);

        if (dtVendaInicio != null) {
            query.setParameter("dtVendaInicio", dtVendaInicio);
        }

        if (dtVendaFim != null) {
            query.setParameter("dtVendaFim", dtVendaFim);
        }

        if (cnpjCorretora != null) {
            query.setParameter("cnpjCorretora", cnpjCorretora);
        }

        @SuppressWarnings("unchecked")
        List<VwodCorretoraTotalVidasPME> vwodCorretoraTotalVidasPME = query.getResultList();

        log.info("vwodCorretoraTotalVidasPME.size():[" + vwodCorretoraTotalVidasPME.size() + "]"); //201806081852 - esert

        log.info("vwodCorretoraTotalVidasPMEByFiltro - fim");

        return vwodCorretoraTotalVidasPME;
    }

    //201806121140 - esert - relatorio vendas pf
    @Override
    public List<VwodCorretoraTotalVidasPF> vwodCorretoraTotalVidasPFByFiltro(Date dtVendaInicio,
                                                                             Date dtVendaFim,
                                                                             String cnpjCorretora) {
        log.info("vwodCorretoraTotalVendasPFByFiltro - ini");

        String stringQuery = "from VwodCorretoraTotalVidasPF v where 1=1 ";

        if (dtVendaInicio != null) {
            stringQuery += " AND v.dt_venda >= :dtVendaInicio";
        }

        if (dtVendaFim != null) {
            stringQuery += " AND v.dt_venda < :dtVendaFim "; //201808022040 - esert - COR-529 - apenas operador < (menor) para nao deve pegar zer0h0ra do D+1
        }

        if (cnpjCorretora != null) {
            stringQuery += " AND v.cnpj_corretora = :cnpjCorretora ";
        }

        Query query = entityManager.createQuery(stringQuery, VwodCorretoraTotalVidasPF.class);

        if (dtVendaInicio != null) {
            query.setParameter("dtVendaInicio", dtVendaInicio);
        }

        if (dtVendaFim != null) {
            query.setParameter("dtVendaFim", dtVendaFim);
        }

        if (cnpjCorretora != null) {
            query.setParameter("cnpjCorretora", cnpjCorretora);
        }

        @SuppressWarnings("unchecked")
        List<VwodCorretoraTotalVidasPF> vwodCorretoraTotalVidasPF = query.getResultList();

        log.info("vwodCorretoraTotalVidasPF.size():[" + vwodCorretoraTotalVidasPF.size() + "]"); //201806121140 - esert

        log.info("vwodCorretoraTotalVendasPFByFiltro - fim");

        return vwodCorretoraTotalVidasPF;
    }
}
