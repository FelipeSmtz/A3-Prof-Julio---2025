package Model;

import DAO.ProdutoDAO;
import java.util.ArrayList;
import java.sql.SQLException;

public class Produto {

    // Atributos conforme o PDF da A3
    private int id;
    private String nome;
    private String descricao;
    private int quantidade;
    private double preco;
    private String dataCadastro; // Vamos tratar como String para facilitar a visualização na tabela
    private final ProdutoDAO dao;

    // Construtor Vazio
    public Produto() {
        this.dao = new ProdutoDAO();
    }

    // Construtor com dados
    public Produto(int id, String nome, String descricao, int quantidade, double preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dao = new ProdutoDAO();
    }

    // Getters e Setters
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
    
    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }

    @Override
    public String toString() {
        return "Produto: " + nome + " | Qtd: " + quantidade + " | R$ " + preco;
    }

    /* MÉTODOS PARA O DAO (Conectando com o Banco) */
    
    public ArrayList<Produto> getMinhaLista() {
        return dao.getMinhaLista();
    }

    public boolean InsertProdutoBD(String nome, String descricao, int quantidade, double preco) throws SQLException {
        Produto objeto = new Produto(0, nome, descricao, quantidade, preco); // ID 0 pois é auto-increment
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
}