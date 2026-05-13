package model;

public class Marca {
    private int codMarca;
    private String nomeMarca;

    public Marca() {}

    public Marca(int codMarca, String nomeMarca) {
        this.codMarca = codMarca;
        this.nomeMarca = nomeMarca;
    }

    public int getCodMarca() {
        return codMarca;
    }

    public void setCodMarca(int codMarca) {
        this.codMarca = codMarca;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }
    
    public void showMarca(){
        System.out.println(
            "CÓDIGO: " + this.getCodMarca() + " > " + this.getNomeMarca());
    }

    public void copyFrom(Marca marca){
        this.setCodMarca(marca.getCodMarca());
        this.setNomeMarca(marca.getNomeMarca());
    }

    public boolean searchByTerm(String termo) {
        return this.getNomeMarca().toUpperCase().contains(termo.toUpperCase());
    }
}