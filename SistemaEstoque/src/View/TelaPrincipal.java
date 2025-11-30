package View;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        initComponents();
        configurarJanela();
    }

    private void configurarJanela() {
        setTitle("Stock404 - Menu Principal");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblImagem = new JLabel();
        lblImagem.setHorizontalAlignment(JLabel.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Stock404.png"));
            java.awt.Image img = icon.getImage().getScaledInstance(250, 200, java.awt.Image.SCALE_SMOOTH);
            lblImagem.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagem.setText("Logo Stock404");
        }
        
        gbc.gridy = 0;
        add(lblImagem, gbc);

        JButton btnCadastro = criarBotao("Novo Cadastro");
        btnCadastro.addActionListener(e -> {
            TelaCadastro tela = new TelaCadastro();
            tela.setVisible(true);
        });
        
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(btnCadastro, gbc);

        JButton btnEstoque = criarBotao("Gerenciar Estoque");
        btnEstoque.addActionListener(e -> {
            GerenciaProduto tela = new GerenciaProduto();
            tela.setVisible(true);
        });

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(btnEstoque, gbc);

        JButton btnSair = criarBotao("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        gbc.gridy = 3;
        add(btnSair, gbc);
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return btn;
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 15);
        } catch (Exception ex) {}

        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
