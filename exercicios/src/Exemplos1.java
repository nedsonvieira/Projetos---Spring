import java.util.*;
import java.util.stream.Collectors;

public class Exemplos1 {

    public static void main(String[] args) {
        //Ex1 Encontrando o maior valor
        List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);

        Optional<Integer> max = numeros.stream()
                .max(Integer::compare);    //.max((n1, n2) -> n1.compareTo(n2);
        max.ifPresent(System.out::println);  //max.ifPresent(n -> System.out.println(n));

        IntSummaryStatistics iss = numeros.stream()
                .mapToInt(Integer::intValue)   //.mapToInt(v -> v.intValue())
                .summaryStatistics();
        System.out.println("Maior número: " + iss.getMax());


        //Ex2 Agrupando strings pelo tamanho
        List<String> palavras = Arrays.asList("java", "stream", "lambda", "code");
        Map<Integer, List<String>> agruparPorTamanho = palavras.stream()
                .collect(Collectors.groupingBy(String::length));   //.collect(Collectors.groupingBy(palavra -> palavra.length());
        System.out.println(agruparPorTamanho);


        //Ex3 Concatenando elementos da lista
        List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");
        String concatenarTodos = nomes.stream()
                .collect(Collectors.joining(", "));
        System.out.println(concatenarTodos);

        //Ex4 Reduzindo uma lista de inteiros
        List<Integer> numeros1 = Arrays.asList(1, 2, 3, 4, 5, 6);

        int somaDosQuadradosPares = numeros1.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)    //.mapToInt(n -> n * n).sum()
                .reduce(0, Integer::sum);  //.reduce(0, (a, b) -> Integer.sum(a, b));
        System.out.println(somaDosQuadradosPares);

        //Ex5 Particionando números ímpares e pares
        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> particionado = numeros2.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Pares: " + particionado.get(true));
        System.out.println("Ímpares: " + particionado.get(false));


        //Ex6 Agrupando produtos por categoria

        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Fone de Ouvido", 100.0, "Eletrônicos"),
                new Produto("Caneta", 5.0, "Papelaria")
        );

        Map<String, List<Produto>> produtosPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria)); //.collect(Collectors.groupingBy(p -> p.getCategoria());

        System.out.println(produtosPorCategoria);

        //Ex7 Quantidade de produtos por categoria
        Map<String, Long> contagemPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria, Collectors.counting()));

        System.out.println(contagemPorCategoria);

        //Ex8 Obtendo o produto mais caro de cada categoria
        Map<String, Optional<Produto>> maisCaroPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria,
                        Collectors.maxBy(Comparator.comparingDouble(Produto::getPreco))));

        System.out.println(maisCaroPorCategoria);

        //Ex9 Soma dos valores por categoria
        Map<String, Double> somaPrecoPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria,
                        Collectors.summingDouble(Produto::getPreco)));

        System.out.println(somaPrecoPorCategoria);


    }
}