package br.unicentro.poo.projetopoo.Dao;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Model.Veterinario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao {
    public void inserir(Consulta c) throws SQLException {
        String sql = "INSERT INTO consulta (data,valor,idAnimal,crmv_veterinario) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            //nao aceita localData
            ps.setObject(1, c.getData());
            ps.setBigDecimal(2, c.getValor());
            ps.setInt(3, c.getAnimal().getId());
            ps.setString(4, c.getVeterinario().getCrmv());
            ps.executeUpdate();
        }
    }

    public List<Consulta> listar(String busca) throws SQLException {
        String sql = """
            SELECT c.idConsulta, c.data, c.valor,
                   a.idAnimal, a.nome AS nome_animal,
                   v.crmv, v.nome AS nome_vet
            FROM consulta c
            JOIN animal a ON a.idAnimal = c.idAnimal
            JOIN veterinario v ON v.crmv = c.crmv_veterinario
            WHERE CAST(c.idConsulta AS TEXT) LIKE ?
               OR a.nome ILIKE ?
               OR v.nome ILIKE ?
               OR v.crmv ILIKE ?
            ORDER BY c.data DESC, c.idConsulta DESC""";

        List<Consulta> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            String filtro = "%" + busca + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ps.setString(4, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = Consulta.builder()
                            .id(rs.getInt("idConsulta"))
                            .data(rs.getObject("data", LocalDate.class))
                            .valor(rs.getBigDecimal("valor"))
                            .animal(Animal.builder().id(rs.getInt("idAnimal")).nome(rs.getString("nome_animal")).build())
                            .veterinario(Veterinario.builder().crmv(rs.getString("crmv")).nome(rs.getString("nome_vet")).build())
                            .build();

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public void atualizar(Consulta c) throws SQLException {
        String sql = "UPDATE consulta SET data = ?, valor = ?, idAnimal = ?, crmv_veterinario = ? WHERE idConsulta = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, c.getData());
            ps.setBigDecimal(2, c.getValor());
            ps.setInt(3, c.getAnimal().getId());
            ps.setString(4, c.getVeterinario().getCrmv());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM consulta WHERE idConsulta = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //caso n tenha consulta, o sum retorna null e getBigDecimal retorna null
    //coalesce -> retorna 0
    public BigDecimal totalFaturado(LocalDate inicio, LocalDate fim) throws SQLException {
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM consulta WHERE data BETWEEN ? AND ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, inicio);
            ps.setObject(2, fim);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBigDecimal("total");
            }
        }
    }

    public int contarPorVeterinario(String crmv) throws SQLException {
        String sql = "SELECT COUNT(*) FROM consulta WHERE crmv_veterinario = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, crmv);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    /*eyshla*/
    public int contarPorAnimal(int idAnimal) throws SQLException {
        String sql = "SELECT COUNT(*) FROM consulta WHERE idAnimal = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAnimal);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

}
