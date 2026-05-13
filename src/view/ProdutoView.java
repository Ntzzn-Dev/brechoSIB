package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class ProdutoView implements BaseView<Produto>{
    private List<Produto> elementList = new ArrayList<>();

    public void insertList(List<Produto> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Produto> getElementList(){
        return this.elementList;
    }

    public Produto requestByCod(int codProd){
        for (Produto p : elementList) {
             if (p.getCodProd() == codProd) {
                return p;
            }
        }
        throw new IllegalArgumentException("Produto não encontrada");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (Produto p : elementList) {
             if (p.getCodProd() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }
    
    // Controle de Produtos ===========================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("================== PRODUTO ===================");
                System.out.println("                                              ");
                System.out.println("1 -              Criar produto             - 1");
                System.out.println("2 -             Editar produto             - 2");
                System.out.println("3 -             Excluir produto            - 3");
                System.out.println("4 -          Listar todas produtos         - 4");
                System.out.println("5 -            Procurar produto            - 5");
                System.out.println("9 -              <- Voltar ->              - 9");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.print("Digite a opção desejada: ");
                opcao = entrada.nextInt();
                entrada.nextLine();
                System.out.println("");

                switch(opcao){
                    case 1:
                        create(entrada);
                        break;
                    case 2:
                        update(entrada);
                        break;
                    case 3:
                        delete(entrada);
                        break;
                    case 4:
                        list(entrada);
                        break;
                    case 5:
                        read(entrada);
                        break;
                    case 9:
                        System.out.println("Voltando pra o menu \n");
                        break;
                }

            } while (opcao != 9);
        } catch (Exception e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void create(Scanner entrada){
        try{
            System.out.println("CRIAR NOVO PRODUTO: ");
            
            String descProd = Dados.requestValue(
                "Digite a descrição do produto: ", 
                "DESCRIÇÃO: ", 
                "CRIAÇÃO",
                false,
                entrada
            );
            System.out.println("");

            String tam;
            while (true) {
                System.out.println("Digite o tamanho do produto");
                System.out.println("(Caso não saiba, liste os tamanhos com /list)");
                System.out.print("TAMANHO: ");
                try{
                    String input = entrada.nextLine();

                    if(input.equals("/list")){
                        Formatacao.mostrarCartazTamanhos();
                        continue;
                    }

                    tam = Formatacao.formatarTamanho(input);
                    break;
                } catch (IllegalArgumentException e) {
                    Formatacao.patternError(e);
                }
            }
            System.out.println("");


            Marca marca = BancoDeDados.marcaV.requestByCod(
                    Dados.requestCod(
                    "Digite o código da marca: ", 
                    "CRIAÇÃO",
                    false,
                    BancoDeDados.marcaV,
                    entrada
                )
            );
            System.out.println("");

            Categoria cat = BancoDeDados.categoriaV.requestByCod(
                    Dados.requestCod(
                    "Digite o código da categoria: ", 
                    "CRIAÇÃO",
                    false,
                    BancoDeDados.categoriaV,
                    entrada
                )
            );
            System.out.println("");

            Venda venda = BancoDeDados.vendaV.requestByCod(
                    Dados.requestCod(
                    "Digite o código da venda: ", 
                    "CRIAÇÃO",
                    false,
                    BancoDeDados.vendaV,
                    entrada
                )
            );
            System.out.println("");

            elementList.add(new Produto(elementList.size() + 1, descProd, tam, marca, cat, venda));

            System.out.println("Produto registrado!");
            System.out.println("==============================================\n");
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR PRODUTO: ");
            
            Produto prod = requestByCod(
                    Dados.requestCod(
                    "Digite o código da produto que deseja alterar: ", 
                    "EDIÇÃO",
                    false,
                    BancoDeDados.produtoV,
                    entrada
                )
            );

            String descProd = Dados.requestValue(
                "Deixe em branco para manter ("+prod.getDescProd()+")\nDigite a nova descrição: ", 
                "DESCRIÇÃO: ",
                "EDIÇÃO",
                true,
                entrada
            );
            descProd = descProd.isEmpty() ? prod.getDescProd() : descProd;

            String tam;
            while (true) {
                System.out.println("Deixe em branco para manter ("+prod.getTamanhoProd()+")");
                System.out.print("Digite o novo tamanho do produto: ");
                System.out.println("(Caso não saiba, liste os tamanhos)");
                try{
                    tam = Formatacao.formatarTamanho(entrada.nextLine());
                    tam = tam.isEmpty() ? prod.getTamanhoProd() : tam;
                    break;
                } catch (IllegalArgumentException e) {
                    Formatacao.patternError(e);
                }
            }
            System.out.println("");

            int respostaM = Dados.requestCod(
                "Deixe em branco para manter ("+prod.getMarcaProd().getNomeMarca()+")\nDigite o código da marca: ",
                "EDIÇÃO",
                true,
                BancoDeDados.marcaV,
                entrada
            );
            
            Marca marca = BancoDeDados.marcaV.requestByCod(respostaM == -1 ? prod.getMarcaProd().getCodMarca() : respostaM);
            System.out.println("Marca selecionada: " + marca.getNomeMarca());  
            System.out.println("");

            int respostaC = Dados.requestCod(
                "Deixe em branco para manter ("+prod.getCatProd().getNomeCat()+")\nDigite o código da categoria: ", 
                "EDIÇÃO",
                true,
                BancoDeDados.categoriaV,
                entrada
            );

            Categoria cat = BancoDeDados.categoriaV.requestByCod(respostaC == -1 ? prod.getCatProd().getCodCat() : respostaC);
            System.out.println("Marca selecionada: " + cat.getNomeCat());  
            System.out.println("");

            int respostaV = Dados.requestCod(
                "Deixe em branco para manter ("+prod.getVendaProd().getCodVenda()+")\nDigite o código da venda: ", 
                "EDIÇÃO",
                true,
                BancoDeDados.vendaV,
                entrada
            );
            
            Venda venda = BancoDeDados.vendaV.requestByCod(respostaV == -1 ? prod.getVendaProd().getCodVenda() : respostaV);
            System.out.println("Venda selecionada");  
            System.out.println("");

            Produto newProd = new Produto(prod.getCodProd(), descProd, tam, marca, cat, venda);

            System.out.println("----------------------------------------------");
            System.out.println("PRODUTO ANTIGA: ");
            prod.showProp();
            System.out.println("PRODUTO ATUALIZADA): ");
            newProd.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição da produto? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    prod.copyFrom(newProd);
                    System.out.println("\nProduto atualizada!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de produto descartada!");
                    break;
                } else {
                    Formatacao.patternError(opcao);
                }
            }

            System.out.println("==============================================\n");
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override 
    public void delete(Scanner entrada){
        try{
            System.out.println("REMOVER PRODUTO: ");

            Produto prod = requestByCod(
                Dados.requestCod(
                    "Digite o código da produto que deseja apagar: ", 
                    "REMOÇÃO",
                    false,
                    BancoDeDados.categoriaV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            prod.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover a produto? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(prod);
                    System.out.println("\nRemoção da produto concluida!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nOperação de remoção cancelada!");
                    break;
                } else {
                    Formatacao.patternError(opcao);
                }
            }
            System.out.println("==============================================\n");
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void list(Scanner entrada){
        System.out.println("PRODUTOS REGISTRADAS: ");
        System.out.println("----------------------------------------------");
        for (Produto prod : elementList) {
            prod.showProp();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR PRODUTO: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoProd = entrada.nextLine();

        List<Produto> termosProdsEncontradas = new ArrayList<>();
        for (Produto prod : elementList) {
            if (prod.searchByTerm(termoProd)) {
                termosProdsEncontradas.add(prod);
            }
        }

        System.out.println("RESULTADOS [PRODUTO]: ");

        if(termosProdsEncontradas.isEmpty()){
            System.out.println("Nenhuma produto com esse termo encontrada\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Produto prod : termosProdsEncontradas) {
            prod.showProp();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}