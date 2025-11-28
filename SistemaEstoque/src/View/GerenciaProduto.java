package view;

import DAO.ProdutoDAO;
import Model.Produto;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GerenciaProduto extends javax.swing.JFrame {

    public GerenciaProduto() {
        initComponents();
        carregarTabela();
    }

    private void carregarTabela() {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            List<Produto> lista = dao.listarProdutos();

            DefaultTableModel modelo = (DefaultTableModel) tabelaProdutos.getModel();
            modelo.setRowCount(0);

            for (Produto p : lista) {
                modelo.addRow(new Object[]{
                    p.getId_produto(),
                    p.getNome_produto(),
                    p.getDescricao_produto(),
                    p.getQuantidade_estoque(),
                    p.getPreco(),
                    p.getData_cadastro()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar tabela: " + e.getMessage());
        }
    }

    private void editarProduto() {
        int linha = tabelaProdutos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto!");
            return;
        }

        int id = (int) tabelaProdutos.getValueAt(linha, 0);

        CadastrarProduto tela = new CadastrarProduto(id);
        tela.setVisible(true);

        dispose();
    }

    private void excluirProduto() {
        int linha = tabelaProdutos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto para excluir!");
            return;
        }

        int id = (int) tabelaProdutos.getValueAt(linha, 0);

        int confirm = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir o produto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ProdutoDAO dao = new ProdutoDAO();
                dao.excluirProduto(id);
                carregarTabela();
                JOptionPane.showMessageDialog(null, "Produto removido!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciar Produtos");

        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Descrição", "Quantidade", "Preço", "Data Cadastro"
            }
        ));
        jScrollPane1.setViewportView(tabelaProdutos);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(evt -> editarProduto());

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(evt -> excluirProduto());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
               
