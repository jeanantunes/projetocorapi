package br.com.odontoprev.portal.corretor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.ContratoCorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ContratoModeloDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodContratoModelo;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@Service
public class ContratoCorretoraServiceImpl implements ContratoCorretoraService {

	private static final Log log = LogFactory.getLog(ContratoCorretoraServiceImpl.class);

	@Autowired
	private ContratoCorretoraDAO contratoCorretoraDAO;

    @Autowired
    private CorretoraDAO corretoraDAO;

    @Autowired
    private ContratoModeloDAO contratoModeloDAO;

	@Override
	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora) throws Exception {
		log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - ini");
		
		ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();

		try {

			List<TbodContratoCorretora> listTbodContratoCorretora = contratoCorretoraDAO.findByTbodCorretoraCdCorretora(cdCorretora);

			if(listTbodContratoCorretora == null){
				log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - fim - listTbodContratoCorretora == null");
				return null;
			}

			contratoCorretora.setCdCorretora(listTbodContratoCorretora.get(0).getTbodCorretora().getCdCorretora());
			contratoCorretora.setDtAceiteContrato(
					(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
							.format(listTbodContratoCorretora.get(0).getDtAceiteContrato())); //201808271556 - esert


		}catch (Exception e){
			log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - erro");
			log.error(e);;
			throw new Exception(e);
		}

		log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - fim");
		return contratoCorretora;
	}
	
	//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	@Override
	public ContratoCorretoraPreenchido getContratoPreenchido(Long cdCorretora, Long cdContratoModelo) throws Exception {
		log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - ini");
		
		ContratoCorretoraPreenchido contratoCorretora = new ContratoCorretoraPreenchido();

		try {

			//List<TbodContratoCorretora> listTbodContratoCorretora = contratoCorretoraDAO.findByTbodCorretoraCdCorretora(cdCorretora);
			List<TbodContratoCorretora> listTbodContratoCorretora = 
					contratoCorretoraDAO.findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(
							cdCorretora,
							Constantes.CONTRATO_CORRETAGEM_V1,
							Constantes.CONTRATO_INTERMEDIACAO_V1
					);
			//TbodCorretora tbodCorretora = corretoraDAO.findOne(cdCorretora);

			if(listTbodContratoCorretora == null || listTbodContratoCorretora.size()==0){
				log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - null - listTbodContratoCorretora == null");
				return null;
			}

			contratoCorretora.setCdCorretora(listTbodContratoCorretora.get(0).getTbodCorretora().getCdCorretora());
			contratoCorretora.setCdContratoModelo(cdContratoModelo);
			String contratoPreenchido = null;
			//contratoPreenchido = tbodContratoCorretora.toString();
			//contratoPreenchido = contratoPreenchido.substring(contratoPreenchido.indexOf("{"), contratoPreenchido.length()) ;
			//contratoPreenchido = tbodCorretora.toString();
			contratoPreenchido = listTbodContratoCorretora.get(0).getTbodCorretora().toString(); //201809111752 - esert
			contratoPreenchido = contratoPreenchido.substring(contratoPreenchido.indexOf("["), contratoPreenchido.length()) ;
			contratoPreenchido = contratoPreenchido.replace("{", "<p>");
			contratoPreenchido = contratoPreenchido.replace("[", "<p>");
			contratoPreenchido = contratoPreenchido.replaceAll(",", "</p><p>");
			contratoPreenchido = contratoPreenchido.replace("}", "</p>");
			contratoPreenchido = contratoPreenchido.replace("]", "</p>");
			contratoCorretora.setContratoPreenchido(contratoPreenchido);


		}catch (Exception e){
			log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - erro");
			log.error(e);;
			throw new Exception(e);
		}

		log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - fim");
		return contratoCorretora;
	}

	@Override
	public ContratoCorretora postContratoCorretora(ContratoCorretora contratoCorretora) {
		log.info("postContratoCorretora - ini");
		log.info("contratoCorretora[" + contratoCorretora.toString() + "]");
		
        TbodCorretora tbodCorretora = corretoraDAO.findOne(contratoCorretora.getCdCorretora());

        if (tbodCorretora == null) {
            log.error("postContratoCorretora - BAD_REQUEST - tbodCorretora null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (tbodCorretora == null) {
            log.error("postContratoCorretora - BAD_REQUEST - tbodCorretora null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (contratoCorretora.getCdSusep() != null) {
            tbodCorretora.setTemSusep(Constantes.SIM);
            tbodCorretora.setCodigoSusep(contratoCorretora.getCdSusep());
            contratoCorretora.setCdContratoModelo(Constantes.CONTRATO_CORRETAGEM_V1); // 1 - Contrato Corretagem
        } else if (contratoCorretora.getCdSusep() == null) {
            tbodCorretora.setTemSusep(Constantes.NAO);
            tbodCorretora.setCodigoSusep(null);
            contratoCorretora.setCdContratoModelo(Constantes.CONTRATO_INTERMEDIACAO_V1); // 2 - Contrato Intermediação
        }

        tbodCorretora = corretoraDAO.save(tbodCorretora);

        TbodContratoCorretora tbodContratoCorretora = new TbodContratoCorretora();

        tbodContratoCorretora.setTbodCorretora(tbodCorretora);

        TbodContratoModelo tbodContratoModelo = contratoModeloDAO.findOne(contratoCorretora.getCdContratoModelo());

        if (tbodContratoModelo == null) {
            log.error("ERROR: tbodContratoModelo == null para " + contratoCorretora.getCdContratoModelo());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        tbodContratoCorretora.setTbodContratoModelo(tbodContratoModelo);
        tbodContratoCorretora.setDtAceiteContrato(new Date());

        tbodContratoCorretora = contratoCorretoraDAO.save(tbodContratoCorretora);

        contratoCorretora.setCdContratoCorretora(tbodContratoCorretora.getCdContratoCorretora());
        contratoCorretora.setDtAceiteContrato(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbodContratoCorretora.getDtAceiteContrato()));
        
		log.info("postContratoCorretora - fim");
        return contratoCorretora;
	}
}
