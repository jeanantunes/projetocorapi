package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.UploadDAO;
import br.com.odontoprev.portal.corretor.dto.UploadResponse;
import br.com.odontoprev.portal.corretor.model.TbodUploadForcavenda;
import br.com.odontoprev.portal.corretor.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService{

	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);
	
	@Autowired
	UploadDAO uploadDAO;
	
	@Override
	public UploadResponse addDadosUpload(TbodUploadForcavenda tbodUpload) {

		log.info("[addDadosUpload - add dados via arquivo csv]");
		
		try {				
			uploadDAO.save(tbodUpload);
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar dados via arquivo csv (TbodUploadForcaVenda) :: Detalhe: [" + e.getMessage() + "]");
			return new UploadResponse(0, "Erro ao cadastrar dados via arquivo csv (TbodUploadForcaVenda). Detalhe: [" + e.getMessage() + "]");
		}
		return new UploadResponse(200, HttpStatus.OK.toString());
	}

}
