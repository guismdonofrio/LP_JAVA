package com.example.projetinho;

import com.example.projetinho.entidades.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TelaController {

    @FXML private TextField txtNomeBarraca;
    @FXML private TextField txtProdutoBarraca;
    @FXML private TextField txtQuantidadeBarraca;
    @FXML private Label lblBarraca;

    private Barraca barraca;

    private int lerQuantidade(TextField txt, Label lbl) {
        try {
            return Integer.parseInt(txt.getText());
        } catch (Exception e) {
            lbl.setText("Digite um número válido!");
            return -1;
            //mudar -negativo
        }
    }

    private void criarBarracaSeNecessario() {
        if (barraca == null) {
            int qtd = lerQuantidade(txtQuantidadeBarraca, lblBarraca);
            if (qtd == -1) return;

            barraca = new Barraca(
                    txtNomeBarraca.getText(),
                    txtProdutoBarraca.getText(),
                    qtd
            );
        }
    }

    @FXML
    public void venderBarraca() {
        criarBarracaSeNecessario();
        if (barraca == null) return;

        int qtd = lerQuantidade(txtQuantidadeBarraca, lblBarraca);
        if (qtd == -1) return;

        lblBarraca.setText(barraca.vender(qtd));
    }

    @FXML
    public void reporBarraca() {
        criarBarracaSeNecessario();
        if (barraca == null) return;

        int qtd = lerQuantidade(txtQuantidadeBarraca, lblBarraca);
        if (qtd == -1) return;

        lblBarraca.setText(barraca.repor(qtd));
    }

    @FXML
    public void mostrarBarraca() {
        if (barraca != null) {
            lblBarraca.setText(barraca.mostrarEstoque());
        } else {
            lblBarraca.setText("Crie a barraca primeiro!");
        }
    }

    @FXML private TextField txtNomeFarmacia;
    @FXML private TextField txtEnderecoFarmacia;
    @FXML private TextField txtQuantidadeFarmacia;
    @FXML private Label lblFarmacia;

    private Farmacia farmacia;

    private void criarFarmaciaSeNecessario() {
        if (farmacia == null) {
            int qtd = lerQuantidade(txtQuantidadeFarmacia, lblFarmacia);
            if (qtd == -1) return;

            farmacia = new Farmacia(
                    txtNomeFarmacia.getText(),
                    txtEnderecoFarmacia.getText(),
                    qtd
            );
        }
    }

    @FXML
    public void comprarFarmacia() {
        criarFarmaciaSeNecessario();
        if (farmacia == null) return;

        int qtd = lerQuantidade(txtQuantidadeFarmacia, lblFarmacia);
        if (qtd == -1) return;

        lblFarmacia.setText(farmacia.comprar(qtd));
    }

    @FXML
    public void venderFarmacia() {
        criarFarmaciaSeNecessario();
        if (farmacia == null) return;

        int qtd = lerQuantidade(txtQuantidadeFarmacia, lblFarmacia);
        if (qtd == -1) return;

        lblFarmacia.setText(farmacia.vender(qtd));
    }

    @FXML
    public void mostrarFarmacia() {
        if (farmacia != null) {
            lblFarmacia.setText(farmacia.mostrarEstoque());
        } else {
            lblFarmacia.setText("Crie a farmácia primeiro!");
        }
    }

    @FXML private TextField txtNomeSalao;
    @FXML private TextField txtFuncionariosSalao;
    @FXML private TextField txtServicoSalao;
    @FXML private Label lblSalao;

    private Salao salao;

    private void criarSalaoSeNecessario() {
        if (salao == null) {
            int qtd = lerQuantidade(txtFuncionariosSalao, lblSalao);
            if (qtd == -1) return;

            salao = new Salao(
                    txtNomeSalao.getText(),
                    qtd,
                    txtServicoSalao.getText()
            );
        }
    }

    @FXML
    public void prestarServico() {
        criarSalaoSeNecessario();
        if (salao == null) return;

        lblSalao.setText(salao.prestarServico());
    }

    @FXML
    public void contratar() {
        criarSalaoSeNecessario();
        if (salao == null) return;

        int qtd = lerQuantidade(txtFuncionariosSalao, lblSalao);
        if (qtd == -1) return;

        lblSalao.setText(salao.contratar(qtd));
    }

    @FXML
    public void mostrarSalao() {
        if (salao != null) {
            lblSalao.setText(salao.mostrarInfo());
        } else {
            lblSalao.setText("Crie o salão primeiro!");
        }
    }
}