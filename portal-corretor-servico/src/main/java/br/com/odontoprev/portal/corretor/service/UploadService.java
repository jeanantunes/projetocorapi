package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.UploadResponse;
import br.com.odontoprev.portal.corretor.model.TbodUpload;

public interface UploadService {

	/*add dados upload corretora*/
	UploadResponse addDadosUpload(TbodUpload tbodUpload);
}
