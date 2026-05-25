package model;
import java.util.Scanner;

public class Funcionario extends Pessoas{
    private double salarioFunc;
    private String senha;

    public Funcionario(){
        super();
    }

    public Funcionario(String cpf, String nome, String telefone, Endereco end, double salario, String senha) {
        super(cpf, nome, telefone, end);
        this.salarioFunc = salario;
        this.senha = senha;
    }

    public void setSalarioFunc(double sal){
        this.salarioFunc = sal;
    }

    public double getSalarioFunc(){
        return this.salarioFunc;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getSenha(){
        return this.senha;
    }

    public boolean validarSenha(Scanner entrada){
        System.out.println("SENHA: ");
        String senha = entrada.nextLine();
        boolean validada = this.senha == senha;
        
        if(!validada) System.out.println("Senha incorreta!");
        return validada;
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
        this.setSenha(func.getSenha());
    }

    public boolean searchByTerm(String termo) {
        return this.getNomePessoa().toUpperCase().contains(termo.toUpperCase()) ||
           this.getCpfPessoa().toUpperCase().contains(termo.toUpperCase()) ||
           this.getTelPessoa().toUpperCase().contains(termo.toUpperCase()) ||
           String.valueOf(this.getSalarioFunc()).toUpperCase().contains(termo.toUpperCase());
    }
}