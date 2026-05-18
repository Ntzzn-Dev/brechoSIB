package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class MarcaView implements BaseView<Marca>{
    private List<Marca> elementList = new ArrayList<>();

    public void insertList(List<Marca> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Marca> getElementList(){
        return this.elementList;
    }
        
    public Marca requestByCod(int codMarca){
        for (Marca m : elementList) {
             if (m.getCodMarca() == codMarca) {
                return m;
            }
        }
        throw new IllegalArgumentException("Marca não encontrada");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (Marca m : elementList) {
             if (m.getCodMarca() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }

    // Controle de Marcas ===============================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("=================== MARCAS ===================");
                System.out.println("                                              ");
                System.out.println("1 -               Criar marca              - 1");
                System.out.println("2 -              Editar marca              - 2");
                System.out.println("3 -              Excluir marca             - 3");
                System.out.println("4 -           Listar todas marcas          - 4");
                System.out.println("5 -             Procurar marca             - 5");
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
            System.out.println("CRIAR NOVA MARCA: ");

            Marca marca = new Marca();
            marca.setCodMarca(elementList.size() + 1);

            Dados.reviewForm(() -> {
                if (marca.getNomeMarca() == null) {
                    marca.setNomeMarca(
                        Dados.requestValue(
                            "Digite o nome da marca: ", 
                            "NOME: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }
            }, () -> {
                review (marca, entrada);
            });

            System.out.println("");

            elementList.add(marca);

            System.out.println("Marca registrada!");
            System.out.println("==============================================\n");
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR MARCA: ");

            Marca marcaOld = requestByCod(
                Dados.requestCod(
                    "Digite o código da marca que deseja alterar: ", 
                    "EDIÇÃO",
                    false,
                    BancoDeDados.marcaV,
                    entrada
                )
            );

            Marca marca = new Marca();
            marca.copyFrom(marcaOld);

            Dados.reviewForm(() -> {
                if (marca.getNomeMarca().equals(marcaOld.getNomeMarca())) {
                    String nomeMarca = Dados.requestValue(
                        "Deixe em branco para manter ("+marca.getNomeMarca()+")\nDigite o novo nome: ", 
                        "NOME: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    nomeMarca = nomeMarca.isEmpty() ? marca.getNomeMarca() : nomeMarca;
                    marca.setNomeMarca(nomeMarca);
                }
            }, () -> {
                review(marca, entrada);
            });

            System.out.println("");

            System.out.println("----------------------------------------------");
            System.out.println("MARCA ANTIGA: ");
            marcaOld.showMarca();
            System.out.println("MARCA ATUALIZADA): ");
            marca.showMarca();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição da Marca? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    marcaOld.copyFrom(marca);
                    System.out.println("\nMarca atualizada!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de marca descartada!");
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
            System.out.println("REMOVER MARCA: ");

            Marca marca = requestByCod(
                Dados.requestCod(
                    "Digite o código da marca que deseja apagar: ",
                    "REMOÇÃO" ,
                    false,
                    BancoDeDados.marcaV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            marca.showMarca();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover a marca? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(marca);
                    System.out.println("\nRemoção da marca concluida!");
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
        System.out.println("MARCAS REGISTRADAS: ");
        System.out.println("----------------------------------------------");
        for (Marca marca : elementList) {
            marca.showMarca();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR MARCA: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoMarca = entrada.nextLine();

        List<Marca> termosMarcasEncontradas = new ArrayList<>();
        for (Marca marca : elementList) {
            if (marca.searchByTerm(termoMarca)) {
                termosMarcasEncontradas.add(marca);
            }
        }

        System.out.println("RESULTADOS [MARCA]: ");

        if(termosMarcasEncontradas.isEmpty()){
            System.out.println("Nenhuma marca com esse termo encontrada\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Marca marca : termosMarcasEncontradas) {
            marca.showMarca();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    public void review(Marca marca, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("Código:        " + (marca.getCodMarca() == -1 ? "Não preenchido ainda" : marca.getCodMarca()));
        System.out.println("Nome:          " + (marca.getNomeMarca() == null ? "Não preenchido ainda" : marca.getNomeMarca()));
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}