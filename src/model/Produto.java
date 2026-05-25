package model;

public class Produto {
    private int codProd;
    private String descProd;
    private String tamanhoProd;
    private Marca marcaProd;
    private Categoria catProd;
    private boolean statusVendido;
    private double precoProd;

    public Produto() {}

    public Produto(int codProd, String descProd, String tamanhoProd, Marca marcaProd, 
            Categoria catProd, boolean statusVendido, double precoProd) {
        this.codProd = codProd;
        this.descProd = descProd;
        this.tamanhoProd = tamanhoProd;
        this.marcaProd = marcaProd;
        this.catProd = catProd;
        this.statusVendido = statusVendido;
        this.precoProd = precoProd;
    }

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public String getDescProd() {
        return descProd;
    }

    public void setDescProd(String descProd) {
        this.descProd = descProd;
    }

    public String getTamanhoProd() {
        return tamanhoProd;
    }

    public void setTamanhoProd(String tamanhoProd) {
        this.tamanhoProd = tamanhoProd;
    }

    public Marca getMarcaProd() {
        return marcaProd;
    }

    public void setMarcaProd(Marca marcaProd) {
        this.marcaProd = marcaProd;
    }

    public Categoria getCatProd() {
        return catProd;
    }

    public void setCatProd(Categoria catProd) {
        this.catProd = catProd;
    }

    public boolean getStatusVendido() {
        return statusVendido;
    }

    public void setStatusVendido(boolean vendido) {
        this.statusVendido = vendido;
    }

    public double getPrecoProd() {
        return precoProd;
    }

    public void setPrecoProd(double precoProd) {
        this.precoProd = precoProd;
    }

    public void showProp(){
        System.out.println(
            "CÓDIGO: " + this.getCodProd() + " > " + this.getMarcaProd().getNomeMarca() + " - " + 
            this.getMarcaProd().getNomeMarca() + "\n[" + this.getTamanhoProd() + "] " + this.getCatProd().getNomeCat() + 
            ", R$: " + this.getPrecoProd() + "\nproduto " + (this.getStatusVendido() ? "vendido" : "disponível"));
    }

    public void copyFrom(Produto prod){
        this.setCodProd(prod.getCodProd());
        this.setDescProd(prod.getDescProd());
        this.setTamanhoProd(prod.getTamanhoProd());
        this.setMarcaProd(prod.getMarcaProd());
        this.setCatProd(prod.getCatProd());
    }

    public boolean searchByTerm(String termo) {
        
        return this.getDescProd().toUpperCase().contains(termo.toUpperCase()) ||
        this.getTamanhoProd().toUpperCase().contains(termo.toUpperCase()) ||
        this.getMarcaProd().getNomeMarca().toUpperCase().contains(termo.toUpperCase()) ||
        this.getCatProd().getNomeCat().toUpperCase().contains(termo.toUpperCase());
    }
}