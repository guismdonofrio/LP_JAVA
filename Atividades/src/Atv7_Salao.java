// Classe: Salao
// - Atributos
// nome : String
// quantidadeFuncionarios : int
// servicoPrincipal : String
//
// - Métodos
// prestarServico()
// contratarFuncionario()
// mostrarInformacoes()

public class Atv7_Salao {

    String nome;
    int quantidadeFuncionarios;
    String servicoPrincipal;

    public Atv7_Salao(String nome, int quantidadeFuncionarios, String servicoPrincipal) {
        this.nome = nome;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
        this.servicoPrincipal = servicoPrincipal;
    }

    public void prestarServico() {
        System.out.println("Serviço sendo realizado.");
    }

    public void contratarFuncionario() {
        quantidadeFuncionarios++;
        System.out.println("Funcionário contratado.");
    }

    public void mostrarInformacoes() {
        System.out.println("Salão: " + nome);
        System.out.println("Funcionários: " + quantidadeFuncionarios);
        System.out.println("Serviço principal: " + servicoPrincipal);
    }
}