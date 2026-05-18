package model;

public class Endereco {
    private int codEnd;
    private String uf;
    private String cep;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;

    public Endereco() {
    }

    public Endereco(int codEnd, String uf, String cep, String cidade, String bairro, String logradouro, String numero, String complemento) {
        this.codEnd = codEnd;
        this.uf = uf;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public int getCodEnd() {
        return codEnd;
    }

    public void setCodEnd(int codEnd) {
        this.codEnd = codEnd;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
        
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public void copyFrom(Endereco end){
        this.setCodEnd(end.getCodEnd());
        this.setUf(end.getUf());
        this.setCep(end.getCep());
        this.setCidade(end.getCidade());
        this.setBairro(end.getBairro());
        this.setLogradouro(end.getLogradouro());
        this.setNumero(end.getNumero());
        this.setComplemento(end.getComplemento());
    }

    public boolean searchByTerm(String termo) {
        return this.getBairro().toUpperCase().contains(termo.toUpperCase()) ||
           this.getCep().toUpperCase().contains(termo.toUpperCase()) ||
           this.getCidade().toUpperCase().contains(termo.toUpperCase()) ||
           this.getLogradouro().toUpperCase().contains(termo.toUpperCase()) ||
           this.getComplemento().toUpperCase().contains(termo.toUpperCase()) ||
           this.getNumero().toUpperCase().contains(termo.toUpperCase()) ||
           this.getUf().toUpperCase().contains(termo.toUpperCase());
    }

    public void showProp(){
        System.out.println(
            "CÓDIGO: " + this.getCodEnd() + " > " + this.getBairro() + ", " + this.getLogradouro() + ", " + this.getNumero() + "-" + this.getComplemento() + "\n[" + this.getUf() + "] " + this.getCidade() + ", " + this.getCep());
    }
}