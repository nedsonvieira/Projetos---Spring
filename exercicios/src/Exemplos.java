import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Exemplos {

    public static void main(String[] args) {
        //Ex1
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        numeros.stream()
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);//.forEach(p -> System.out.println(p));

/****************************************************************************************************************/

        //Ex2
        List<String> palavras = Arrays.asList("java", "stream", "lambda");

        for (int i = 0; i < palavras.size(); i++) {
            if (palavras.get(i).equalsIgnoreCase("java"))
                System.out.println(palavras.get(i));
        }


        palavras.stream()
                        .filter(p -> p.equalsIgnoreCase("java"))
                                .forEach(System.out::println);

        Predicate<String> isJava = "java"::equalsIgnoreCase;

        palavras.stream()
                .filter(isJava)
                .forEach(System.out::println);

        palavras.stream()
                .map(String::toUpperCase)//.map(p -> p.toUpperCase())
                .forEach(System.out::println);//.forEach(p -> System.out.println(p));

/****************************************************************************************************************/

        //Ex3
        List<Integer> numeros1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> resultado = numeros1.stream()
                .filter(n -> n % 2 == 1)
                .map(n -> n * 2)
                .collect(Collectors.toList());
        System.out.println(resultado);

/****************************************************************************************************************/

        //Ex4
        List<String> palavras1 = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        List<String> unicas = palavras1.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(unicas);

/****************************************************************************************************************/

        //Ex5
        List<List<Integer>> listaDeNumeros = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(5, 6, 7, 8),
                Arrays.asList(9, 10, 11, 12)
        );
        List<Integer> novaLista = listaDeNumeros.stream()
                .flatMap(ln -> ln.stream())//.flatMap(List::stream) method reference
                .filter(num -> Exemplos.ehPrimo(num))//.filter(Exemplos::ehPrimo)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(novaLista);

/****************************************************************************************************************/

        //Ex6
        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("Alice", 22),
                new Pessoa("Bob", 17),
                new Pessoa("Charlie", 19)
        );

        pessoas.stream()
                .filter(p -> p.getIdade() > 18)
                .map(p -> p.getNome())//.map(Pessoa::getNome)
                .sorted()
                .forEach(System.out::println);

/****************************************************************************************************************/

        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Teclado", 200.0, "Eletrônicos"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Monitor", 900.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis")
        );

//        List<Produto> novoProdutos = produtos.stream()
//                .filter(p -> p.getCategoria().equals("Eletrônicos") && p.getPreco()<1000)
//                .sorted((p1, p2) -> Double.compare(p1.getPreco(), p2.getPreco()))
//                .collect(Collectors.toList());
//
//        System.out.println(novoProdutos);

        List<Produto> novoProdutos1 = produtos.stream()
                .filter(Produto::isEletronicoAteMil) // Method reference no filtro
                .sorted(Produto::compararPorPreco)  // Method reference na ordenação
                .limit(2)
                .collect(Collectors.toList());

        novoProdutos1.forEach(System.out::println);

  }
    private static boolean ehPrimo(int numero) {
        if (numero < 2) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
}