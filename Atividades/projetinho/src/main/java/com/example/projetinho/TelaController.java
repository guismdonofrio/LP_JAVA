package com.example.projetinho;

import com.example.projetinho.dao.BarracaDAO;
import com.example.projetinho.dao.FarmaciaDAO;
import com.example.projetinho.dao.SalaoDAO;
import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Barraca;
import com.example.projetinho.entidades.Farmacia;
import com.example.projetinho.entidades.Salao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaController implements Initializable {

    // BARRACA
    @FXML private TextField txtNomeBarraca;
    @FXML private TextField txtProdutoBarraca;
    @FXML private TextField txtQuantidadeBarraca;
    @FXML private Label     lblBarraca;
    @FXML private TableView<Barraca>            tabelaBarraca;
    @FXML private TableColumn<Barraca, Integer> colBarracaId;
    @FXML private TableColumn<Barraca, String>  colBarracaNome;
    @FXML private TableColumn<Barraca, String>  colBarracaProduto;
    @FXML private TableColumn<Barraca, Integer> colBarracaEstoque;

    //barraca atualmente selecionada
    private Barraca barracaSelecionada;

    //operações no banco de dados
    private final BarracaDAO barracaDAO = new BarracaDAO();

    // Carrega  os registros vindos do banco de dados.
    private void carregarTabelaBarraca() {
        tabelaBarraca.setItems(FXCollections.observableArrayList(barracaDAO.listarTodas()));
    }

    // Edição TableView.
    private void configurarColunaEditavelBarraca() {

        colBarracaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colBarracaNome.setOnEditCommit(e -> {
            e.getRowValue().setNome(e.getNewValue());
            salvarEDesativarEdicaoBarraca(e.getRowValue());
        });

        colBarracaProduto.setCellFactory(TextFieldTableCell.forTableColumn());
        colBarracaProduto.setOnEditCommit(e -> {
            e.getRowValue().setProduto(e.getNewValue());
            salvarEDesativarEdicaoBarraca(e.getRowValue());
        });

        colBarracaEstoque.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colBarracaEstoque.setOnEditCommit(e -> {
            e.getRowValue().setEstoque(e.getNewValue());
            salvarEDesativarEdicaoBarraca(e.getRowValue());
        });

        // Começa não editável
        tabelaBarraca.setEditable(false);
    }

    // Atualiza os dados e desabilita a edição
    private void salvarEDesativarEdicaoBarraca(Barraca b) {
        barracaDAO.atualizar(b);
        tabelaBarraca.setEditable(false);
        lblBarraca.setText("Barraca #" + b.getId() + " salva.");
        carregarTabelaBarraca();
    }
    // Configuramento da linha seleciona
    private void configurarSelecaoBarraca() {
        tabelaBarraca.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                barracaSelecionada = selecionado;
                txtNomeBarraca.setText(selecionado.getNome());
                txtProdutoBarraca.setText(selecionado.getProduto());
                txtQuantidadeBarraca.setText(String.valueOf(selecionado.getEstoque()));
                lblBarraca.setText("Selecionado: barraca #" + selecionado.getId() + ".");
            }
        });
    }

    // Limpa campos para novas informações
    @FXML public void novaBarraca() {
        barracaSelecionada = null;
        tabelaBarraca.setEditable(false);
        txtNomeBarraca.clear(); txtProdutoBarraca.clear(); txtQuantidadeBarraca.clear();
        tabelaBarraca.getSelectionModel().clearSelection();
        lblBarraca.setText("Preencha os campos e clique Inserir para salvar.");
    }

    // Nova barraca no banco
    @FXML public void inserirBarraca() {
        String nome    = txtNomeBarraca.getText().trim();
        String produto = txtProdutoBarraca.getText().trim();
        if (nome.isEmpty() || produto.isEmpty()) {
            lblBarraca.setText("Preencha Nome e Produto antes de inserir.");
            return;
        }
        int qtd = lerInt(txtQuantidadeBarraca, lblBarraca);
        if (qtd < 0) return;
        Barraca nova = barracaDAO.inserir(new Barraca(nome, produto, qtd));
        lblBarraca.setText("Barraca inserida com ID #" + nova.getId() + ".");
        carregarTabelaBarraca();
    }

    @FXML public void editarBarraca() {
        if (barracaSelecionada == null) {
            lblBarraca.setText("Selecione uma linha na tabela primeiro.");
            return;
        }
        tabelaBarraca.setEditable(true);
        // Encontra e seleciona o índice correto após possível reload
        int idx = tabelaBarraca.getItems().indexOf(barracaSelecionada);
        if (idx < 0) {
            // busca por ID caso a referência tenha mudado
            for (int i = 0; i < tabelaBarraca.getItems().size(); i++) {
                if (tabelaBarraca.getItems().get(i).getId() == barracaSelecionada.getId()) { idx = i; break; }
            }
        }
        tabelaBarraca.getSelectionModel().select(idx);
        tabelaBarraca.edit(idx, colBarracaNome); // abre edição direto na coluna Nome
        lblBarraca.setText("Edite a célula desejada e pressione ENTER para confirmar.");
    }

    @FXML public void deletarBarraca() {
        if (barracaSelecionada == null) { lblBarraca.setText("Selecione uma linha na tabela."); return; }
        if (barracaDAO.deletar(barracaSelecionada.getId())) {
            lblBarraca.setText("Barraca #" + barracaSelecionada.getId() + " removida.");
            barracaSelecionada = null;
            txtNomeBarraca.clear(); txtProdutoBarraca.clear(); txtQuantidadeBarraca.clear();
        } else {
            lblBarraca.setText("Erro ao deletar barraca.");
        }
        tabelaBarraca.setEditable(false);
        carregarTabelaBarraca();
    }

    // FARMÁCIA
    @FXML private TextField txtNomeFarmacia;
    @FXML private TextField txtEnderecoFarmacia;
    @FXML private TextField txtQuantidadeFarmacia;
    @FXML private Label     lblFarmacia;
    @FXML private TableView<Farmacia>            tabelaFarmacia;
    @FXML private TableColumn<Farmacia, Integer> colFarmaciaId;
    @FXML private TableColumn<Farmacia, String>  colFarmaciaNome;
    @FXML private TableColumn<Farmacia, String>  colFarmaciaEndereco;
    @FXML private TableColumn<Farmacia, Integer> colFarmaciaEstoque;

    private Farmacia farmaciaSelecionada;
    private final FarmaciaDAO farmaciaDAO = new FarmaciaDAO();

    private void carregarTabelaFarmacia() {
        tabelaFarmacia.setItems(FXCollections.observableArrayList(farmaciaDAO.listarTodas()));
    }

    private void configurarColunaEditavelFarmacia() {
        colFarmaciaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colFarmaciaNome.setOnEditCommit(e -> {
            e.getRowValue().setNome(e.getNewValue());
            salvarEDesativarEdicaoFarmacia(e.getRowValue());
        });

        colFarmaciaEndereco.setCellFactory(TextFieldTableCell.forTableColumn());
        colFarmaciaEndereco.setOnEditCommit(e -> {
            e.getRowValue().setEndereco(e.getNewValue());
            salvarEDesativarEdicaoFarmacia(e.getRowValue());
        });

        colFarmaciaEstoque.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colFarmaciaEstoque.setOnEditCommit(e -> {
            e.getRowValue().setEstoque(e.getNewValue());
            salvarEDesativarEdicaoFarmacia(e.getRowValue());
        });

        tabelaFarmacia.setEditable(false);
    }

    private void salvarEDesativarEdicaoFarmacia(Farmacia f) {
        farmaciaDAO.atualizar(f);
        tabelaFarmacia.setEditable(false);
        lblFarmacia.setText("Farmácia #" + f.getId() + " salva.");
        carregarTabelaFarmacia();
    }

    private void configurarSelecaoFarmacia() {
        tabelaFarmacia.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                farmaciaSelecionada = selecionado;
                txtNomeFarmacia.setText(selecionado.getNome());
                txtEnderecoFarmacia.setText(selecionado.getEndereco());
                txtQuantidadeFarmacia.setText(String.valueOf(selecionado.getEstoque()));
                lblFarmacia.setText("Selecionado: farmácia #" + selecionado.getId() + ".");
            }
        });
    }

    @FXML public void novaFarmacia() {
        farmaciaSelecionada = null;
        tabelaFarmacia.setEditable(false);
        txtNomeFarmacia.clear(); txtEnderecoFarmacia.clear(); txtQuantidadeFarmacia.clear();
        tabelaFarmacia.getSelectionModel().clearSelection();
        lblFarmacia.setText("Preencha os campos e clique Inserir para salvar.");
    }

    @FXML public void inserirFarmacia() {
        String nome     = txtNomeFarmacia.getText().trim();
        String endereco = txtEnderecoFarmacia.getText().trim();
        if (nome.isEmpty() || endereco.isEmpty()) {
            lblFarmacia.setText("Preencha Nome e Endereço antes de inserir.");
            return;
        }
        int qtd = lerInt(txtQuantidadeFarmacia, lblFarmacia);
        if (qtd < 0) return;
        Farmacia nova = farmaciaDAO.inserir(new Farmacia(nome, endereco, qtd));
        lblFarmacia.setText("Farmácia inserida com ID #" + nova.getId() + ".");
        carregarTabelaFarmacia();
    }

    @FXML public void editarFarmacia() {
        if (farmaciaSelecionada == null) {
            lblFarmacia.setText("Selecione uma linha na tabela primeiro.");
            return;
        }
        tabelaFarmacia.setEditable(true);
        int idx = tabelaFarmacia.getItems().indexOf(farmaciaSelecionada);
        if (idx < 0) {
            for (int i = 0; i < tabelaFarmacia.getItems().size(); i++) {
                if (tabelaFarmacia.getItems().get(i).getId() == farmaciaSelecionada.getId()) { idx = i; break; }
            }
        }
        tabelaFarmacia.getSelectionModel().select(idx);
        tabelaFarmacia.edit(idx, colFarmaciaNome);
        lblFarmacia.setText("Edite a célula desejada e pressione ENTER para confirmar.");
    }

    @FXML public void deletarFarmacia() {
        if (farmaciaSelecionada == null) { lblFarmacia.setText("Selecione uma linha na tabela."); return; }
        if (farmaciaDAO.deletar(farmaciaSelecionada.getId())) {
            lblFarmacia.setText("Farmácia #" + farmaciaSelecionada.getId() + " removida.");
            farmaciaSelecionada = null;
            txtNomeFarmacia.clear(); txtEnderecoFarmacia.clear(); txtQuantidadeFarmacia.clear();
        } else {
            lblFarmacia.setText("Erro ao deletar farmácia.");
        }
        tabelaFarmacia.setEditable(false);
        carregarTabelaFarmacia();
    }

    // SALÃO
    @FXML private TextField txtNomeSalao;
    @FXML private TextField txtFuncionariosSalao;
    @FXML private TextField txtServicoSalao;
    @FXML private Label     lblSalao;
    @FXML private TableView<Salao>            tabelaSalao;
    @FXML private TableColumn<Salao, Integer> colSalaoId;
    @FXML private TableColumn<Salao, String>  colSalaoNome;
    @FXML private TableColumn<Salao, Integer> colSalaoFuncionarios;
    @FXML private TableColumn<Salao, String>  colSalaoServico;

    private Salao salaoSelecionado;
    private final SalaoDAO salaoDAO = new SalaoDAO();

    private void carregarTabelaSalao() {
        tabelaSalao.setItems(FXCollections.observableArrayList(salaoDAO.listarTodos()));
    }

    private void configurarColunaEditavelSalao() {
        colSalaoNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colSalaoNome.setOnEditCommit(e -> {
            e.getRowValue().setNome(e.getNewValue());
            salvarEDesativarEdicaoSalao(e.getRowValue());
        });

        colSalaoFuncionarios.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colSalaoFuncionarios.setOnEditCommit(e -> {
            e.getRowValue().setFuncionarios(e.getNewValue());
            salvarEDesativarEdicaoSalao(e.getRowValue());
        });

        colSalaoServico.setCellFactory(TextFieldTableCell.forTableColumn());
        colSalaoServico.setOnEditCommit(e -> {
            e.getRowValue().setServico(e.getNewValue());
            salvarEDesativarEdicaoSalao(e.getRowValue());
        });

        tabelaSalao.setEditable(false);
    }

    private void salvarEDesativarEdicaoSalao(Salao s) {
        salaoDAO.atualizar(s);
        tabelaSalao.setEditable(false);
        lblSalao.setText("Salão #" + s.getId() + " salvo.");
        carregarTabelaSalao();
    }

    private void configurarSelecaoSalao() {
        tabelaSalao.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                salaoSelecionado = selecionado;
                txtNomeSalao.setText(selecionado.getNome());
                txtFuncionariosSalao.setText(String.valueOf(selecionado.getFuncionarios()));
                txtServicoSalao.setText(selecionado.getServico());
                lblSalao.setText("Selecionado: salão #" + selecionado.getId() + ".");
            }
        });
    }

    @FXML public void novoSalao() {
        salaoSelecionado = null;
        tabelaSalao.setEditable(false);
        txtNomeSalao.clear(); txtFuncionariosSalao.clear(); txtServicoSalao.clear();
        tabelaSalao.getSelectionModel().clearSelection();
        lblSalao.setText("Preencha os campos e clique Inserir para salvar.");
    }

    @FXML public void inserirSalao() {
        String nome    = txtNomeSalao.getText().trim();
        String servico = txtServicoSalao.getText().trim();
        if (nome.isEmpty() || servico.isEmpty()) {
            lblSalao.setText("Preencha Nome e Serviço antes de inserir.");
            return;
        }
        int qtd = lerInt(txtFuncionariosSalao, lblSalao);
        if (qtd < 0) return;
        Salao novo = salaoDAO.inserir(new Salao(nome, qtd, servico));
        lblSalao.setText("Salão inserido com ID #" + novo.getId() + ".");
        carregarTabelaSalao();
    }

    @FXML public void editarSalao() {
        if (salaoSelecionado == null) {
            lblSalao.setText("Selecione uma linha na tabela primeiro.");
            return;
        }
        tabelaSalao.setEditable(true);
        int idx = tabelaSalao.getItems().indexOf(salaoSelecionado);
        if (idx < 0) {
            for (int i = 0; i < tabelaSalao.getItems().size(); i++) {
                if (tabelaSalao.getItems().get(i).getId() == salaoSelecionado.getId()) { idx = i; break; }
            }
        }
        tabelaSalao.getSelectionModel().select(idx);
        tabelaSalao.edit(idx, colSalaoNome);
        lblSalao.setText("Edite a célula desejada e pressione ENTER para confirmar.");
    }

    @FXML public void deletarSalao() {
        if (salaoSelecionado == null) { lblSalao.setText("Selecione uma linha na tabela."); return; }
        if (salaoDAO.deletar(salaoSelecionado.getId())) {
            lblSalao.setText("Salão #" + salaoSelecionado.getId() + " removido.");
            salaoSelecionado = null;
            txtNomeSalao.clear(); txtFuncionariosSalao.clear(); txtServicoSalao.clear();
        } else {
            lblSalao.setText("Erro ao deletar salão.");
        }
        tabelaSalao.setEditable(false);
        carregarTabelaSalao();
    }

    // INICIALIZAÇÃO
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Barraca
        colBarracaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBarracaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colBarracaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colBarracaEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        configurarColunaEditavelBarraca();
        configurarSelecaoBarraca();
        carregarTabelaBarraca();

        // Farmácia
        colFarmaciaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFarmaciaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFarmaciaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colFarmaciaEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        configurarColunaEditavelFarmacia();
        configurarSelecaoFarmacia();
        carregarTabelaFarmacia();

        // Salão
        colSalaoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSalaoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSalaoFuncionarios.setCellValueFactory(new PropertyValueFactory<>("funcionarios"));
        colSalaoServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        configurarColunaEditavelSalao();
        configurarSelecaoSalao();
        carregarTabelaSalao();
    }

    // UTILITÁRIOS
    private int lerInt(TextField txt, Label lbl) {
        try {
            int v = Integer.parseInt(txt.getText().trim());
            if (v < 0) throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            lbl.setText("Digite um número inteiro positivo!");
            return -1;
        }
    }

    public void fecharConexao() {
        ConexaoDB.fechar();
    }
}