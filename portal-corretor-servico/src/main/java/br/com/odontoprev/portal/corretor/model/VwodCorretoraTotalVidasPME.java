package br.com.odontoprev.portal.corretor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

//201806071143 - esert - view para contagem de vidas por dt_venda, cnpj corretora, cpf corretor forca venda
//201806121639 - esert - padronizacao do nome da view de (VWOD_CORRETORA_TOTAL_VIDAS) para (VWOD_EMP_TOTAL_VIDAS_PME)
@Table(name = "VWOD_EMP_TOTAL_VIDAS_PME")
@Entity
public class VwodCorretoraTotalVidasPME implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7980931302249763552L;

    //venda.dt_venda,
    @Id
    @Column(name = "DT_VENDA") //01 - apenas um contador para nao perder a conta //201806111532 - esert
    private Date dtVenda;

    //cor.cnpj cnpj_corretora,
    @Column(name = "CNPJ_CORRETORA") //02
    private String cnpj_corretora;

    //cor.nome corretora,
    @Column(name = "CORRETORA") //03
    private String corretora;

    //forca.cpf,
    @Column(name = "CPF") //04
    private String cpf;

    //forca.nome vendedor,
    @Column(name = "VENDEDOR") //05
    private String vendedor;

    //empc.cnpj CNPJ_CLIENTE,
    @Column(name = "CNPJ_CLIENTE") //06
    private String cnpj_cliente;

    //empc.razao_social RAZAO_SOCIAL_CLIENTE,
    @Column(name = "RAZAO_SOCIAL_CLIENTE") //07
    private String razao_social_cliente;

    //emp.cd_empresa LOGIN_DCMS,
    @Column(name = "LOGIN_DCMS") //08
    private String login_dcms;

    //ende.logradouro,
    @Column(name = "LOGRADOURO") //09
    private String logradouro;

    //ende.numero,
    @Column(name = "NUMERO") //10
    private String numero;

    //ende.complemento,
    @Column(name = "COMPLEMENTO") //11
    private String complemento;

    //ende.bairro,
    @Column(name = "BAIRRO") //12
    private String bairro;

    //ende.cidade,
    @Column(name = "CIDADE") //13
    private String cidade;

    //ende.UF,
    @Column(name = "UF") //14
    private String uf;

    //ende.cep,
    @Column(name = "CEP") //15
    private String cep;

    //empc.representante_legal,
    @Column(name = "REPRESENTANTE_LEGAL") //16
    private String representante_legal;

    //empc.celular,
    @Column(name = "CELULAR") //17
    private String celular;

    //empc.email,
    @Column(name = "EMAIL") //18
    private String email;

    //--venda.cd_plano TOTAL_PLANOS,

    //plano.titulo PLANO,
    @Column(name = "PLANO") //19
    private String plano;

    //Count(vida.cd_vida) TOTAL_VIDAS,
    @Column(name = "TOTAL_VIDAS") //20
    private Integer total_vidas;

    //venda.fatura_vencimento DIA_FATURA
    @Column(name = "DIA_FATURA") //21
    private Integer dia_fatura;

    @Column(name = "VALOR_VENDA")
    private String valor_venda;

    @Column(name = "STATUS_VENDA")
    private String status_venda;

    public Date getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(Date dtVenda) {
        this.dtVenda = dtVenda;
    }

    public String getCnpj_corretora() {
        return cnpj_corretora;
    }

    public void setCnpj_corretora(String cnpj_corretora) {
        this.cnpj_corretora = cnpj_corretora;
    }

    public String getCorretora() {
        return corretora;
    }

    public void setCorretora(String corretora) {
        this.corretora = corretora;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCnpj_cliente() {
        return cnpj_cliente;
    }

    public void setCnpj_cliente(String cnpj_cliente) {
        this.cnpj_cliente = cnpj_cliente;
    }

    public String getRazao_social_cliente() {
        return razao_social_cliente;
    }

    public void setRazao_social_cliente(String razao_social_cliente) {
        this.razao_social_cliente = razao_social_cliente;
    }

    public String getLogin_dcms() {
        return login_dcms;
    }

    public void setLogin_dcms(String login_dcms) {
        this.login_dcms = login_dcms;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRepresentante_legal() {
        return representante_legal;
    }

    public void setRepresentante_legal(String representante_legal) {
        this.representante_legal = representante_legal;
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

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public Integer getTotal_vidas() {
        return total_vidas;
    }

    public void setTotal_vidas(Integer total_vidas) {
        this.total_vidas = total_vidas;
    }

    public Integer getDia_fatura() {
        return dia_fatura;
    }

    public void setDia_fatura(Integer dia_fatura) {
        this.dia_fatura = dia_fatura;
    }

    public String getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(String valor_venda) {
        this.valor_venda = valor_venda;
    }


    public String getStatus_venda() {
        return status_venda;
    }

    public void setStatus_venda(String status_venda) {
        this.status_venda = status_venda;
    }

    @Override
    public String toString() {
        return "VwodCorretoraTotalVidasPME{" +
                "dtVenda=" + dtVenda +
                ", cnpj_corretora='" + cnpj_corretora + '\'' +
                ", corretora='" + corretora + '\'' +
                ", cpf='" + cpf + '\'' +
                ", vendedor='" + vendedor + '\'' +
                ", cnpj_cliente='" + cnpj_cliente + '\'' +
                ", razao_social_cliente='" + razao_social_cliente + '\'' +
                ", login_dcms='" + login_dcms + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", uf='" + uf + '\'' +
                ", cep='" + cep + '\'' +
                ", representante_legal='" + representante_legal + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", plano='" + plano + '\'' +
                ", total_vidas=" + total_vidas +
                ", dia_fatura=" + dia_fatura +
                ", valor_venda='" + valor_venda + '\'' +
                ", status_venda='" + status_venda + '\'' +
                '}';
    }


}

//filosofia T__ELA & TABELA