package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Esta classe representa a composição da chave do token, está chave é composta por cdVenda + cdToken.
 */
@Embeddable
public class TbodTokenAceitePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="CD_VENDA", insertable=false, updatable=false)
	private long cdVenda;

	@Column(name="CD_TOKEN")
	private String cdToken;

	public TbodTokenAceitePK() {
	}
	public long getCdVenda() {
		return this.cdVenda;
	}
	public void setCdVenda(long cdVenda) {
		this.cdVenda = cdVenda;
	}
	public String getCdToken() {
		return this.cdToken;
	}
	public void setCdToken(String cdToken) {
		this.cdToken = cdToken;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TbodTokenAceitePK)) {
			return false;
		}
		TbodTokenAceitePK castOther = (TbodTokenAceitePK)other;
		return 
			(this.cdVenda == castOther.cdVenda)
			&& this.cdToken.equals(castOther.cdToken);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cdVenda ^ (this.cdVenda >>> 32)));
		hash = hash * prime + this.cdToken.hashCode();
		
		return hash;
	}
	
	
}
