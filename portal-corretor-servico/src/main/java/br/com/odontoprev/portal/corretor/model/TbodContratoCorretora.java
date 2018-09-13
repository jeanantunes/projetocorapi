package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBOD_CONTRATO_CORRETORA")
public class TbodContratoCorretora implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
	@SequenceGenerator(name = "SEQ_TBOD_CONTRATO_CORRETORA", sequenceName = "SEQ_TBOD_CONTRATO_CORRETORA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator="SEQ_TBOD_CONTRATO_CORRETORA",strategy=GenerationType.SEQUENCE)	
    @Column(name = "CD_CONTRATO_CORRETORA")
    private Long cdContratoCorretora;
    
	@ManyToOne
	@JoinColumn(name = "CD_CORRETORA")
    private TbodCorretora tbodCorretora;
    
	@ManyToOne
	@JoinColumn(name = "CD_CONTRATO_MODELO")
    private TbodContratoModelo tbodContratoModelo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_ACEITE_CONTRATO")
    private Date dtAceiteContrato;

	public Long getCdContratoCorretora() {
		return cdContratoCorretora;
	}

	public void setCdContratoCorretora(Long cdContratoCorretora) {
		this.cdContratoCorretora = cdContratoCorretora;
	}

	public TbodCorretora getTbodCorretora() {
		return tbodCorretora;
	}

	public void setTbodCorretora(TbodCorretora tbodCorretora) {
		this.tbodCorretora = tbodCorretora;
	}

	public TbodContratoModelo getTbodContratoModelo() {
		return tbodContratoModelo;
	}

	public void setTbodContratoModelo(TbodContratoModelo tbodContratoModelo) {
		this.tbodContratoModelo = tbodContratoModelo;
	}

	public Date getDtAceiteContrato() {
		return dtAceiteContrato;
	}

	public void setDtAceiteContrato(Date dtAceiteContrato) {
		this.dtAceiteContrato = dtAceiteContrato;
	}

	@Override
	public String toString() {
		return "TbodContratoCorretora2 [" 
				+ "cdContratoCorretora=" + cdContratoCorretora 
				+ ", tbodCorretora=" + tbodCorretora
				+ ", tbodContratoModelo=" + tbodContratoModelo 
				+ ", dtAceiteContrato=" + dtAceiteContrato 
				+ "]";
	}

	
}