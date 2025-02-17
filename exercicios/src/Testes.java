import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Testes {
    public static void main(String[] args) {

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

    }
}