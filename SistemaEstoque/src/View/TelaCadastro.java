package View;

import DAO.ProdutoDAO;
import Model.Produto;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class TelaCadastro extends JFrame {
    
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private int idProduto = -1; 

    public TelaCadastro() {
        initComponents("Novo Produto");
        configurarJanela();
    }

    public TelaCadastro(Produto p) {
        initComponents("Editar Produto");
        configurarJanela();
        preencherDados(p);
    }

    private void configurarJanela() {
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void preencherDados(Produto p) {
        this.idProduto = p.getId();
        txtNome.setText(p.getNome());
        txtDescricao.setText(p.getDescricao());
        txtPreco.setText(String.valueOf(p.getPreco()).replace(".", ","));
        txtQuantidade.setText(String.valueOf(p.getQuantidade()));
    }

    private void initComponents(String titulo) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 30, 10);
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 10, 8, 10);

        addLabel("Nome:", 1, gbc);
        txtNome = addTextField(1, gbc);

        addLabel("Descrição:", 2, gbc);
        txtDescricao = addTextField(2, gbc);

        addLabel("Preço (R$):", 3, gbc);
        txtPreco = addTextField(3, gbc);

        addLabel("Quantidade:", 4, gbc);
        txtQuantidade = addTextField(4, gbc);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(200, 45));
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> salvarProduto());
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(btnSalvar, gbc);
    }

    private void addLabel(String texto, int y, GridBagConstraints gbc) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        add(label, gbc);
    }

    private JTextField addTextField(int y, GridBagConstraints gbc) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 35));
        gbc.gridy = y;
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        add(field, gbc);
        return field;
    }

    private void salvarProduto() {
        try {
            String nome = txtNome.getText();
            String desc = txtDescricao.getText();
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            int qtd = Integer.parseInt(txtQuantidade.getText());

            if (nome.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            Produto p = new Produto(idProduto, nome, desc, qtd, preco);
            ProdutoDAO dao = new ProdutoDAO();
            
            boolean sucesso;
            if (idProduto == -1) {
                sucesso = dao.InsertProdutoBD(p);
            } else {
                sucesso = dao.UpdateProdutoBD(p);
            }

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Sucesso!");
                dispose(); 
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique os números (Preço e Quantidade).");
        }
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ex) { }
        java.awt.EventQueue.invokeLater(() -> new TelaCadastro().setVisible(true));
    }
}
