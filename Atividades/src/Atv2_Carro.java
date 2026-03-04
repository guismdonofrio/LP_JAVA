//CLASSE: CARRO
//- Atributos:
//marca
//modelo
//cor
//ano
//placa
//
//- Métodos:
//ligar
//desligar

public class Carro {
    
    // ATRIBUTOS
    String marca;
    String modelo;
    String cor;
    int ano;
    String placa;
    
    // MÉTODO LIGAR
    void ligar() {
        System.out.println("O carro está ligado.");
    }
    
    // MÉTODO DESLIGAR
    void desligar() {
        System.out.println("O carro está desligado.");
    }
}


public class Atv2_Carro {
    public static void atv3carro(String[] args) {
        
        Carro meuCarro = new Carro();
        
        meuCarro.marca = "Toyota";
        meuCarro.modelo = "Corolla";
        meuCarro.cor = "Preto";
        meuCarro.ano = 2022;
        meuCarro.placa = "ABC-1234";
        
        System.out.println("Marca: " + meuCarro.marca);
        System.out.println("Modelo: " + meuCarro.modelo);
        
        meuCarro.ligar();
        meuCarro.desligar();
    }
}