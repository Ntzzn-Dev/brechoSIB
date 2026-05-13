package model;

public class Categoria {
    private int codCat;
    private String nomeCat;

    public Categoria() {}

    public Categoria(int codCat, String nomeCat) {
        this.codCat = codCat;
        this.nomeCat = nomeCat;
    }

    public int getCodCat() {
        return codCat;
    }

    public void setCodCat(int codCat) {
        this.codCat = codCat;
    }

    public String getNomeCat() {
        return nomeCat;
    }

    public void setNomeCat(String nomeCat) {
        this.nomeCat = nomeCat;
    }

    public void showProp(){
        System.out.println(
            "CÓDIGO: " + this.getCodCat() + " > " + this.getNomeCat());
    }

    public boolean searchByTerm(String termo) {
        return this.getNomeCat().toUpperCase().contains(termo.toUpperCase());
    }

    public void copyFrom(Categoria cat){
        this.setCodCat(cat.getCodCat());
        this.setNomeCat(cat.getNomeCat());
    }
}