// Classe: Barraca
// - Atributos
// nome
// produtoPrincipal
// quantidadeProdutos
//
// - Métodos
// venderProduto()
// reporEstoque()
// mostrarEstoque()

public class Atv6_Barraca {

    String nome;
    String produtoPrincipal;
    int quantidadeProdutos;

    public Atv6_Barraca(String nome, String produtoPrincipal, int quantidadeProdutos) {
        this.nome = nome;
        this.produtoPrincipal = produtoPrincipal;
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public void venderProduto(int quantidade) {
        quantidadeProdutos -= quantidade;
        System.out.println("Produto vendido.");
    }

    public void reporEstoque(int quantidade) {
        quantidadeProdutos += quantidade;
        System.out.println("Estoque reposto.");
    }

    public void mostrarEstoque() {
        System.out.println("Quantidade em estoque: " + quantidadeProdutos);
    }
}

//Main
/*
public class Main {
    public static void main(String[] args) {

        Atv6_Barraca barraca = new Atv6_Barraca("Barraca do João", "Pastel", 30);

        barraca.mostrarEstoque();

        barraca.venderProduto(15);
        barraca.mostrarEstoque();

        barraca.reporEstoque(25);
        barraca.mostrarEstoque();
    }
}
 */
