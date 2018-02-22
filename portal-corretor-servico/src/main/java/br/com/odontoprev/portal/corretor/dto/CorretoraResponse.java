package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class CorretoraResponse implements Serializable {
	
	private static final long serialVersionUID = 2127819694346550728L;
	
	private long id;
	
	public CorretoraResponse(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	

}
