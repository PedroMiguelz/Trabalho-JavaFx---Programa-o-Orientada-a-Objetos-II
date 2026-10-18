package br.unicentro.poo.projetopoo.Dao;

import br.unicentro.poo.projetopoo.Model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void inserir(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (cpf, nome, telefone) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCpf());
            ps.setString(2, c.getNome());
            ps.setString(3, c.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Cliente> listar(String busca) throws SQLException {
        String sql = """
                SELECT c.cpf, c.nome, c.telefone, COUNT(a.idAnimal) AS qtd
                FROM cliente c LEFT JOIN animal a ON a.cpf_cliente = c.cpf
                WHERE c.nome ILIKE ? OR c.cpf LIKE ?
                GROUP BY c.cpf ORDER BY c.nome""";
        List<Cliente> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + busca + "%");
            ps.setString(2, "%" + busca + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setCpf(rs.getString("cpf"));
                    c.setNome(rs.getString("nome"));
                    c.setTelefone(rs.getString("telefone").trim());
                    c.setQtdAnimais(rs.getInt("qtd"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public void atualizar(Cliente c) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE cpf = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getCpf());
            ps.executeUpdate();
        }
    }

    public void excluir(String cpf) throws SQLException {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM cliente WHERE cpf = ?")) {
            ps.setString(1, cpf);
            ps.executeUpdate();
        }
    }
}
