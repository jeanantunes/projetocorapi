package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.portal.corretor.dao.ContratoCorretoraDataAceiteDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class ContratoCorretoraServiceImpl implements ContratoCorretoraService {

	private static final Log log = LogFactory.getLog(ContratoCorretoraServiceImpl.class);

	@Autowired
	ContratoCorretoraDataAceiteDAO contratoCorretoraDataAceiteDAO;

	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora){

		ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();

		try {

			TbodContratoCorretora tbodContratoCorretora = contratoCorretoraDataAceiteDAO.findOne(cdCorretora);

			if(tbodContratoCorretora == null){
				return null;
			}

			contratoCorretora.setCdCorretora(tbodContratoCorretora.getCdCorretora());
			contratoCorretora.setDtAceiteContrato(
					(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
							.format(tbodContratoCorretora.getDtAceiteContrato())); //201808271556 - esert


		}catch (Exception e){

		}

		return contratoCorretora;
	}
}
