package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TBOD_MAT_CATEG")
@NamedQuery(name = "TbodMaterialDivulgacaoCategoria.findAll", query = "SELECT t FROM TbodMaterialDivulgacaoCategoria t where ativo = 'S'")
public class TbodMaterialDivulgacaoCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_MAT_CATEG")
	private Long codigoMaterialDivulgacaoCategoria;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "ATIVO")
	private String ativo; //S|N //201803031736 String seguindo padrao outras tabelas Corretora e ForcaVenda (esertorio@vector)(moliveira@odpv)

	public TbodMaterialDivulgacaoCategoria() {
	}

	public Long getCodigoMaterialDivulgacaoCategoria() {
		return codigoMaterialDivulgacaoCategoria;
	}

	public void setCodigoMaterialDivulgacaoCategoria(Long codigoMaterialDivulgacaoCategoria) {
		this.codigoMaterialDivulgacaoCategoria = codigoMaterialDivulgacaoCategoria;
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

	@Override
	public String toString() {
		return "TbodMaterialDivulgacaoCategoria [codigoMaterialDivulgacaoCategoria=" + codigoMaterialDivulgacaoCategoria
				+ ", nome=" + nome + ", descricao=" + descricao + ", ativo=" + ativo + "]";
	}

}