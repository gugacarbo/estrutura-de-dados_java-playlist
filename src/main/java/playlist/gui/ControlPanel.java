package playlist.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton btnAnterior;
    private JButton btnProxima;
    private JButton btnAdicionar;
    private JButton btnRemover;

    public ControlPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelNavegacao = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAnterior = new JButton("← Anterior");
        btnProxima = new JButton("Próxima →");
        painelNavegacao.add(btnAnterior);
        painelNavegacao.add(btnProxima);

        JPanel painelControle = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAdicionar = new JButton("Adicionar Música");
        btnRemover = new JButton("Remover Atual");
        painelControle.add(btnAdicionar);
        painelControle.add(btnRemover);

        add(painelNavegacao, BorderLayout.NORTH);
        add(painelControle, BorderLayout.SOUTH);
    }

    public JButton getBtnAnterior() {
        return btnAnterior;
    }

    public JButton getBtnProxima() {
        return btnProxima;
    }

    public JButton getBtnAdicionar() {
        return btnAdicionar;
    }

    public JButton getBtnRemover() {
        return btnRemover;
    }
}
