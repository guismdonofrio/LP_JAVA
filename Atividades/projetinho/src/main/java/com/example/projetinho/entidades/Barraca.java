package com.example.projetinho.entidades;

// Apenas guarda dados e contém as regras de negócio relacionadas à barraca.
// Ela NÃO sabe nada sobre banco de dados — isso é responsabilidade do BarracaDAO.
public class Barraca {

    private int    id;       // Identificador único no banco (0 = ainda não salva)
    private String nome;     // Nome da barraca
    private String produto;  // Produto principal vendido
    private int    estoque;  // Quantidade em estoque

    // Construtor usado ao criar uma nova barraca ANTES de salvar no banco.
    // O id começa em 0 e será preenchido pelo DAO após o INSERT.
    public Barraca(String nome, String produto, int estoque) {
        this(0, nome, produto, estoque);
    }

    // Construtor completo — usado ao recuperar uma barraca do banco (já tem id).

    public Barraca(int id, String nome, String produto, int estoque) {
        this.id      = id;
        this.nome    = nome;
        this.produto = produto;
        this.estoque = estoque;
    }

    // Regras de negócio
    // Realiza a venda de uma quantidade de produtos. Só permite vender se houver estoque suficiente.
    // @return mensagem de resultado para exibir na tela
    public String vender(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        if (estoque >= qtd) {
            estoque -= qtd; // desconta do estoque
            return qtd + " produto(s) vendido(s). Estoque atual: " + estoque;
        }
        return "Estoque insuficiente. Disponível: " + estoque;
    }

    // Repõe (adiciona) uma quantidade de produtos ao estoque.
    // @return mensagem de resultado para exibir na tela
    public String repor(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        estoque += qtd; // adiciona ao estoque
        return qtd + " produto(s) adicionado(s). Estoque atual: " + estoque;
    }

    // Retorna uma string formatada com as informações da barraca.
    public String mostrarEstoque() {
        return "[#" + id + "] " + nome + " | Produto: " + produto + " | Estoque: " + estoque;
    }

    //  Getters e Setters
    // Necessários para o JavaFX acessar os campos nas colunas da TableView
    // via PropertyValueFactory (usa reflexão para chamar getX())

    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getNome()            { return nome; }
    public void   setNome(String n)    { this.nome = n; }

    public String getProduto()         { return produto; }
    public void   setProduto(String p) { this.produto = p; }

    public int  getEstoque()       { return estoque; }
    public void setEstoque(int e)  { this.estoque = e; }
}