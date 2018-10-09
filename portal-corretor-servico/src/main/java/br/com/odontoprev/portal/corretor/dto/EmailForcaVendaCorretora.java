package br.com.odontoprev.portal.corretor.dto;

public class EmailForcaVendaCorretora {

    private Long cdForcaVenda;
    private String emailForcaVenda;
    private Long cdCorretora;
    private String emailCorretora;

    public Long getCdForcaVenda() {
        return cdForcaVenda;
    }

    public void setCdForcaVenda(Long cdForcaVenda) {
        this.cdForcaVenda = cdForcaVenda;
    }

    public String getEmailForcaVenda() {
        return emailForcaVenda;
    }

    public void setEmailForcaVenda(String emailForcaVenda) {
        this.emailForcaVenda = emailForcaVenda;
    }

    public Long getCdCorretora() {
        return cdCorretora;
    }

    public void setCdCorretora(Long cdCorretora) {
        this.cdCorretora = cdCorretora;
    }

    public String getEmailCorretora() {
        return emailCorretora;
    }

    public void setEmailCorretora(String emailCorretora) {
        this.emailCorretora = emailCorretora;
    }
}
