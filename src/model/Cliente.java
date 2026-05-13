package model;

public class Cliente extends Pessoas{
    public Cliente(){
        super();
    }

    public Cliente(String id, String nome, String telefone, Endereco end) {
        super(id, nome, telefone, end);
    }
    
    public void showProp(){
        super.showPessoa();
    }

    public void copyFrom(Cliente client){
        this.setCpfPessoa(client.getCpfPessoa());
        this.setNomePessoa(client.getNomePessoa());
        this.setTelPessoa(client.getTelPessoa());
        this.setEndPessoa(client.getEndPessoa());
    }

    public boolean searchByTerm(String termo) {
        return this.getNomePessoa().toUpperCase().contains(termo.toUpperCase()) ||
           this.getCpfPessoa().toUpperCase().contains(termo.toUpperCase()) ||
           this.getTelPessoa().toUpperCase().contains(termo.toUpperCase());
    }
}