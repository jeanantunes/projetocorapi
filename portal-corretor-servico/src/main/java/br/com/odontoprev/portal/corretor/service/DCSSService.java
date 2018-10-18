package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.PropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMSResponse;

//201810171900 - esert - inc - COR-763:Isolar Inserção JSON Request DCMS
public interface DCSSService {

	public PropostaDCMSResponse chamarWSLegadoPropostaPOST(PropostaDCMS propostaDCMS);
	
}
