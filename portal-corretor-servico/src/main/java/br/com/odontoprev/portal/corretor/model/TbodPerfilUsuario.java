package br.com.odontoprev.portal.corretor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TBOD_PERFIL_USUARIO")
public class TbodPerfilUsuario {

	
	@Id
	@Column(name="CD_USUARIO")
	private String usuario;
	
	@JoinColumn(name="CD_PERFIL")
	@OneToOne
    private TbodPerfil perfil;

	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public TbodPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(TbodPerfil nomePerfil) {
		this.perfil = nomePerfil;
	}
}
