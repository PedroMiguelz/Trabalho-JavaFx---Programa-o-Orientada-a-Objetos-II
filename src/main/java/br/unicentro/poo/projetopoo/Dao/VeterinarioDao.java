package br.unicentro.poo.projetopoo.Dao;

import br.unicentro.poo.projetopoo.Model.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDao {

    public void inserir(Veterinario v) throws SQLException {
        String sql = "INSERT INTO veterinario (crmv, nome, dataEntrada, especializacao, telefone) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getCrmv());
            ps.setString(2, v.getNome());
            ps.setDate(3, Date.valueOf(v.getDataEntrada()));
            ps.setString(4, v.getEspecializacao());
            ps.setString(5, v.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Veterinario> listar(String busca) throws SQLException {
        String sql = "SELECT * FROM veterinario WHERE nome ILIKE ? OR crmv ILIKE ? ORDER BY nome";
        List<Veterinario> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + busca + "%");
            ps.setString(2, "%" + busca + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tel = rs.getString("telefone");
                    Veterinario v = Veterinario.builder()
                            .crmv(rs.getString("crmv"))
                            .nome(rs.getString("nome"))
                            .dataEntrada(rs.getDate("dataEntrada").toLocalDate())
                            .especializacao(rs.getString("especializacao"))
                            .telefone(tel == null ? null : tel.trim())
                            .build();
                    lista.add(v);
                }
            }
        }
        return lista;
    }

    public void atualizar(Veterinario v) throws SQLException {
        String sql = "UPDATE veterinario SET nome = ?, dataEntrada = ?, especializacao = ?, telefone = ? WHERE crmv = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getNome());
            ps.setDate(2, Date.valueOf(v.getDataEntrada()));
            ps.setString(3, v.getEspecializacao());
            ps.setString(4, v.getTelefone());
            ps.setString(5, v.getCrmv());
            ps.executeUpdate();
        }
    }

    public void excluir(String crmv) throws SQLException {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM veterinario WHERE crmv = ?")) {
            ps.setString(1, crmv);
            ps.executeUpdate();
        }
    }
}