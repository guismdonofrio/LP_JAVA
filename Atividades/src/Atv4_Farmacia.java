//Classe: Medicamento
//- Atributos:
//nome
//fabricante
//preco
//validade
//
//- Métodos:
//vender
//aplicar

public class Atv4_Farmacia {
    
    String nome;
    String fabricante;
    double preco;
    String validade;
    
    public Atv4_Farmacia(String nome, String fabricante, double preco, String validade) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
        this.validade = validade;
    }
    
    void vender() {
        System.out.println("Medicamento vendido.");
    }
    
    void aplicar() {
        System.out.println("Medicamento aplicado.");
    }
}
//Main
/*
public class Main {

    public static void main(String[] args) {

        Atv4_Farmacia medicamento = new Atv4_Farmacia(
            "Paracetamol",
            "EMS",
            12.50,
            "12/2026"
        );

        medicamento.vender();
        medicamento.aplicar();
    }

}
 */
