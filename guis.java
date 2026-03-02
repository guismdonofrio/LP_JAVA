	1- 
public class Main {
    public static void main(String[] args) {
        System.out.println("Estou aprendendo Java!");
    }
}

	2-
public class Main {
    public static void main(String[] args) {
        System.out.println("Primeira linha");
        System.out.println("Segunda linha");
    }
}

	3-
public class Main {
    public static void main(String[] args) {
        System.out.println("Primeira linha\nSegunda linha");
    }
}

	4- 
public class MyFirstJavaProgram {

   /* This is my first java program.
    * This will print 'Hello, World!' as the output
    */

   public static void main(String []args) {
       
      double janeiro = 15000;
      double fevereiro = 23000;
      double marco = 17000;
      
      double total = janeiro + fevereiro + marco;
      double media = total/3;
      
      System.out.println("Total por trimentre: " + total + "\nMedia mensal: " + media );
      //System.out.println();
      
   }
}


	CARRO:
CLASSE: CARRO
- Atributos:
marca
modelo
cor
ano
placa

- Métodos:
ligar
desligar

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


public class Main {
    public static void main(String[] args) {
        
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


	LOJA DE ROUPA:
Classe: Roupa
- Atributos:
tipo
tamanho
cor
preco

- Métodos:
vestir
vender

public class Roupa {
    
    String tipo;
    String tamanho;
    String cor;
    double preco;
    
    void vestir() {
        System.out.println("A roupa está sendo vestida.");
    }
    
    void vender() {
        System.out.println("A roupa foi vendida.");
    }
}


	FARMACIA:
Classe: Medicamento
- Atributos:
nome
fabricante
preco
validade

- Métodos:
vender
aplicar

public class Medicamento {
    
    String nome;
    String fabricante;
    double preco;
    String validade;
    
    void vender() {
        System.out.println("Medicamento vendido.");
    }
    
    void aplicar() {
        System.out.println("Medicamento aplicado.");
    }
}


