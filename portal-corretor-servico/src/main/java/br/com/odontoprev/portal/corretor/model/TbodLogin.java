package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TBOD_LOGIN database table.
 * 
 */
@Entity
@Table(name="TBOD_LOGIN")
@NamedQuery(name="TbodLogin.findAll", query="SELECT t FROM TbodLogin t")
public class TbodLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_LOGIN")
	private long cdLogin;

	@Column(name="CD_TIPO_LOGIN")
	private BigDecimal cdTipoLogin;

	@Column(name="FOTO_PERFIL_B64")
	private String fotoPerfilB64;

	@Column(name="SENHA")
	private String senha;

	//bi-directional one-to-one association to TbodCorretora
	@ManyToOne
	@JoinColumn(name="CD_FORCA_VENDA_CORRETORA", insertable=false, updatable=false)
	private TbodCorretora tbodCorretora;

	//bi-directional many-to-one association to TbodForcaVenda
	@ManyToOne
	@JoinColumn(name="CD_FORCA_VENDA_CORRETORA", insertable=false, updatable=false)
	private TbodForcaVenda tbodForcaVenda;

	public TbodLogin() {
	}

	public long getCdLogin() {
		return this.cdLogin;
	}

	public void setCdLogin(long cdLogin) {
		this.cdLogin = cdLogin;
	}

	public BigDecimal getCdTipoLogin() {
		return this.cdTipoLogin;
	}

	public void setCdTipoLogin(BigDecimal cdTipoLogin) {
		this.cdTipoLogin = cdTipoLogin;
	}

	public String getFotoPerfilB64() {
		return this.fotoPerfilB64;
	}

	public void setFotoPerfilB64(String fotoPerfilB64) {
		this.fotoPerfilB64 = fotoPerfilB64;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TbodCorretora getTbodCorretora() {
		return this.tbodCorretora;
	}

	public void setTbodCorretora(TbodCorretora tbodCorretora) {
		this.tbodCorretora = tbodCorretora;
	}

	public TbodForcaVenda getTbodForcaVenda() {
		return this.tbodForcaVenda;
	}

	public void setTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		this.tbodForcaVenda = tbodForcaVenda;
	}

}