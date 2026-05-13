package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class EnderecoView implements BaseView<Endereco>{
    private List<Endereco> elementList = new ArrayList<>();

    public void insertList(List<Endereco> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Endereco> getElementList(){
        return this.elementList;
    }
    
    public Endereco requestByCod(int codEnd){
        for (Endereco e : elementList) {
            if (e.getCodEnd() == codEnd) {
                return e;
            }
        }
        throw new IllegalArgumentException("Endereço não encontrado");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (Endereco e : elementList) {
             if (e.getCodEnd() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }

    public Endereco searchEnd(Scanner entrada){
        Endereco end;
        while (true) {
            System.out.println("1 - Realizar uma consulta");
            System.out.println("2 - Cadastrar novo endereço");
            System.out.println("3 - Indicar o endereço para essa pessoa");
            System.out.print("Opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine();
            int codEnd;
            switch(opcao){
                case 1:
                    read(entrada);
                    break;
                case 2:
                    codEnd = createAndSaveCod(entrada);
                    end = requestByCod(codEnd);
                    if(end != null) return end;
                    break;
                case 3:
                    System.out.print("Digite o código do endereço: ");
                    codEnd = entrada.nextInt();
                    entrada.nextLine();
                    end = requestByCod(codEnd);
                    if(end != null) return end;
                    break;
            }
        }
    }

    public Endereco searchEnd(Scanner entrada, Endereco end){
        while (true) {
            System.out.println("1 - Realizar uma consulta");
            System.out.println("2 - Cadastrar novo endereço");
            System.out.println("3 - Indicar o endereço para essa pessoa");
            System.out.println("4 - Manter endereço atual");
            System.out.print("Opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine();
            int codEnd;
            switch(opcao){
                case 1:
                    read(entrada);
                    break;
                case 2:
                    codEnd = createAndSaveCod(entrada);
                    end = requestByCod(codEnd);
                    if(end != null) return end;
                    break;
                case 3:
                    System.out.print("Digite o código do endereço: ");
                    codEnd = entrada.nextInt();
                    entrada.nextLine();
                    end = requestByCod(codEnd);
                    if(end != null) return end;
                    break;
                case 4: 
                    if(end != null) return end;
                    break;
            }
        }
    }
    
     // Controle de endereço ==================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("================= ENDEREÇOS ==================");
                System.out.println("                                              ");
                System.out.println("1 -             Criar endereço             - 1");
                System.out.println("2 -             Editar endereço            - 2");
                System.out.println("3 -            Excluir endereço            - 3");
                System.out.println("4 -         Listar todos endereços         - 4");
                System.out.println("5 -           Procurar endereços           - 5");
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
        createAndSaveCod(entrada);
    }

    public int createAndSaveCod(Scanner entrada){
        try{
            System.out.println("CRIAR NOVO ENDEREÇO: ");

            String ufEnd = Dados.requestValue(
                "Digite o UF: ", 
                "UF: ", 
                "CRIAÇÃO",
                false,
                entrada
            );

            String cepEnd = Dados.requestValue(
                "Digite o CEP: ", 
                "CEP: ", 
                "CRIAÇÃO",
                false,
                entrada
            );

            String cidadeEnd = Dados.requestValue(
                "Digite a cidade: ", 
                "CIDADE: ", 
                "CRIAÇÃO",
                false,
                entrada
            );

            String bairroEnd = Dados.requestValue(
                "Digite o bairro: ", 
                "BAIRRO: ", 
                "CRIAÇÃO",
                false,
                entrada
            );

            String logradouroEnd = Dados.requestValue(
                "Digite o logradouro: ", 
                "LOGRADOURO: ", 
                "CRIAÇÃO",
                false,
                entrada
            );

            String numeroEnd = Dados.requestValue(
                "Digite o numero: ", 
                "NUMERO: ", 
                "CRIAÇÃO",
                false,
                entrada
            );

            String complementoEnd = Dados.requestValue(
                "Digite o complemento: ", 
                "COMPLEMENTO: ", 
                "CRIAÇÃO",
                true,
                entrada
            );

            System.out.println("");

            int newCodEnd = elementList.size() + 1;

            elementList.add(new Endereco(newCodEnd, ufEnd, cepEnd, cidadeEnd, bairroEnd, logradouroEnd, numeroEnd, complementoEnd));

            System.out.println("Endereço registrado!");
            System.out.println("==============================================\n");
            return newCodEnd;
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
            return -1;
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR ENDEREÇO: ");

            Endereco end = requestByCod(
                Dados.requestCod(
                    "Digite o Codigo do endereço que deseja alterar: ", 
                    "EDIÇÃO",
                    false, 
                    BancoDeDados.enderecoV,
                    entrada
                )
            );

            String ufEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getUf()+")\nDigite o novo uf:", 
                "UF: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            ufEnd = ufEnd.isEmpty() ? end.getUf() : ufEnd;

            String cepEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getCep()+")\nDigite o novo cep:", 
                "CEP: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            cepEnd = cepEnd.isEmpty() ? end.getCep() : cepEnd;
            
            String cidadeEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getCidade()+")\nDigite a nova cidade:", 
                "CIDADE: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            cidadeEnd = cidadeEnd.isEmpty() ? end.getCidade() : cidadeEnd;

            String bairroEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getBairro()+")\nDigite o novo bairro:", 
                "BAIRRO: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            bairroEnd = bairroEnd.isEmpty() ? end.getBairro() : bairroEnd;

            String logradouroEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getBairro()+")\nDigite o novo logradouro:", 
                "LOGRADOURO: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            logradouroEnd = logradouroEnd.isEmpty() ? end.getLogradouro() : logradouroEnd;

            String numeroEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getNumero()+")\nDigite o novo numero:", 
                "NUMERO: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            numeroEnd = numeroEnd.isEmpty() ? end.getNumero() : numeroEnd;

            String complementoEnd = Dados.requestValue(
                "Deixe em branco para manter ("+end.getComplemento()+")\nDigite o novo complemento:", 
                "COMPLEMENTO: ", 
                "EDIÇÃO",
                true,
                entrada
            );
            complementoEnd = complementoEnd.isEmpty() ? end.getComplemento() : complementoEnd;
            System.out.println("");

            Endereco newEnd = new Endereco(end.getCodEnd(), ufEnd, cepEnd, cidadeEnd, bairroEnd, logradouroEnd, numeroEnd, complementoEnd);

            System.out.println("----------------------------------------------");
            System.out.println("ENDEREÇO ANTIGO: ");
            end.showProp();
            System.out.println("ENDEREÇO ATUALIZADO): ");
            newEnd.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição do endereço? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    end.copyFrom(newEnd);
                    System.out.println("\nEndereço atualizado!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de endereço descartada!");
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
            System.out.println("REMOVER ENDEREÇO: ");
            
            Endereco end = requestByCod(
                Dados.requestCod(
                    "Digite o Codigo do endereço que deseja apagar: ", 
                    "REMOÇÃO",
                    false,
                    BancoDeDados.enderecoV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            end.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover o endereço? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(end);
                    System.out.println("\nRemoção do endereço concluida!");
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
        System.out.println("ENDEREÇOS REGISTRADOS: ");
        System.out.println("----------------------------------------------");
        for (Endereco end : elementList) {
            end.showProp();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR ENDEREÇO: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoEnd = entrada.nextLine();

        List<Endereco> termosEndEncontrados = new ArrayList<>();
        for (Endereco end : elementList) {
            if (end.searchByTerm(termoEnd)) {
                termosEndEncontrados.add(end);
            }
        }

        System.out.println("RESULTADOS [ENDEREÇO]: ");

        if(termosEndEncontrados.isEmpty()){
            System.out.println("Nenhum endereço com esse termo encontrado\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Endereco end : termosEndEncontrados) {
            end.showProp();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}
