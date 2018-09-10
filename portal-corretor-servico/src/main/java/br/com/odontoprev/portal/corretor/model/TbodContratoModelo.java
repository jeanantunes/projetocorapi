package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBOD_CONTRATO_MODELO")
public class TbodContratoModelo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CD_CONTRATO_MODELO")
    private Long cdContratoModelo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_CRIACAO")
    private Date dtCriacao;

    @Column(name = "ARQ_MODELO")
    private byte[] arquivoModelo;

	public Long getCdContratoModelo() {
		return cdContratoModelo;
	}

	public void setCdContratoModelo(Long cdContratoModelo) {
		this.cdContratoModelo = cdContratoModelo;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public byte[] getArquivoModelo() {
		return arquivoModelo;
	}

	public void setArquivoModelo(byte[] arquivoModelo) {
		this.arquivoModelo = arquivoModelo;
	}

	@Override
	public String toString() {
		return "TbodContratoModelo [" 
				+ "cdContratoModelo=" + cdContratoModelo 
				+ ", dtCriacao=" + dtCriacao
				+ ", arquivoModelo=" + (arquivoModelo != null ? String.valueOf(Arrays.toString(arquivoModelo).length()) : "NuLL") + "]";
	}

}