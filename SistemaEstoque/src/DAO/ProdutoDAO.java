package DAO;

import Model.Produto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    public Connection getConexao() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String url = "jdbc:mysql://localhost:3306/estoqueprodutos?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = ""; //coloque a senha do banco de dados aqui

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Produto> getMinhaLista() {
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            Connection conn = this.getConexao();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet res = stmt.executeQuery("SELECT * FROM estoque");
                while (res.next()) {
                    lista.add(new Produto(
                        res.getInt("id_produto"),
                        res.getString("nome_produto"),
                        res.getString("descricao_produto"),
                        res.getInt("quantidade_estoque"),
                        res.getDouble("preco")
                    ));
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex.getMessage());
        }
        return lista;
    }

    public boolean InsertProdutoBD(Produto objeto) {
        String sql = "INSERT INTO estoque(nome_produto, descricao_produto, quantidade_estoque, preco, data_cadastro) VALUES(?,?,?,?, CURDATE())";
        try {
            Connection conn = this.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getDescricao());
            stmt.setInt(3, objeto.getQuantidade());
            stmt.setDouble(4, objeto.getPreco());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir: " + erro.getMessage());
            return false;
        }
    }

    public boolean DeleteProdutoBD(int id) {
        try {
            Connection conn = this.getConexao();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM estoque WHERE id_produto = " + id);
            stmt.close();
            return true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar: " + erro.getMessage());
            return false;
        }
    }

    public boolean UpdateProdutoBD(Produto objeto) {
        String sql = "UPDATE estoque SET nome_produto=?, descricao_produto=?, quantidade_estoque=?, preco=? WHERE id_produto=?";
        try {
            Connection conn = this.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getDescricao());
            stmt.setInt(3, objeto.getQuantidade());
            stmt.setDouble(4, objeto.getPreco());
            stmt.setInt(5, objeto.getId());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + erro.getMessage());
            return false;
        }
    }

    public Produto carregaProduto(int id) {
        Produto objeto = null;
        try {
            Connection conn = this.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM estoque WHERE id_produto = " + id);
            if (res.next()) {
                objeto = new Produto(
                    id,
                    res.getString("nome_produto"),
                    res.getString("descricao_produto"),
                    res.getInt("quantidade_estoque"),
                    res.getDouble("preco")
                );
            }
            stmt.close();
        } catch (SQLException erro) {
        }
        return objeto;
    }
}
