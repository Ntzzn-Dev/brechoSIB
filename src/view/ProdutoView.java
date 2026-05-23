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

            Produto prod = new Produto();
            prod.setCodProd(elementList.size() + 1);

            Dados.reviewForm(() -> {
                if (prod.getDescProd() == null) {
                    prod.setDescProd(
                        Dados.requestValue(
                            "Digite a descrição do produto: ", 
                            "DESCRIÇÃO: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }

                if (prod.getTamanhoProd() == null) {
                    while (true) {
                        System.out.println("----------------------------------------------");
                        System.out.println("Digite o tamanho do produto");
                        System.out.println("Comandos: [/review] [/list] [/cancel]");
                        System.out.println("----------------------------------------------");
                        System.out.print("TAMANHO: ");
                        try{
                            String input = entrada.nextLine();

                            if(input.equals("/list")){
                                Formatacao.mostrarCartazTamanhos();
                                continue;
                            } else
                            if (input.equals("/cancel")){
                                throw new CancelOperationException("CRIAÇÃO");
                            } else
                            if (input.equals("/review")){
                                throw new ReviewOperationException("CRIAÇÃO");
                            }

                            System.out.println("");
                            prod.setTamanhoProd(Formatacao.formatarTamanho(input));
                        } catch (IllegalArgumentException e) {
                            Formatacao.patternError(e);
                        }
                    }
                }

                if (prod.getMarcaProd() == null) {
                    prod.setMarcaProd(
                       BancoDeDados.marcaV.requestByCod(
                            Dados.requestCod(
                                "Digite o código da marca: ", 
                                "CRIAÇÃO",
                                false,
                                BancoDeDados.marcaV,
                                entrada
                            )
                        )
                    );
                }

                if (prod.getCatProd() == null) {
                    prod.setCatProd(
                       BancoDeDados.categoriaV.requestByCod(
                                Dados.requestCod(
                                "Digite o código da categoria: ", 
                                "CRIAÇÃO",
                                false,
                                BancoDeDados.categoriaV,
                                entrada
                            )
                        )
                    );
                }
            }, () -> {
                review (prod, entrada);
            });
            
            System.out.println("");

            elementList.add(prod);

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
            
            Produto prodOld = requestByCod(
                    Dados.requestCod(
                    "Digite o código da produto que deseja alterar: ", 
                    "EDIÇÃO",
                    false,
                    BancoDeDados.produtoV,
                    entrada
                )
            );

            Produto prod = new Produto();
            prod.copyFrom(prodOld);

            Dados.reviewForm(() -> {
                if (prod.getDescProd().equals(prodOld.getDescProd())) {
                    String descProd = Dados.requestValue(
                        "Deixe em branco para manter ("+prod.getDescProd()+")\nDigite a nova descrição: ", 
                        "DESCRIÇÃO: ",
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    descProd = descProd.isEmpty() ? prod.getDescProd() : descProd;
                    prod.setDescProd(descProd);
                }

                if (prod.getTamanhoProd().equals(prodOld.getTamanhoProd())) {
                    while (true) {
                        System.out.println("----------------------------------------------");
                        System.out.println("Deixe em branco para manter ("+prod.getTamanhoProd()+")\nDigite o tamanho do produto");
                        System.out.println("Comandos: [/review] [/list] [/cancel]");
                        System.out.println("----------------------------------------------");
                        System.out.print("TAMANHO: ");
                        try{
                            String input = entrada.nextLine();

                            if(input.equals("/list")){
                                Formatacao.mostrarCartazTamanhos();
                                continue;
                            } else
                            if (input.equals("/cancel")){
                                throw new CancelOperationException("CRIAÇÃO");
                            } else
                            if (input.equals("/review")){
                                throw new ReviewOperationException("CRIAÇÃO");
                            }
                            
                            String tam = Formatacao.formatarTamanho(input);
                            tam = tam.isEmpty() ? prod.getTamanhoProd() : tam;

                            System.out.println("");
                            prod.setTamanhoProd(tam);
                        } catch (IllegalArgumentException e) {
                            Formatacao.patternError(e);
                        }
                    }
                }

                if (prod.getMarcaProd().getCodMarca() == prodOld.getMarcaProd().getCodMarca()) {
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
                    prod.setMarcaProd(marca);
                }

                if (prod.getCatProd().getCodCat() == prodOld.getCatProd().getCodCat()) {
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
                    prod.setCatProd(cat);
                }
            }, () -> {
                review (prod, entrada);
            });

            System.out.println("");

            System.out.println("----------------------------------------------");
            System.out.println("PRODUTO ANTIGA: ");
            prodOld.showProp();
            System.out.println("PRODUTO ATUALIZADA): ");
            prod.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição da produto? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    prodOld.copyFrom(prod);
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

    public void review(Produto prod, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("Código:        " + (prod.getCodProd() == -1 ? "Não preenchido ainda" : prod.getCodProd()));
        System.out.println("Descrição:     " + (prod.getDescProd() == null ? "Não preenchido ainda" : prod.getDescProd()));
        System.out.println("Tamanho:       " + (prod.getTamanhoProd() == null ? "Não preenchido ainda" : prod.getTamanhoProd()));
        System.out.println("Marca:         " + (prod.getMarcaProd() == null ? "Não preenchido ainda" : prod.getMarcaProd().getNomeMarca()));
        System.out.println("Categoria:     " + (prod.getCatProd() == null ? "Não preenchido ainda" : prod.getCatProd().getNomeCat()));
        System.out.println("Status:        " + (prod.getStatusVendido() ? "Vendido" : "Disponível"));
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}