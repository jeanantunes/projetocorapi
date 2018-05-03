package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.UploadForcaCriticados;
import br.com.odontoprev.portal.corretor.dto.UploadResponse;
import br.com.odontoprev.portal.corretor.model.TbodUploadForcavenda;

public interface UploadService {

	/*add dados upload corretora*/
	UploadResponse addDadosUpload(TbodUploadForcavenda tbodUpload);
	
	List<UploadForcaCriticados> getCriticados();
}
