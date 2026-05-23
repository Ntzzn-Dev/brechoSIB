package model;

public class VendaPagamento {
    private MetodoPagamento metodoPagamento;
    private double valorPago;

    public VendaPagamento() {}

    public VendaPagamento(MetodoPagamento metodoPagamento, double valorPago) {
        this.metodoPagamento = metodoPagamento;
        this.valorPago = valorPago;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
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
        this.setValorPago(vdPg.getValorPago());
    }

    public boolean searchByTerm(String termo) {
        return this.getMetodoPagamento().getDescMetodoPag().toUpperCase().contains(termo.toUpperCase());
    }
}