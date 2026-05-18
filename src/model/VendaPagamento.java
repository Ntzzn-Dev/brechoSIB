package model;

public class VendaPagamento {
    private MetodoPagamento metodoPagamento;
    private Venda venda;
    private double valorPago;

    public VendaPagamento() {}

    public VendaPagamento(MetodoPagamento metodoPagamento, Venda venda, double valorPago) {
        this.metodoPagamento = metodoPagamento;
        this.venda = venda;
        this.valorPago = valorPago;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public void showVendaPag(){
        System.out.println(
            "Método:     " + this.getMetodoPagamento().getDescMetodoPag() + 
            "\nParcela R$: " + String.format("%.2f", this.getValorPago()));
    }

    public void copyFrom(VendaPagamento vdPg){
        this.setMetodoPagamento(vdPg.getMetodoPagamento());
        this.setVenda(vdPg.getVenda());
        this.setValorPago(vdPg.getValorPago());
    }

    public boolean searchByTerm(String termo) {
        return this.getMetodoPagamento().getDescMetodoPag().toUpperCase().contains(termo.toUpperCase());
    }
}