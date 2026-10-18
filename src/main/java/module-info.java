module br.unicentro.poo.projetopoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.apache.pdfbox;

    opens br.unicentro.poo.projetopoo.Controller.TabCadastro to javafx.fxml;
    opens br.unicentro.poo.projetopoo.Controller.TabConsulta to javafx.fxml;
    opens br.unicentro.poo.projetopoo.Controller.TabAtualizar to javafx.fxml;
    opens br.unicentro.poo.projetopoo.Controller.TabExcluir to javafx.fxml;

    opens br.unicentro.poo.projetopoo to javafx.fxml;
    opens br.unicentro.poo.projetopoo.Controller to javafx.fxml;
    exports br.unicentro.poo.projetopoo;
    opens br.unicentro.poo.projetopoo.Controller.Telas to javafx.fxml;
    opens br.unicentro.poo.projetopoo.Controller.Entidades to javafx.fxml;
}