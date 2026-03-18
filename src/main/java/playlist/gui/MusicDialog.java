package playlist.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import playlist.Musica;

public class MusicDialog extends JDialog {
    private JTextField txtTitulo;
    private JTextField txtArtista;
    private JTextField txtDuracao;
    private boolean confirmed;

    public MusicDialog(JFrame parent) {
        super(parent, "Adicionar Música", true);
        confirmed = false;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 200);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        txtTitulo = new JTextField(20);
        txtArtista = new JTextField(20);
        txtDuracao = new JTextField(5);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.add(new JLabel("Título:"));
        painel.add(txtTitulo);
        painel.add(new JLabel("Artista:"));
        painel.add(txtArtista);
        painel.add(new JLabel("Duração (seg):"));
        painel.add(txtDuracao);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnOK = new JButton("OK");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnOK);
        painelBotoes.add(btnCancelar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnOK.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> cancelar());
    }

    private void confirmar() {
        confirmed = true;
        dispose();
    }

    private void cancelar() {
        confirmed = false;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Musica getMusica() throws NumberFormatException {
        String titulo = txtTitulo.getText().trim();
        String artista = txtArtista.getText().trim();
        int duracao = Integer.parseInt(txtDuracao.getText().trim());

        return new Musica(titulo, artista, duracao);
    }

    public boolean isValidInput() {
        String titulo = txtTitulo.getText().trim();
        String artista = txtArtista.getText().trim();

        return !titulo.isEmpty() && !artista.isEmpty();
    }
}
