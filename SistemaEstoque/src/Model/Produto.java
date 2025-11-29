package Model;

import DAO.ProdutoDAO;
import java.util.ArrayList;
import java.sql.SQLException;

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private int quantidade;
    private double preco;
    private final ProdutoDAO dao;

    public Produto() {
        this.dao = new ProdutoDAO();
    }

    public Produto(int id, String nome, String descricao, int quantidade, double preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dao = new ProdutoDAO();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Nome: " + this.nome + " | Preço: R$ " + this.preco;
    }
    
    public ArrayList<Produto> getMinhaLista() {
        return dao.getMinhaLista();
    }

    public boolean InsertProdutoBD(String nome, String descricao, int quantidade, double preco) throws SQLException {
        Produto objeto = new Produto(0, nome, descricao, quantidade, preco);
        dao.InsertProdutoBD(objeto);
        return true;
    }

    public boolean DeleteProdutoBD(int id) {
        dao.DeleteProdutoBD(id);
        return true;
    }

    public boolean UpdateProdutoBD(int id, String nome, String descricao, int quantidade, double preco) {
        Produto objeto = new Produto(id, nome, descricao, quantidade, preco);
        dao.UpdateProdutoBD(objeto);
        return true;
    }

    public Produto carregaProduto(int id) {
        return dao.carregaProduto(id);
    }

    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
