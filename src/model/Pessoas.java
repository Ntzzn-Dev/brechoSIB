package model;

public abstract class Pessoas {
    private String cpfPessoa;
    private String nomePessoa;
    private String telPessoa;
    private Endereco endPessoa;

    public Pessoas() {}

    public Pessoas(String cpf, String nome, String tel, Endereco end) {
        this.cpfPessoa = cpf;
        this.nomePessoa = nome;
        this.telPessoa = tel;
        this.endPessoa = end;
    }

    public void setCpfPessoa(String cpf) {
        this.cpfPessoa = cpf;
    }

    public String getCpfPessoa(){
        return this.cpfPessoa;
    }

    public void setNomePessoa(String nome) {
        this.nomePessoa = nome;
    }

    public String getNomePessoa(){
        return this.nomePessoa;
    }

    public void setTelPessoa(String tel) {
        this.telPessoa = tel;
    }

    public String getTelPessoa(){
        return this.telPessoa;
    }

    public void setEndPessoa(Endereco end) {
        this.endPessoa = end;
    }

    public Endereco getEndPessoa(){
        return this.endPessoa;
    }

    public void showPessoa(){
        System.out.println(
            "CPF: " + this.getCpfPessoa() + "\n> NOME: " + this.getNomePessoa() + "\n> TEL: " + this.getTelPessoa());
    }
}