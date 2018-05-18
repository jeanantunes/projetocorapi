package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBOD_DEVICE_TOKEN")
public class TbodDeviceToken  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_DEVICE_TOKEN")
	@SequenceGenerator(
	        name="SEQ_DEVICE_TOKEN",
	        sequenceName="SEQ_DEVICE_TOKEN"
	    )
	@GeneratedValue(generator="SEQ_DEVICE_TOKEN",strategy=GenerationType.SEQUENCE)	
	private Long codigo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CD_LOGIN")
	private TbodLogin login;   
	
	@Column(name="ID_TOKEN")
	private String token;		
                    
	@Column(name="NM_MODEL")
	private String modelo;
	
	@Column(name="NM_OPERATION_SYSTEM")
	private String sistemaOperacional;
     
	@Column(name="DT_INCLUSAO")
	private Date dataInclusao = new Date();

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public TbodLogin getLogin() {
		return login;
	}

	public void setLogin(TbodLogin login) {
		this.login = login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}    
	
}
