package model;

public class Funcionario extends Pessoas{
    private double salarioFunc;

    public Funcionario(){
        super();
    }

    public Funcionario(String cpf, String nome, String telefone, Endereco end, double salario) {
        super(cpf, nome, telefone, end);
        this.salarioFunc = salario;
    }

    public void setSalarioFunc(double sal){
        this.salarioFunc = sal;
    }

    public double getSalarioFunc(){
        return this.salarioFunc;
    }

    public void showFunc(){
        super.showPessoa();
        System.out.println("> SALARIO: " + this.getSalarioFunc());
    }

    public void copyFrom(Funcionario func){
        this.setCpfPessoa(func.getCpfPessoa());
        this.setNomePessoa(func.getNomePessoa());
        this.setTelPessoa(func.getTelPessoa());
        this.setEndPessoa(func.getEndPessoa());
        this.setSalarioFunc(func.getSalarioFunc());
    }

    public boolean searchByTerm(String termo) {
        return this.getNomePessoa().toUpperCase().contains(termo.toUpperCase()) ||
           this.getCpfPessoa().toUpperCase().contains(termo.toUpperCase()) ||
           this.getTelPessoa().toUpperCase().contains(termo.toUpperCase()) ||
           String.valueOf(this.getSalarioFunc()).toUpperCase().contains(termo.toUpperCase());
    }
}