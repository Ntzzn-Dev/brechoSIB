package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.*;
import view.*;

public class BancoDeDados {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    public static CategoriaView categoriaV;
    public static ClienteView clienteV;
    public static EnderecoView enderecoV;
    public static FuncionarioView funcionarioV;
    public static MarcaView marcaV;
    public static MetodoPagamentoView metodoPagamentoV;
    public static VendaView vendaV;
    public static ProdutoView produtoV;
    // =========================================================
    // ENDEREÇOS
    // =========================================================

    public static void serializeEnd() {
        if (enderecoV != null) return;
        enderecoV = new EnderecoView();

        enderecoV.insertList(List.of(
            new Endereco(1, "SP", "01001000", "São Paulo", "Centro", "Rua A", "100", "Apto 1"),
            new Endereco(2, "RJ", "20040002", "Rio de Janeiro", "Copacabana", "Rua B", "200", "Casa"),
            new Endereco(3, "MG", "30140071", "Belo Horizonte", "Savassi", "Av. C", "300", ""),
            new Endereco(4, "RS", "90010000", "Porto Alegre", "Centro", "Rua D", "400", ""),
            new Endereco(5, "PR", "80010000", "Curitiba", "Batel", "Rua E", "500", "Sala 2"),
            new Endereco(6, "BA", "40010000", "Salvador", "Pelourinho", "Rua F", "600", ""),
            new Endereco(7, "PE", "50010000", "Recife", "Boa Viagem", "Av. G", "700", "Apto 7")
        ));
    }

    // =========================================================
    // FUNCIONÁRIOS
    // =========================================================

    public static void serializeFun() {
        if (funcionarioV != null) return;
        funcionarioV = new FuncionarioView();

        serializeEnd();

        List<Endereco> ends = enderecoV.getElementList();

        funcionarioV.insertList(List.of(
            new Funcionario("12345678901", "João Silva", "11999999999", ends.get(0), 2500.0),
            new Funcionario("98765432100", "Maria Souza", "21988888888", ends.get(1), 3200.0),
            new Funcionario("11122233344", "Carlos Lima", "31977777777", ends.get(2), 2800.0),
            new Funcionario("22233344455", "Ana Paula", "51966666666", ends.get(3), 3000.0),
            new Funcionario("33344455566", "Bruno Costa", "41955555555", ends.get(4), 2700.0),
            new Funcionario("44455566677", "Fernanda Alves", "71944444444", ends.get(5), 3500.0),
            new Funcionario("55566677788", "Lucas Rocha", "81933333333", ends.get(6), 2900.0)
        ));
    }

    // =========================================================
    // CLIENTES
    // =========================================================

    public static void serializeCli() {
        if (clienteV != null) return;
            clienteV = new ClienteView();

        serializeEnd();

        List<Endereco> ends = enderecoV.getElementList();

        clienteV.insertList(List.of(
            new Cliente("99988877766", "Pedro Gomes", "11911111111", ends.get(0)),
            new Cliente("88877766655", "Juliana Martins", "21922222222", ends.get(1)),
            new Cliente("77766655544", "Rafael Dias", "31933333333", ends.get(2)),
            new Cliente("66655544433", "Camila Freitas", "51944444444", ends.get(3)),
            new Cliente("55544433322", "Thiago Ribeiro", "41955555555", ends.get(4)),
            new Cliente("44433322211", "Patrícia Nunes", "71966666666", ends.get(5)),
            new Cliente("33322211100", "Gabriel Mendes", "81977777777", ends.get(6))
        ));
    }

    // =========================================================
    // MÉTODOS DE PAGAMENTO
    // =========================================================

    public static void serializeMtd() {
        if (metodoPagamentoV != null) return;
        metodoPagamentoV = new MetodoPagamentoView();

        metodoPagamentoV.insertList(List.of(
            new MetodoPagamento(1, "Dinheiro", true),
            new MetodoPagamento(2, "Cartão de Débito", true),
            new MetodoPagamento(3, "Cartão de Crédito", true),
            new MetodoPagamento(4, "Pix", true),
            new MetodoPagamento(5, "Boleto", false),
            new MetodoPagamento(6, "Transferência Bancária", false),
            new MetodoPagamento(7, "Carteira Digital", true)
        ));
    }

    // =========================================================
    // MARCAS
    // =========================================================

    public static void serializeMar() {
        if (marcaV != null) return;
        marcaV = new MarcaView();

        marcaV.insertList(List.of(
            new Marca(1, "Nike"),
            new Marca(2, "Adidas"),
            new Marca(3, "Puma"),
            new Marca(4, "Zara"),
            new Marca(5, "Hering"),
            new Marca(6, "Renner"),
            new Marca(7, "C&A")
        ));
    }

    // =========================================================
    // CATEGORIAS
    // =========================================================

    public static void serializeCat() {
        if(categoriaV != null) return;
            categoriaV = new CategoriaView();

        categoriaV.insertList(List.of(
            new Categoria(1, "Camiseta"),
            new Categoria(2, "Calça"),
            new Categoria(3, "Jaqueta"),
            new Categoria(4, "Vestido"),
            new Categoria(5, "Shorts"),
            new Categoria(6, "Tênis"),
            new Categoria(7, "Acessórios")
        ));
    }

    // =========================================================
    // VENDAS
    // =========================================================

    public static void serializeVen() {
        if (vendaV != null) return;
        vendaV = new VendaView();

        serializeCli();
        serializeFun();
        serializeMtd();

        List<Cliente> clientes = clienteV.getElementList();
        List<Funcionario> funcs = funcionarioV.getElementList();

        LocalDateTime agora = LocalDateTime.now();

        vendaV.insertList(List.of(
            new Venda(1, agora.minusDays(2).format(FORMATTER), clientes.get(0), funcs.get(0), 199.90, 2),
            new Venda(2, agora.minusDays(1).format(FORMATTER), clientes.get(1), funcs.get(1), 349.50, 2),
            new Venda(3, agora.minusHours(5).format(FORMATTER), clientes.get(2), funcs.get(2), 89.90, 0),
            new Venda(4, agora.minusHours(2).format(FORMATTER), clientes.get(3), funcs.get(3), 120.00, 0),
            new Venda(5, agora.minusDays(3).format(FORMATTER), clientes.get(4), funcs.get(4), 560.75, 1),
            new Venda(6, agora.minusDays(4).format(FORMATTER), clientes.get(5), funcs.get(5), 220.40, 1),
            new Venda(7, agora.minusMinutes(30).format(FORMATTER), clientes.get(6), funcs.get(6), 75.99, 0)
        ));
        
        List<MetodoPagamento> mtd = metodoPagamentoV.getElementList();
        List<Venda> vnd = vendaV.getElementList();

        vendaV.insertVendasPagamentosList(List.of(
            new VendaPagamento(mtd.get(3), vnd.get(4), 300.75),
            new VendaPagamento(mtd.get(2), vnd.get(4), 260.00),

            new VendaPagamento(mtd.get(0), vnd.get(5), 100.40),
            new VendaPagamento(mtd.get(1), vnd.get(5), 120.00)
        ));
    }

    // =========================================================
    // PRODUTOS
    // =========================================================

    public static void serializePro() {
        if (produtoV != null) return;
        produtoV = new ProdutoView();

        serializeMar();
        serializeCat();
        serializeVen();

        List<Marca> marcas = marcaV.getElementList();
        List<Categoria> categorias = categoriaV.getElementList();
        List<Venda> vendas = vendaV.getElementList();

        produtoV.insertList(List.of(
            new Produto(1, "Camiseta básica branca", "M", marcas.get(0), categorias.get(0), vendas.get(0)),
            new Produto(2, "Calça jeans slim", "42", marcas.get(1), categorias.get(1), vendas.get(1)),
            new Produto(3, "Jaqueta couro preta", "G", marcas.get(2), categorias.get(2), vendas.get(2)),
            new Produto(4, "Vestido floral verão", "P", marcas.get(3), categorias.get(3), vendas.get(3)),
            new Produto(5, "Shorts esportivo", "M", marcas.get(4), categorias.get(4), vendas.get(4)),
            new Produto(6, "Tênis corrida", "42", marcas.get(0), categorias.get(5), vendas.get(5)),
            new Produto(7, "Camisa Polo", "M", marcas.get(6), categorias.get(6), vendas.get(6)),
            new Produto(8, "Camiseta oversized", "GG", marcas.get(1), categorias.get(0), vendas.get(0)),
            new Produto(9, "Calça moletom", "G", marcas.get(5), categorias.get(1), vendas.get(1)),
            new Produto(10, "Tênis casual", "40", marcas.get(2), categorias.get(5), vendas.get(2))
        ));
    }
}