package model;

import utils.Formatacao;

public class Venda {
    private int codVenda;
    private String dataVenda;
    private Cliente clienteVenda;
    private Funcionario funcVenda;
    private double valorTotal;
    private int status;

    // 0 - CRIADA
    // 1 - PAGA
    // 2 - CANCELADA

    public Venda() {
        this.status = 0;
    }

    public Venda(int codVenda, String dataVenda, Cliente clienteVenda,
                 Funcionario funcVenda, double valorTotal, int status) {
        this.codVenda = codVenda;
        this.dataVenda = dataVenda;
        this.clienteVenda = clienteVenda;
        this.funcVenda = funcVenda;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Cliente getClienteVenda() {
        return clienteVenda;
    }

    public void setClienteVenda(Cliente clienteVenda) {
        this.clienteVenda = clienteVenda;
    }

    public Funcionario getFuncVenda() {
        return funcVenda;
    }

    public void setFuncVenda(Funcionario funcVenda) {
        this.funcVenda = funcVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusString() {
        switch (status){
            case 0:
                return "EM ABERTO";
            case 1:
                return "PAGA";
            case 2:
                return "CANCELADA";
            default:
                return "ERRO";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public void showVenda(){
        String codV = String.valueOf(this.getCodVenda());
        boolean cancelada = this.getStatus() == 2;

        System.out.println(
            "Código: " + codV + Formatacao.alinharDireita(this.getDataVenda(), 28 - codV.length()) + 
            "\nCliente: " + Formatacao.alinharDireita(this.getClienteVenda().getNomePessoa(), 27) + 
            "\nFuncionário: " + Formatacao.alinharDireita(this.getFuncVenda().getNomePessoa(), 23) + 
            "\n====================================" +
            "\nTOTAL: " + Formatacao.alinharDireita("R$" + String.format("%.2f", this.getValorTotal()), 29) +
            "\nSTATUS: " + (cancelada ? Formatacao.alinharDireita(this.getStatusString(), 28, "x") : Formatacao.alinharDireita(this.getStatusString(), 28)));
    }

    public void copyFrom(Venda venda){
        this.setCodVenda(venda.getCodVenda());
        this.setDataVenda(venda.getDataVenda());
        this.setClienteVenda(venda.getClienteVenda());
        this.setFuncVenda(venda.getFuncVenda());
        this.setValorTotal(venda.getValorTotal());
    }

    public boolean searchByTerm(String termo) {
        return this.getClienteVenda().getNomePessoa().toUpperCase().contains(termo.toUpperCase()) ||
        this.getFuncVenda().getNomePessoa().toUpperCase().contains(termo.toUpperCase()) ||
        this.getDataVenda().toUpperCase().contains(termo.toUpperCase()) ||
        String.valueOf(this.getValorTotal()).toUpperCase().contains(termo.toUpperCase()) ||
        this.getStatusString().toUpperCase().contains(termo.toUpperCase());
    }
}