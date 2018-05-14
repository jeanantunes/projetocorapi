package br.com.odontoprev.portal.corretor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.UploadDAO;
import br.com.odontoprev.portal.corretor.dto.UploadForcaCriticados;
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

	public List<UploadForcaCriticados> getCriticados() {
		
		log.info("[UploadServiceImpl::getCriticados]");
		
		List<TbodUploadForcavenda> entities = new ArrayList<TbodUploadForcavenda>();
		
		entities = uploadDAO.findAll();
				
		return adaptEntityToDto(entities);
		
	}

	private List<UploadForcaCriticados> adaptEntityToDto(List<TbodUploadForcavenda> entities){
		
		List<UploadForcaCriticados> forcas = new ArrayList<UploadForcaCriticados>();
		
		for (TbodUploadForcavenda forca : entities) {
			 
			UploadForcaCriticados upload = new UploadForcaCriticados();
			upload.setNome(forca.getNome());
			upload.setEmail(forca.getEmail());
			upload.setCpf(forca.getCpf());
			upload.setCelular(forca.getCelular());
			upload.setCargo(forca.getCargo());
			upload.setDepartamento(forca.getDepartamento());
			upload.setDataNascimento(forca.getDataNascimento());
			upload.setCdCorretora(forca.getCdCorretora());
			upload.setCritica(forca.getCritica());						
			forcas.add(upload);
		}
		
		return forcas;		
	}
}
