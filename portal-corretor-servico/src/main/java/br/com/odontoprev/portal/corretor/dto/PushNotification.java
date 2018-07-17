package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class PushNotification implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] destinations;
    private String title;
    private String message;
    private Map<String,String> dados;
    private String systemOperation;
    private String senderSystem;
    private String privateKey;
    private String projetoFirebase;





    public PushNotification() {
        super();
    }
    public String[] getDestinations() {
        return destinations;
    }
    public void setDestinations(String[] destinations) {
        this.destinations = destinations;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Map<String, String> getDados() {
        return dados;
    }
    public void setDados(Map<String, String> dados) {
        this.dados = dados;
    }
    public String getSystemOperation() {
        return systemOperation;
    }
    public void setSystemOperation(String systemOperation) {
        this.systemOperation = systemOperation;
    }
    public String getSenderSystem() {
        return senderSystem;
    }
    public void setSenderSystem(String senderSystem) {
        this.senderSystem = senderSystem;
    }
    public String getPrivateKey() {
        return privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getProjetoFirebase() {
        return projetoFirebase;
    }
    public void setProjetoFirebase(String projetoFirebase) {
        this.projetoFirebase = projetoFirebase;
    }

    @Override
    public String toString() {
        return "PushNotification{" +
                "destinations=" + Arrays.toString(destinations) +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", dados=" + dados +
                ", systemOperation='" + systemOperation + '\'' +
                ", senderSystem='" + senderSystem + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", projetoFirebase='" + projetoFirebase + '\'' +
                '}';
    }
}
