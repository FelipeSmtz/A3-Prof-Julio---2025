package View;

import DAO.ProdutoDAO;
import Model.Produto;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class GerenciaProduto extends JFrame {

    private JTable tabela;
    private JLabel lblTotal;
    private JCheckBox chkEsgotados;

    public GerenciaProduto() {
        initComponents();
        carregarDados();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Gerenciamento de Estoque - Stock404");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTopo = new JPanel(new BorderLayout());
        panelTopo.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("  Controle de Estoque");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelTopo.add(titulo, BorderLayout.WEST);
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFiltros.setBackground(new Color(245, 245, 245));
        
        chkEsgotados = new JCheckBox("Ver apenas Esgotados");
        chkEsgotados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkEsgotados.setOpaque(false);
        chkEsgotados.addActionListener(e -> carregarDados());
        
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(new Color(0, 100, 0));
        
        panelFiltros.add(chkEsgotados);
        panelFiltros.add(new JLabel("   |   "));
        panelFiltros.add(lblTotal);
        panelFiltros.add(new JLabel("  "));
        
        panelTopo.add(panelFiltros, BorderLayout.EAST);
        add(panelTopo, BorderLayout.NORTH);

        tabela = new JTable();
        tabela.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nome", "Descrição", "Preço Unit.", "Qtd", "Total Item"}
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 0 || columnIndex == 4) return Integer.class;
                return Object.class;
            }
        });
        
        tabela.setAutoCreateRowSorter(true);
        
        tabela.setRowHeight(25);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 14);
        btnNovo.setFont(btnFont);
        btnEditar.setFont(btnFont);
        btnExcluir.setFont(btnFont);
        btnAtualizar.setFont(btnFont);

        btnNovo.addActionListener(e -> abrirTelaCadastro(null));
        btnEditar.addActionListener(e -> editarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
        btnAtualizar.addActionListener(e -> carregarDados());

        panelBotoes.add(btnNovo);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnAtualizar);
        
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarDados() {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        
        ProdutoDAO dao = new ProdutoDAO();
        double valorTotalEstoque = 0;
        
        for (Produto p : dao.getMinhaLista()) {
            if (chkEsgotados.isSelected() && p.getQuantidade() > 0) {
                continue;
            }
            
            double totalItem = p.getPreco() * p.getQuantidade();
            valorTotalEstoque += totalItem;

            modelo.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                String.format("R$ %.2f", p.getPreco()),
                p.getQuantidade(),
                String.format("R$ %.2f", totalItem)
            });
        }
        
        lblTotal.setText(String.format("Total Estoque: R$ %.2f", valorTotalEstoque));
    }

    private void excluirProduto() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
            return;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linha);
        int id = (int) tabela.getModel().getValueAt(linhaModelo, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Excluir", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ProdutoDAO dao = new ProdutoDAO();
            if (dao.DeleteProdutoBD(id)) {
                carregarDados();
            }
        }
    }

    private void editarProduto() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
            return;
        }
        
        int linhaModelo = tabela.convertRowIndexToModel(linha);
        int id = (int) tabela.getModel().getValueAt(linhaModelo, 0);
        
        ProdutoDAO dao = new ProdutoDAO();
        Produto p = dao.carregaProduto(id);
        
        if (p != null) {
            abrirTelaCadastro(p);
        }
    }

    private void abrirTelaCadastro(Produto p) {
        TelaCadastro tela;
        if (p == null) {
            tela = new TelaCadastro();
        } else {
            tela = new TelaCadastro(p);
        }
        
        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                carregarDados();
            }
        });
        
        tela.setVisible(true);
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ex) {}
        java.awt.EventQueue.invokeLater(() -> new GerenciaProduto().setVisible(true));
    }
}
