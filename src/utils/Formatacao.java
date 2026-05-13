package utils;

public class Formatacao{
    final static String[] UFS = {
        "AC","AL","AP","AM","BA","CE","DF","ES","GO",
        "MA","MT","MS","MG","PA","PB","PR","PE","PI",
        "RJ","RN","RS","RO","RR","SC","SP","SE","TO"
    };

    final static String[] TAMS = {
        // Letras (roupas)
        "XS", "PP", "P", "M", "G", "GG", "XG", "XGG", "XXG", "XXXG",
        "XL", "XXL", "XXXL",

        // Calças (BR masculino/feminino comum)
        "36", "38", "40", "42", "44", "46", "48", "50", "52", "54", "56",

        // Calçados (BR)
        "33", "34", "35", "36", "37", "38", "39",
        "40", "41", "42", "43", "44", "45", "46", "47", "48"
    };

    public static String limparCpf(String cpf) {
        if (cpf == null || cpf == "") return "";
        return cpf.replaceAll("\\D", "");
    }

    public static String formatarCpf(String cpf) {
        if (cpf == null || cpf == "") return "";

        cpf = limparCpf(cpf);

        if (cpf.length() != 11) throw new IllegalArgumentException("Tamanho de CPF inválido");

        return cpf.substring(0, 3) + "." +
            cpf.substring(3, 6) + "." +
            cpf.substring(6, 9) + "-" +
            cpf.substring(9, 11);
    }

    public static String limparNum(String num) {
        if (num == null || num == "") return "";
        return num.replaceAll("\\D", "");
    }

    private static String limparCep(String cep) {
        if (cep == null || cep == "") return "";
        return cep.replaceAll("\\D", "");
    }

    public static String formatarCep(String cep) {
        if (cep == null || cep == "") return "";

        cep = limparCep(cep);

        if (cep.length() != 8) throw new IllegalArgumentException("Tamanho de CEP inválido");

        return cep.substring(0, 5) + "-" +
            cep.substring(5, 8);
    }

    public static String formatarUF(String uf) {
        if (uf == null || uf == "") return "";

        uf = uf.trim().toUpperCase();

        for (String u : UFS) {
            if (u.equals(uf)) {
                return uf;
            }
        }

        throw new IllegalArgumentException("UF inválida: " + uf);
    }

    public static String formatarTamanho(String tam) {
        if (tam == null || tam == "") return "";

        tam = tam.trim().toUpperCase();

        for (String t : TAMS) {
            if (t.equals(tam)) {
                return tam;
            }
        }

        throw new IllegalArgumentException("Tamanho inválido: " + tam);
    }

    public static String alinharDireita(String texto, int larguraTotal) {
        int espacos = larguraTotal - texto.length();

        if (espacos < 0) espacos = 0;

        return " ".repeat(espacos) + texto;
    }
    public static String alinharDireita(String texto, int larguraTotal, String c) {
        int espacos = larguraTotal - texto.length();

        if (espacos < 0) espacos = 0;

        return c.repeat(espacos) + texto;
    }

    public static void patternError(Exception e){
        System.out.println("\nX>>==-- Erro: " + e.getMessage() + " --==<<X\n");
    }
    public static void patternError(IllegalArgumentException e){
        System.out.println("\nX>>==-- Erro: " + e.getMessage() + " --==<<X\n");
    }
    public static void patternError(CancelOperationException e){
        System.out.println("\nX>>==-- < CANCELANDO " + e.getMessage() + " > --==<<X\n");
    }
    public static void patternError(char e){
        System.out.println("\nX>>==-- Entrada: " + e + " inválida --==<<X\n");
    }
    public static void patternError(ReviewOperationException e){
        System.out.println("\nX>>==-- < REVISANDO " + e.getMessage() + " > --==<<X\n");
    }
    public static void patternError(){
        System.out.println("\nX>>==-- Valor inválido --==<<X\n");
    }
    
    public static void mostrarCartazTamanhos() {

        int largura = 60;

        // Separação manual
        String[] roupas = {
            "XS","PP","P","M","G","GG","XG","XGG","XXG","XXXG","XL","XXL","XXXL"
        };

        String[] calcas = {
            "36","38","40","42","44","46","48","50","52","54","56"
        };

        String[] calcados = {
            "33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48"
        };

        System.out.println("=".repeat(largura));
        System.out.println(centralizar("TAMANHOS DISPONÍVEIS", largura));
        System.out.println("=".repeat(largura));

        mostrarSecao("ROUPAS", roupas, largura);
        mostrarSecao("CALÇAS", calcas, largura);
        mostrarSecao("CALÇADOS", calcados, largura);

        System.out.println("=".repeat(largura));
    }

    public static void mostrarSecao(String titulo, String[] dados, int largura) {

        System.out.println();
        System.out.println(centralizar("[" + titulo + "]", largura));
        System.out.println("-".repeat(largura));

        String linha = "";

        for (int i = 0; i < dados.length; i++) {
            linha += String.format("%-5s", dados[i]);

            if ((i + 1) % 10 == 0) {
                System.out.println(centralizar(linha, largura));
                linha = "";
            }
        }

        if (!linha.isEmpty()) {
            System.out.println(centralizar(linha, largura));
        }
    }

    public static String centralizar(String texto, int largura) {
        int espacos = (largura - texto.length()) / 2;

        if (espacos < 0) espacos = 0;

        return " ".repeat(espacos) + texto;
    }
}