package br.unicentro.poo.projetopoo.Controller.TabConsulta;

import br.unicentro.poo.projetopoo.Controller.Entidades.ConsultaController;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TabConsultaConsultaController {

    @FXML
    private Button btnConsultarConsulta;

    @FXML
    private Button btnGerarPdfOnAction;

    @FXML
    private Button btnLimparConsulta;

    @FXML
    private DatePicker dpResultadoDataConsulta;

    @FXML
    private TextField txtConsultarIdConsulta;

    @FXML
    private TextField txtResultadoCrmvConsulta;

    @FXML
    private TextField txtResultadoIdAnimal;

    @FXML
    private TextField txtResultadoIdConsulta;

    @FXML
    private TextField txtResultadoValorConsulta;

    @FXML
    private DatePicker dpInicioFaturamento;

    @FXML
    private DatePicker dpFimFaturamento;

    @FXML
    private Label lblTotalFaturado;

    @FXML
    void btnConsultarConsultaOnAction(ActionEvent event) {
        String buscaIdConsulta = txtConsultarIdConsulta.getText().trim();

        if (buscaIdConsulta.isEmpty()) {
            Alertas.mostrarAviso("Insira o ID da consulta");
            txtConsultarIdConsulta.requestFocus();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(buscaIdConsulta);
        } catch (NumberFormatException e) {
            Alertas.mostrarErro("Informe um ID válido (número inteiro)");
            txtConsultarIdConsulta.requestFocus();
            return;
        }

        try {
            ConsultaController consultaController = new ConsultaController();
            List<Consulta> consultas = consultaController.buscarConsultas(String.valueOf(id));

            Consulta c = consultas.stream()
                    .filter(consulta -> consulta.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (c == null) {
                Alertas.mostrarAviso("Nenhuma consulta encontrada");
                limparCampos();
                return;
            }

            txtResultadoIdConsulta.setText(String.valueOf(c.getId()));
            dpResultadoDataConsulta.setValue(c.getData());
            txtResultadoValorConsulta.setText(c.getValor() != null ? c.getValor().toString() : "");
            txtResultadoIdAnimal.setText(String.valueOf(c.getAnimal().getId()));
            txtResultadoCrmvConsulta.setText(c.getVeterinario().getCrmv());

            Alertas.mostrarSucesso("Consulta encontrada!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao consultar consulta: " + e.getMessage());
        }
    }

    @FXML
    void btnGerarPdfOnAction(ActionEvent event) {
        String idStr = txtResultadoIdConsulta.getText().trim();
        if (idStr.isEmpty()) {
            Alertas.mostrarAviso("Consulte ou selecione uma consulta primeiro para gerar o comprovante.");
            return;
        }

        // 2. Abre a janela para escolher onde salvar o PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Comprovante de Atendimento");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf"));
        fileChooser.setInitialFileName("comprovante_consulta_" + idStr + ".pdf");

        File arquivo = fileChooser.showSaveDialog(btnConsultarConsulta.getScene().getWindow());

        if (arquivo != null) {
            try (PDDocument documento = new PDDocument()) {
                PDPage pagina = new PDPage();
                documento.addPage(pagina);

                try (PDPageContentStream conteudo = new PDPageContentStream(documento, pagina)) {

                    // Cabeçalho da Clínica
                    conteudo.beginText();
                    conteudo.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                    conteudo.newLineAtOffset(50, 730);
                    conteudo.showText("Pet-Shop");
                    conteudo.endText();

                    conteudo.beginText();
                    conteudo.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                    conteudo.newLineAtOffset(50, 710);
                    conteudo.showText("Comprovante de Atendimento / Receituário");
                    conteudo.endText();

                    // Linha divisória simples (desenho)
                    conteudo.setLineWidth(1);
                    conteudo.moveTo(50, 695);
                    conteudo.lineTo(550, 695);
                    conteudo.stroke();

                    // Dados da Consulta recolhidos dos campos da tela
                    int y = 650;
                    int espacamento = 25;

                    String[] dados = {
                            "ID da Consulta: " + txtResultadoIdConsulta.getText(),
                            "Data do Atendimento: " + (dpResultadoDataConsulta.getValue() != null ? dpResultadoDataConsulta.getValue().toString() : ""),
                            "ID do Animal: " + txtResultadoIdAnimal.getText(),
                            "CRMV do Veterinário: " + txtResultadoCrmvConsulta.getText(),
                            "Valor do Serviço: R$ " + txtResultadoValorConsulta.getText()
                    };

                    for (String linha : dados) {
                        conteudo.beginText();
                        conteudo.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                        conteudo.newLineAtOffset(50, y);
                        conteudo.showText(linha);
                        conteudo.endText();
                        y -= espacamento;
                    }

                    // Rodapé / Observações
                    y -= 40;
                    conteudo.beginText();
                    conteudo.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 10);
                    conteudo.newLineAtOffset(50, y);
                    conteudo.showText("Documento gerado eletronicamente pelo sistema em " + java.time.LocalDate.now());
                    conteudo.endText();
                }

                // Salva o documento no caminho escolhido
                documento.save(arquivo);
                Alertas.mostrarSucesso("PDF gerado com sucesso em:\n" + arquivo.getAbsolutePath());

            } catch (IOException e) {
                Alertas.mostrarErro("Erro ao gerar o arquivo PDF: " + e.getMessage());
            }
        }
    }

    @FXML
    void btnCalcularTotalOnAction(){
        try {
            ConsultaController consultaController = new ConsultaController();
            BigDecimal total = consultaController.calcularTotalFaturado(
                    dpInicioFaturamento.getValue(), dpFimFaturamento.getValue());

            NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            lblTotalFaturado.setText(moeda.format(total));

        } catch (IllegalArgumentException e) {
            Alertas.mostrarAviso(e.getMessage());
        } catch (SQLException e) {
            Alertas.mostrarErro("Erro ao calcular faturamento: " + e.getMessage());
        }
    }

    @FXML
    void btnLimparConsultaOnAction(ActionEvent event) {
        limparCampos();
    }

    private void limparCampos() {
        txtConsultarIdConsulta.clear();
        txtResultadoIdConsulta.clear();
        dpResultadoDataConsulta.setValue(null);
        txtResultadoValorConsulta.clear();
        txtResultadoIdAnimal.clear();
        txtResultadoCrmvConsulta.clear();
        txtConsultarIdConsulta.requestFocus();
    }
}