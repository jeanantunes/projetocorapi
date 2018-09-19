package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ForcaVenda implements Serializable {

	private static final long serialVersionUID = 3665956677976317178L;

	private Long cdForcaVenda;
	private String nome;
	private String celular;
	private String email;
	private Corretora corretora;
	private String statusForcaVenda;
	private String cpf;
	private boolean ativo;
	private String departamento;
	private String cargo;
	private String dataNascimento;
	private String nomeEmpresa;
	private String nomeGerente;
	private String responsavel;
	private String rg;
	private String senha;
	private String canalVenda;
	private String status;
	private Login login;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Corretora getCorretora() {
		return corretora;
	}

	public void setCorretora(Corretora corretora) {
		this.corretora = corretora;
	}

	public String getStatusForcaVenda() {
		return statusForcaVenda;
	}

	public void setStatusForcaVenda(String statusForcaVenda) {
		this.statusForcaVenda = statusForcaVenda;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCanalVenda() {
		return canalVenda;
	}

	public void setCanalVenda(String canalVenda) {
		this.canalVenda = canalVenda;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "ForcaVenda [cdForcaVenda=" + cdForcaVenda + ", nome=" + nome + ", celular=" + celular + ", email="
				+ email + ", corretora=" + corretora + ", statusForcaVenda=" + statusForcaVenda + ", cpf=" + cpf
				+ ", ativo=" + ativo + ", departamento=" + departamento + ", cargo=" + cargo + ", dataNascimento="
				+ dataNascimento + ", nomeEmpresa=" + nomeEmpresa + ", nomeGerente=" + nomeGerente + ", responsavel="
				+ responsavel + ", rg=" + rg + ", senha=" + senha + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((canalVenda == null) ? 0 : canalVenda.hashCode());
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((cdForcaVenda == null) ? 0 : cdForcaVenda.hashCode());
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((corretora == null) ? 0 : corretora.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeEmpresa == null) ? 0 : nomeEmpresa.hashCode());
		result = prime * result + ((nomeGerente == null) ? 0 : nomeGerente.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((statusForcaVenda == null) ? 0 : statusForcaVenda.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ForcaVenda other = (ForcaVenda) obj;
		
		if(!this.toString().equals(other.toString()))
			return false;
		
//		if (ativo != other.ativo)
//			return false;
//		if (canalVenda == null) {
//			if (other.canalVenda != null)
//				return false;
//		} else if (!canalVenda.equals(other.canalVenda))
//			return false;
//		if (cargo == null) {
//			if (other.cargo != null)
//				return false;
//		} else if (!cargo.equals(other.cargo))
//			return false;
//		if (cdForcaVenda == null) {
//			if (other.cdForcaVenda != null)
//				return false;
//		} else if (!cdForcaVenda.equals(other.cdForcaVenda))
//			return false;
//		if (celular == null) {
//			if (other.celular != null)
//				return false;
//		} else if (!celular.equals(other.celular))
//			return false;
//		if (corretora == null) {
//			if (other.corretora != null)
//				return false;
//		} else if (!corretora.equals(other.corretora))
//			return false;
//		if (cpf == null) {
//			if (other.cpf != null)
//				return false;
//		} else if (!cpf.equals(other.cpf))
//			return false;
//		if (dataNascimento == null) {
//			if (other.dataNascimento != null)
//				return false;
//		} else if (!dataNascimento.equals(other.dataNascimento))
//			return false;
//		if (departamento == null) {
//			if (other.departamento != null)
//				return false;
//		} else if (!departamento.equals(other.departamento))
//			return false;
//		if (email == null) {
//			if (other.email != null)
//				return false;
//		} else if (!email.equals(other.email))
//			return false;
//		if (nome == null) {
//			if (other.nome != null)
//				return false;
//		} else if (!nome.equals(other.nome))
//			return false;
//		if (nomeEmpresa == null) {
//			if (other.nomeEmpresa != null)
//				return false;
//		} else if (!nomeEmpresa.equals(other.nomeEmpresa))
//			return false;
//		if (nomeGerente == null) {
//			if (other.nomeGerente != null)
//				return false;
//		} else if (!nomeGerente.equals(other.nomeGerente))
//			return false;
//		if (responsavel == null) {
//			if (other.responsavel != null)
//				return false;
//		} else if (!responsavel.equals(other.responsavel))
//			return false;
//		if (rg == null) {
//			if (other.rg != null)
//				return false;
//		} else if (!rg.equals(other.rg))
//			return false;
//		if (senha == null) {
//			if (other.senha != null)
//				return false;
//		} else if (!senha.equals(other.senha))
//			return false;
//		if (status == null) {
//			if (other.status != null)
//				return false;
//		} else if (!status.equals(other.status))
//			return false;
//		if (statusForcaVenda == null) {
//			if (other.statusForcaVenda != null)
//				return false;
//		} else if (!statusForcaVenda.equals(other.statusForcaVenda))
//			return false;
		return true;
	}

}
