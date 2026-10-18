package br.unicentro.poo.projetopoo.Dao;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDao {

    public void inserir(Animal a) throws SQLException {
        String sql = "INSERT INTO animal (nome, especie, raca, sexo, cpf_cliente) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getEspecie());
            ps.setString(3, a.getRaca());
            ps.setString(4, a.getSexo());
            ps.setString(5, a.getCliente().getCpf());
            ps.executeUpdate();
        }
    }

    public List<Animal> listar(String busca) throws SQLException {
        String sql = """
                SELECT a.idAnimal, a.nome, a.especie, a.raca, a.sexo, c.cpf, c.nome as cnome
                FROM animal a JOIN cliente c ON c.cpf = a.cpf_cliente
                WHERE a.nome ILIKE ? 
                ORDER BY a.nome""";
        List<Animal> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + busca + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setCpf(rs.getString("cpf"));
                    c.setNome(rs.getString("cnome"));

                    Animal a = new Animal();
                    a.setId(rs.getInt("idAnimal"));
                    a.setNome(rs.getString("nome"));
                    a.setEspecie(rs.getString("especie"));
                    a.setRaca(rs.getString("raca"));
                    a.setSexo(rs.getString("sexo"));
                    a.setCliente(c);
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    public Animal buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT a.idAnimal, a.nome, a.especie, a.raca, a.sexo, c.cpf, c.nome AS cnome
            FROM animal a JOIN cliente c ON c.cpf = a.cpf_cliente
            WHERE a.idAnimal = ?""";

        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setCpf(rs.getString("cpf"));
                    c.setNome(rs.getString("cnome"));

                    Animal a = new Animal();
                    a.setId(rs.getInt("idAnimal"));
                    a.setNome(rs.getString("nome"));
                    a.setEspecie(rs.getString("especie"));
                    a.setRaca(rs.getString("raca"));
                    a.setSexo(rs.getString("sexo"));
                    a.setCliente(c);
                    return a;
                }
            }
        }
        return null;
    }

    public void atualizar(Animal a) throws SQLException {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, sexo = ?, cpf_cliente = ? WHERE idAnimal = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getEspecie());
            ps.setString(3, a.getRaca());
            ps.setString(4, a.getSexo());
            ps.setString(5, a.getCliente().getCpf());
            ps.setInt(6, a.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM animal WHERE idAnimal = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
