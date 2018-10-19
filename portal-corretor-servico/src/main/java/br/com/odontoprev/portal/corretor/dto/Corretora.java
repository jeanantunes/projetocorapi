package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Corretora implements Serializable {

    private static final long serialVersionUID = 2180567984276302023L;

    private long cdCorretora;
    private String cnpj;
    private String razaoSocial;
    private String cnae;
    private String telefone;
    private String celular;
    private String email;
    private String statusCnpj;
    private String simplesNacional;
    private String dataAbertura;
    private Endereco enderecoCorretora;
    private Conta conta;
    private Login login;
    private String temSusep;
    private String codigoSusep;

    private String senha; //201807181317 - esert - COR-319 - incluida senha para json do put de troca de senha

    private List<Representante> representantes;

    public long getCdCorretora() {
        return cdCorretora;
    }

    public void setCdCorretora(long cdCorretora) {
        this.cdCorretora = cdCorretora;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnae() {
        return cnae;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getStatusCnpj() {
        return statusCnpj;
    }

    public void setStatusCnpj(String statusCnpj) {
        this.statusCnpj = statusCnpj;
    }

    public String getSimplesNacional() {
        return simplesNacional;
    }

    public void setSimplesNacional(String simplesNacional) {
        this.simplesNacional = simplesNacional;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Endereco getEnderecoCorretora() {
        return enderecoCorretora;
    }

    public void setEnderecoCorretora(Endereco enderecoCorretora) {
        this.enderecoCorretora = enderecoCorretora;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes) {
        this.representantes = representantes;
    }

    public String getTemSusep() {
        return temSusep;
    }

    public void setTemSusep(String temSusep) {
        this.temSusep = temSusep;
    }

    public String getCodigoSusep() {
        return codigoSusep;
    }

    public void setCodigoSusep(String codigoSusep) {
        this.codigoSusep = codigoSusep;
    }

    @Override
    public String toString() {
        return "Corretora{" +
                "cdCorretora=" + cdCorretora +
                ", cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnae='" + cnae + '\'' +
                ", telefone='" + telefone + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", statusCnpj='" + statusCnpj + '\'' +
                ", simplesNacional='" + simplesNacional + '\'' +
                ", dataAbertura='" + dataAbertura + '\'' +
                ", enderecoCorretora=" + enderecoCorretora +
                ", conta=" + conta +
                ", login=" + login +
                ", temSusep='" + temSusep + '\'' +
                ", codigoSusep='" + codigoSusep + '\'' +
                ", senha='" + senha + '\'' +
                ", representantes=" + representantes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corretora corretora = (Corretora) o;
        return cdCorretora == corretora.cdCorretora &&
                Objects.equals(cnpj, corretora.cnpj) &&
                Objects.equals(razaoSocial, corretora.razaoSocial) &&
                Objects.equals(cnae, corretora.cnae) &&
                Objects.equals(telefone, corretora.telefone) &&
                Objects.equals(celular, corretora.celular) &&
                Objects.equals(email, corretora.email) &&
                Objects.equals(statusCnpj, corretora.statusCnpj) &&
                Objects.equals(simplesNacional, corretora.simplesNacional) &&
                Objects.equals(dataAbertura, corretora.dataAbertura) &&
                Objects.equals(enderecoCorretora, corretora.enderecoCorretora) &&
                Objects.equals(conta, corretora.conta) &&
                Objects.equals(login, corretora.login) &&
                Objects.equals(temSusep, corretora.temSusep) &&
                Objects.equals(codigoSusep, corretora.codigoSusep) &&
                Objects.equals(senha, corretora.senha) &&
                Objects.equals(representantes, corretora.representantes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cdCorretora, cnpj, razaoSocial, cnae, telefone, celular, email, statusCnpj, simplesNacional, dataAbertura, enderecoCorretora, conta, login, temSusep, codigoSusep, senha, representantes);
    }
}
