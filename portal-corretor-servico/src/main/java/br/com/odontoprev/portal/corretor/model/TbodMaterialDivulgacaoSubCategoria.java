package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TBOD_MAT_SUBCATEG")
@NamedQuery(name = "TbodMaterialDivulgacaoSubCategoria.findAll", query = "SELECT t FROM TbodMaterialDivulgacaoSubCategoria t where ativo = 'S'")
public class TbodMaterialDivulgacaoSubCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_MAT_SUBCATEG")
	private Long codigoMaterialDivulgacaoSubCategoria;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "ATIVO")
	private String ativo; //S|N //201803031736 String seguindo padrao outras tabelas Corretora e ForcaVenda (esertorio@vector)(moliveira@odpv)
	
	@Column(name = "APP")
	private String app; //S|N //201803031736 String S|N seguindo padrao outras tabelas Corretora e ForcaVenda (esertorio@vector)(moliveira@odpv)
	
	@Column(name = "WEB")
	private String web; //S|N //201803031736 String S|N seguindo padrao outras tabelas Corretora e ForcaVenda (esertorio@vector)(moliveira@odpv)

	public TbodMaterialDivulgacaoSubCategoria() {
	}

	public Long getCodigoMaterialDivulgacaoSubCategoria() {
		return codigoMaterialDivulgacaoSubCategoria;
	}

	public void setCodigoMaterialDivulgacaoSubCategoria(Long codigoMaterialDivulgacaoSubCategoria) {
		this.codigoMaterialDivulgacaoSubCategoria = codigoMaterialDivulgacaoSubCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Override
	public String toString() {
		return "TbodMaterialDivulgacaoSubCategoria [" 
				+ "codigoMaterialDivulgacaoSubCategoria=" + codigoMaterialDivulgacaoSubCategoria 
				+ ", nome=" + nome 
				+ ", descricao=" + descricao 
				+ ", ativo=" + ativo 
				+ ", app=" + app 
				+ ", web=" + web 
				+ "]";
	}
	
}