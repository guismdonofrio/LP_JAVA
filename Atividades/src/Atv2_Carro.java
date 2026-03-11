// Classe: Carro
// - Atributos:
// marca
// modelo
// cor
// ano
// placa
//
// - Métodos:
// ligar
// desligar

class Carro {
    
    String marca;
    String modelo;
    String cor;
    int ano;
    String placa;

    public Carro(String marca, String modelo, String cor, int ano, String placa){
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.placa = placa;
    }
    
    void ligar() {
        System.out.println("O carro está ligado.");
    }
    
    void desligar() {
        System.out.println("O carro está desligado.");
    }
}


public class Atv2_Carro {
    public static void main(String[] args) {
        
        Carro meuCarro = new Carro("Toyota", "Corolla", "Preto", 2022, "ABC-1234");
        
        System.out.println("Marca: " + meuCarro.marca);
        System.out.println("Modelo: " + meuCarro.modelo);
        System.out.println("Cor: " + meuCarro.cor);
        System.out.println("Ano: " + meuCarro.ano);
        System.out.println("Placa: " + meuCarro.placa);
        
        meuCarro.ligar();
        meuCarro.desligar();
    }
}