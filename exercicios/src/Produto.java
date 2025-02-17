public class Produto {
    private String nome;
    private double preco;
    private String categoria;

    public Produto(String nome, double preco, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public static int compararPorPreco(Produto p1, Produto p2) {
        return Double.compare(p1.getPreco(), p2.getPreco());
    }

    public boolean isEletronicoAteMil() {
        return categoria.equals("Eletrônicos") && preco < 1000;
    }

    @Override
    public String toString() {
        return nome + " - " + preco;
    }
}