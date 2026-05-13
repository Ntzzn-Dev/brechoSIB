package model;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import utils.BancoDeDados;

public class MetodoPagamento {
    private int codMetodoPag;
    private String descMetodoPag;
    private boolean ativoMetodoPag;

    public MetodoPagamento() {}

    public MetodoPagamento(int codMetodoPag, String descMetodoPag) {
        this(codMetodoPag, descMetodoPag, true);
    }

    public MetodoPagamento(int codMetodoPag, String descMetodoPag, boolean ativoMetodoPag) {
        this.codMetodoPag = codMetodoPag;
        this.descMetodoPag = descMetodoPag;
        this.ativoMetodoPag = ativoMetodoPag;
    }

    public int getCodMetodoPag() {
        return codMetodoPag;
    }

    public void setCodMetodoPag(int codMetodoPag) {
        this.codMetodoPag = codMetodoPag;
    }

    public String getDescMetodoPag() {
        return descMetodoPag;
    }

    public void setDescMetodoPag(String descMetodoPag) {
        this.descMetodoPag = descMetodoPag;
    }

    public boolean getAtivoMetodoPag() {
        return ativoMetodoPag;
    }

    public void setAtivoMetodoPag(boolean ativoMetodoPag) {
        this.ativoMetodoPag = ativoMetodoPag;
    }

    public void showMetodoPag(){
        System.out.println(
            "CÓDIGO: " + this.getCodMetodoPag() + " > " + this.getDescMetodoPag() + ", " + (ativoMetodoPag ? "ativo" : "desativado"));
    }

    public void copyFrom(MetodoPagamento mtd){
        this.setCodMetodoPag(mtd.getCodMetodoPag());
        this.setDescMetodoPag(mtd.getDescMetodoPag());
        this.setAtivoMetodoPag(mtd.getAtivoMetodoPag());
    }

    public boolean searchByTerm(String termo) {
        return this.getDescMetodoPag().toUpperCase().contains(termo.toUpperCase()) ||
        (this.getAtivoMetodoPag() ? "ativo" : "desativado").toUpperCase().contains(termo.toUpperCase());
    }
}