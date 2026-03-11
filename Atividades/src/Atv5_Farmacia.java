// Classe: Farmacia
// - Atributos
// nome : String
// endereco : String
// quantidadeMedicamentos : int
//
// - Métodos
// comprarMedicamento()
// venderMedicamento()
// mostrarEstoque()

public class Atv5_Farmacia {

    String nome;
    String endereco;
    int quantidadeMedicamentos;

    public Atv5_Farmacia(String nome, String endereco, int quantidadeMedicamentos) {
        this.nome = nome;
        this.endereco = endereco;
        this.quantidadeMedicamentos = quantidadeMedicamentos;
    }

    public void comprarMedicamento(int quantidade) {
        quantidadeMedicamentos += quantidade;
        System.out.println("Compra realizada.");
    }

    public void venderMedicamento(int quantidade) {
        quantidadeMedicamentos -= quantidade;
        System.out.println("Venda realizada.");
    }

    public void mostrarEstoque() {
        System.out.println("Estoque atual: " + quantidadeMedicamentos);
    }
}
