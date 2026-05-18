package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class CategoriaView implements BaseView<Categoria>{
    private List<Categoria> elementList = new ArrayList<>();

    public void insertList(List<Categoria> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Categoria> getElementList(){
        return this.elementList;
    }

    public Categoria requestByCod(int codCat){
        for (Categoria c : elementList) {
             if (c.getCodCat() == codCat) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (Categoria c : elementList) {
             if (c.getCodCat() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }
    
    // Controle de Categorias =================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("================= CATEGORIA ==================");
                System.out.println("                                              ");
                System.out.println("1 -             Criar categoria            - 1");
                System.out.println("2 -            Editar categoria            - 2");
                System.out.println("3 -            Excluir categoria           - 3");
                System.out.println("4 -         Listar todas categorias        - 4");
                System.out.println("5 -           Procurar categoria           - 5");
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
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void create(Scanner entrada){
        try{
            System.out.println("CRIAR NOVA CATEGORIA: ");

            Categoria cat = new Categoria();
            cat.setCodCat(elementList.size() + 1);

            Dados.reviewForm(() -> {
                if (cat.getNomeCat() == null) {
                        cat.setNomeCat(
                            Dados.requestValue(
                            "Digite o nome da categoria: ",
                            "NOME: ",
                            "CRIAÇÃO", 
                            false, 
                            entrada
                        )
                    );
                }
            }, () -> {
                review(cat, entrada);
            });

            elementList.add(cat);

            System.out.println("Categoria registrada!");
            System.out.println("==============================================\n");
        }catch (Exception e) {
            Formatacao.patternError(e);
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR CATEGORIA: ");

            Categoria catOld = requestByCod(
                Dados.requestCod(
                    "Digite o código da categoria que deseja alterar: ", 
                    "EDIÇÃO",
                    false,
                    BancoDeDados.categoriaV,
                    entrada
                )
            );

            Categoria cat = new Categoria();
            cat.copyFrom(catOld);
            
            Dados.reviewForm(() -> {
                if (cat.getNomeCat().equals(catOld.getNomeCat())) {
                    String nomeCat = Dados.requestValue(
                        "Deixe em branco para manter ("+cat.getNomeCat()+")\nDigite o novo nome: ", 
                        "NOME: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    nomeCat = nomeCat.isEmpty() ? cat.getNomeCat() : nomeCat;
                    cat.setNomeCat(nomeCat);
                }
            }, () -> {
                review(cat, entrada);
            });

            System.out.println("----------------------------------------------");
            System.out.println("CATEGORIA ANTIGA: ");
            catOld.showProp();
            System.out.println("CATEGORIA ATUALIZADA): ");
            cat.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição da categoria? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    catOld.copyFrom(cat);
                    System.out.println("\nCategoria atualizada!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de categoria descartada!");
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
            System.out.println("REMOVER CATEGORIA: ");
            
            Categoria cat = requestByCod(
                Dados.requestCod(
                    "Digite o código da categoria que deseja apagar: ",
                    "REMOÇÃO",
                    false,
                    BancoDeDados.categoriaV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            cat.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover a categoria? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(cat);
                    System.out.println("\nRemoção da categoria concluida!");
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
        System.out.println("CATEGORIAS REGISTRADAS: ");
        System.out.println("----------------------------------------------");
        for (Categoria cat : elementList) {
            cat.showProp();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR CATEGORIA: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoCat = entrada.nextLine();

        List<Categoria> termosCatsEncontradas = new ArrayList<>();
        for (Categoria cat : elementList) {
            if (cat.searchByTerm(termoCat)) {
                termosCatsEncontradas.add(cat);
            }
        }

        System.out.println("RESULTADOS [CATEGORIA]: ");

        if(termosCatsEncontradas.isEmpty()){
            System.out.println("Nenhuma categoria com esse termo encontrada\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Categoria cat : termosCatsEncontradas) {
            cat.showProp();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    public void review(Categoria cat, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("Código:        " + (cat.getCodCat() == -1 ? "Não preenchido ainda" : cat.getCodCat()));
        System.out.println("Nome:          " + (cat.getNomeCat() == null ? "Não preenchido ainda" : cat.getNomeCat()));
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}