package DAO;

import Model.Produto;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoDAO {

    public static ArrayList<Produto> MinhaLista = new ArrayList<>();

    public ProdutoDAO() {
    }

    public Connection getConexao() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            
            String server = "localhost";
            String database = "estoqueprodutos"; 
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "";

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Erro de Conexão: " + e.getMessage());
            return null;
        }
    }

    public int maiorID() throws SQLException {
        int maior = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(id_produto) id FROM estoque");
            if (res.next()) {
                maior = res.getInt("id");
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro maiorID: " + ex.getMessage());
        }
        return maior;
    }

    public ArrayList<Produto> getMinhaLista() {
        MinhaLista.clear();
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM estoque");
            while (res.next()) {
                int id = res.getInt("id_produto");
                String nome = res.getString("nome_produto");
                String desc = res.getString("descricao_produto");
                int qtd = res.getInt("quantidade_estoque");
                double preco = res.getDouble("preco");
                
                Produto p = new Produto(id, nome, desc, qtd, preco);
                MinhaLista.add(p);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return MinhaLista;
    }

    public boolean InsertProdutoBD(Produto objeto) {
        String sql = "INSERT INTO estoque(id_produto, nome_produto, descricao_produto, quantidade_estoque, preco, data_cadastro) VALUES(?,?,?,?,?, CURDATE())";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getDescricao());
            stmt.setInt(4, objeto.getQuantidade());
            stmt.setDouble(5, objeto.getPreco());
            
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean DeleteProdutoBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM estoque WHERE id_produto = " + id);
            stmt.close();
            return true;
        } catch (SQLException erro) {
            return false;
        }
    }

    public boolean UpdateProdutoBD(Produto objeto) {
        String sql = "UPDATE estoque SET nome_produto=?, descricao_produto=?, quantidade_estoque=?, preco=? WHERE id_produto=?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getDescricao());
            stmt.setInt(3, objeto.getQuantidade());
            stmt.setDouble(4, objeto.getPreco());
            stmt.setInt(5, objeto.getId());

            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public Produto carregaProduto(int id) {
        Produto objeto = null;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM estoque WHERE id_produto = " + id);
            if (res.next()) {
                String nome = res.getString("nome_produto");
                String desc = res.getString("descricao_produto");
                int qtd = res.getInt("quantidade_estoque");
                double preco = res.getDouble("preco");
                
                objeto = new Produto(id, nome, desc, qtd, preco);
            }
            stmt.close();
        } catch (SQLException erro) {
        }
        return objeto;
    }
}
