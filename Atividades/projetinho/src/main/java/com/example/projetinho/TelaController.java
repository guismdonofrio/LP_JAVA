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

import java.net.URL;
import java.util.ResourceBundle;

public class TelaController implements Initializable {

    // BARRACA
    @FXML private TextField  txtNomeBarraca;
    @FXML private TextField  txtProdutoBarraca;
    @FXML private TextField  txtQuantidadeBarraca;
    @FXML private Label      lblBarraca;
    @FXML private TableView<Barraca>            tabelaBarraca;
    @FXML private TableColumn<Barraca, Integer> colBarracaId;
    @FXML private TableColumn<Barraca, String>  colBarracaNome;
    @FXML private TableColumn<Barraca, String>  colBarracaProduto;
    @FXML private TableColumn<Barraca, Integer> colBarracaEstoque;

    private Barraca barraca;
    private final BarracaDAO barracaDAO = new BarracaDAO();

    private void carregarTabelaBarraca() {
        tabelaBarraca.setItems(FXCollections.observableArrayList(barracaDAO.listarTodas()));
    }

    // Ao clicar em uma linha, preenche os campos para edição.
    private void configurarSelecaoBarraca() {
        tabelaBarraca.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                barraca = selecionado;
                txtNomeBarraca.setText(selecionado.getNome());
                txtProdutoBarraca.setText(selecionado.getProduto());
                txtQuantidadeBarraca.setText(String.valueOf(selecionado.getEstoque()));
                lblBarraca.setText("Editando barraca #" + selecionado.getId() + " — altere os campos e clique Vender ou Repor.");
            }
        });
    }

    private boolean garantirBarraca() {
        if (barraca == null) {
            int qtd = lerInt(txtQuantidadeBarraca, lblBarraca);
            if (qtd < 0) return false;
            barraca = barracaDAO.inserir(
                    new Barraca(txtNomeBarraca.getText(), txtProdutoBarraca.getText(), qtd)
            );
            lblBarraca.setText("Barraca criada com ID #" + barraca.getId());
            carregarTabelaBarraca();
        }
        return true;
    }

    @FXML public void venderBarraca() {
        if (!garantirBarraca()) return;
        int qtd = lerInt(txtQuantidadeBarraca, lblBarraca);
        if (qtd < 0) return;
        // Se vier da seleção de linha, sincroniza nome/produto editados também
        barraca.setNome(txtNomeBarraca.getText());
        barraca.setProduto(txtProdutoBarraca.getText());
        lblBarraca.setText(barraca.vender(qtd));
        barracaDAO.atualizar(barraca);
        carregarTabelaBarraca();
    }

    @FXML public void reporBarraca() {
        if (!garantirBarraca()) return;
        int qtd = lerInt(txtQuantidadeBarraca, lblBarraca);
        if (qtd < 0) return;
        barraca.setNome(txtNomeBarraca.getText());
        barraca.setProduto(txtProdutoBarraca.getText());
        lblBarraca.setText(barraca.repor(qtd));
        barracaDAO.atualizar(barraca);
        carregarTabelaBarraca();
    }

    @FXML public void editarBarraca() {
        if (barraca == null) { lblBarraca.setText("Selecione uma linha para editar."); return; }
        barraca.setNome(txtNomeBarraca.getText());
        barraca.setProduto(txtProdutoBarraca.getText());
        int qtd = lerInt(txtQuantidadeBarraca, lblBarraca);
        if (qtd < 0) return;
        barraca.setEstoque(qtd);
        barracaDAO.atualizar(barraca);
        lblBarraca.setText("Barraca #" + barraca.getId() + " atualizada.");
        carregarTabelaBarraca();
    }

    @FXML public void deletarBarraca() {
        if (barraca == null) { lblBarraca.setText("Nenhuma barraca selecionada."); return; }
        if (barracaDAO.deletar(barraca.getId())) {
            lblBarraca.setText("Barraca #" + barraca.getId() + " removida.");
            barraca = null;
            limparCamposBarraca();
        } else {
            lblBarraca.setText("Erro: barraca não encontrada.");
        }
        carregarTabelaBarraca();
    }

    private void limparCamposBarraca() {
        txtNomeBarraca.clear();
        txtProdutoBarraca.clear();
        txtQuantidadeBarraca.clear();
        tabelaBarraca.getSelectionModel().clearSelection();
    }

    // FARMÁCIA
    @FXML private TextField  txtNomeFarmacia;
    @FXML private TextField  txtEnderecoFarmacia;
    @FXML private TextField  txtQuantidadeFarmacia;
    @FXML private Label      lblFarmacia;
    @FXML private TableView<Farmacia>            tabelaFarmacia;
    @FXML private TableColumn<Farmacia, Integer> colFarmaciaId;
    @FXML private TableColumn<Farmacia, String>  colFarmaciaNome;
    @FXML private TableColumn<Farmacia, String>  colFarmaciaEndereco;
    @FXML private TableColumn<Farmacia, Integer> colFarmaciaEstoque;

    private Farmacia farmacia;
    private final FarmaciaDAO farmaciaDAO = new FarmaciaDAO();

    private void carregarTabelaFarmacia() {
        tabelaFarmacia.setItems(FXCollections.observableArrayList(farmaciaDAO.listarTodas()));
    }

    private void configurarSelecaoFarmacia() {
        tabelaFarmacia.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                farmacia = selecionado;
                txtNomeFarmacia.setText(selecionado.getNome());
                txtEnderecoFarmacia.setText(selecionado.getEndereco());
                txtQuantidadeFarmacia.setText(String.valueOf(selecionado.getEstoque()));
                lblFarmacia.setText("Editando farmácia #" + selecionado.getId() + " — altere os campos e clique Comprar ou Vender.");
            }
        });
    }

    private boolean garantirFarmacia() {
        if (farmacia == null) {
            int qtd = lerInt(txtQuantidadeFarmacia, lblFarmacia);
            if (qtd < 0) return false;
            farmacia = farmaciaDAO.inserir(
                    new Farmacia(txtNomeFarmacia.getText(), txtEnderecoFarmacia.getText(), qtd)
            );
            lblFarmacia.setText("Farmácia criada com ID #" + farmacia.getId());
            carregarTabelaFarmacia();
        }
        return true;
    }

    @FXML public void comprarFarmacia() {
        if (!garantirFarmacia()) return;
        int qtd = lerInt(txtQuantidadeFarmacia, lblFarmacia);
        if (qtd < 0) return;
        farmacia.setNome(txtNomeFarmacia.getText());
        farmacia.setEndereco(txtEnderecoFarmacia.getText());
        lblFarmacia.setText(farmacia.comprar(qtd));
        farmaciaDAO.atualizar(farmacia);
        carregarTabelaFarmacia();
    }

    @FXML public void venderFarmacia() {
        if (!garantirFarmacia()) return;
        int qtd = lerInt(txtQuantidadeFarmacia, lblFarmacia);
        if (qtd < 0) return;
        farmacia.setNome(txtNomeFarmacia.getText());
        farmacia.setEndereco(txtEnderecoFarmacia.getText());
        lblFarmacia.setText(farmacia.vender(qtd));
        farmaciaDAO.atualizar(farmacia);
        carregarTabelaFarmacia();
    }

    @FXML public void editarFarmacia() {
        if (farmacia == null) { lblFarmacia.setText("Selecione uma linha para editar."); return; }
        farmacia.setNome(txtNomeFarmacia.getText());
        farmacia.setEndereco(txtEnderecoFarmacia.getText());
        int qtd = lerInt(txtQuantidadeFarmacia, lblFarmacia);
        if (qtd < 0) return;
        farmacia.setEstoque(qtd);
        farmaciaDAO.atualizar(farmacia);
        lblFarmacia.setText("Farmácia #" + farmacia.getId() + " atualizada.");
        carregarTabelaFarmacia();
    }

    @FXML public void deletarFarmacia() {
        if (farmacia == null) { lblFarmacia.setText("Nenhuma farmácia selecionada."); return; }
        if (farmaciaDAO.deletar(farmacia.getId())) {
            lblFarmacia.setText("Farmácia #" + farmacia.getId() + " removida.");
            farmacia = null;
            limparCamposFarmacia();
        } else {
            lblFarmacia.setText("Erro: farmácia não encontrada.");
        }
        carregarTabelaFarmacia();
    }

    private void limparCamposFarmacia() {
        txtNomeFarmacia.clear();
        txtEnderecoFarmacia.clear();
        txtQuantidadeFarmacia.clear();
        tabelaFarmacia.getSelectionModel().clearSelection();
    }

    // SALÃO
    @FXML private TextField  txtNomeSalao;
    @FXML private TextField  txtFuncionariosSalao;
    @FXML private TextField  txtServicoSalao;
    @FXML private Label      lblSalao;
    @FXML private TableView<Salao>            tabelaSalao;
    @FXML private TableColumn<Salao, Integer> colSalaoId;
    @FXML private TableColumn<Salao, String>  colSalaoNome;
    @FXML private TableColumn<Salao, Integer> colSalaoFuncionarios;
    @FXML private TableColumn<Salao, String>  colSalaoServico;

    private Salao salao;
    private final SalaoDAO salaoDAO = new SalaoDAO();

    private void carregarTabelaSalao() {
        tabelaSalao.setItems(FXCollections.observableArrayList(salaoDAO.listarTodos()));
    }

    private void configurarSelecaoSalao() {
        tabelaSalao.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                salao = selecionado;
                txtNomeSalao.setText(selecionado.getNome());
                txtFuncionariosSalao.setText(String.valueOf(selecionado.getFuncionarios()));
                txtServicoSalao.setText(selecionado.getServico());
                lblSalao.setText("Editando salão #" + selecionado.getId() + " — altere os campos e clique Contratar.");
            }
        });
    }

    private boolean garantirSalao() {
        if (salao == null) {
            int qtd = lerInt(txtFuncionariosSalao, lblSalao);
            if (qtd < 0) return false;
            salao = salaoDAO.inserir(
                    new Salao(txtNomeSalao.getText(), qtd, txtServicoSalao.getText())
            );
            lblSalao.setText("Salão criado com ID #" + salao.getId());
            carregarTabelaSalao();
        }
        return true;
    }

    @FXML public void prestarServico() {
        if (!garantirSalao()) return;
        salao.setNome(txtNomeSalao.getText());
        salao.setServico(txtServicoSalao.getText());
        salaoDAO.atualizar(salao);
        lblSalao.setText(salao.prestarServico());
        carregarTabelaSalao();
    }

    @FXML public void contratar() {
        if (!garantirSalao()) return;
        int qtd = lerInt(txtFuncionariosSalao, lblSalao);
        if (qtd < 0) return;
        salao.setNome(txtNomeSalao.getText());
        salao.setServico(txtServicoSalao.getText());
        lblSalao.setText(salao.contratar(qtd));
        salaoDAO.atualizar(salao);
        carregarTabelaSalao();
    }

    @FXML public void editarSalao() {
        if (salao == null) { lblSalao.setText("Selecione uma linha para editar."); return; }
        salao.setNome(txtNomeSalao.getText());
        salao.setServico(txtServicoSalao.getText());
        int qtd = lerInt(txtFuncionariosSalao, lblSalao);
        if (qtd < 0) return;
        salao.setFuncionarios(qtd);
        salaoDAO.atualizar(salao);
        lblSalao.setText("Salão #" + salao.getId() + " atualizado.");
        carregarTabelaSalao();
    }

    @FXML public void deletarSalao() {
        if (salao == null) { lblSalao.setText("Nenhum salão selecionado."); return; }
        if (salaoDAO.deletar(salao.getId())) {
            lblSalao.setText("Salão #" + salao.getId() + " removido.");
            salao = null;
            limparCamposSalao();
        } else {
            lblSalao.setText("Erro: salão não encontrado.");
        }
        carregarTabelaSalao();
    }

    private void limparCamposSalao() {
        txtNomeSalao.clear();
        txtFuncionariosSalao.clear();
        txtServicoSalao.clear();
        tabelaSalao.getSelectionModel().clearSelection();
    }

    // Inicialização
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Barraca
        colBarracaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBarracaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colBarracaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colBarracaEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        configurarSelecaoBarraca();
        carregarTabelaBarraca();

        // Farmácia
        colFarmaciaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFarmaciaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFarmaciaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colFarmaciaEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        configurarSelecaoFarmacia();
        carregarTabelaFarmacia();

        // Salão
        colSalaoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSalaoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSalaoFuncionarios.setCellValueFactory(new PropertyValueFactory<>("funcionarios"));
        colSalaoServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        configurarSelecaoSalao();
        carregarTabelaSalao();
    }

    // Utilitários
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
