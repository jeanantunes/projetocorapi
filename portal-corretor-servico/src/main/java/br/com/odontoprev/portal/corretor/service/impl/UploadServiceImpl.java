package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.UploadDAO;
import br.com.odontoprev.portal.corretor.dto.UploadResponse;
import br.com.odontoprev.portal.corretor.model.TbodUpload;
import br.com.odontoprev.portal.corretor.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService{

	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);
	
	@Autowired
	UploadDAO uploadDAO;
	
	@Override
	public UploadResponse addDadosUpload(TbodUpload tbodUpload) {

		log.info("[addDadosUpload - add dados via planilha dos corretores]");
		
		try {
			//TbodUpload tbodUpload = new TbodUpload();
			//tbodUpload.setCnpj(upload.getCnpj());
			//tbodUpload.setArquivo(upload.getArquivo());
			//tbodUpload.setDataCriacao(new Date());
			//tbodUpload.setNome(upload.getNome());
			//tbodUpload.setCpf(upload.getCpf());
			//tbodUpload.setDataNascimento(upload.getDataNascimento());
			//tbodUpload.setCelular(upload.getNome());
			//tbodUpload.setEmail(upload.getEmail());
			//tbodUpload.setDepartamento(upload.getDepartamento());
			//tbodUpload.setCargo(upload.getCargo());
			uploadDAO.save(tbodUpload);
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar dados da corretora via arquivo excel (TbodUpload) :: Detalhe: [" + e.getMessage() + "]");
			return new UploadResponse(0, "Erro ao cadastrar dados da corretora via arquivo excel (TbodUpload). Detalhe: [" + e.getMessage() + "]");
		}
		return new UploadResponse(200, HttpStatus.OK.toString());
	}

}
