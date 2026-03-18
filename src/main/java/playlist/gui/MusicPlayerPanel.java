package playlist.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import playlist.Nodo;

public class MusicPlayerPanel extends JPanel {
    private JLabel lblMusicaAtual;

    public MusicPlayerPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Música Atual"));

        lblMusicaAtual = new JLabel("Nenhuma música selecionada", SwingConstants.CENTER);
        lblMusicaAtual.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblMusicaAtual, BorderLayout.CENTER);
    }

    public void atualizarMusicaAtual(Nodo musicaAtual) {
        if (musicaAtual != null) {
            lblMusicaAtual.setText(musicaAtual.getMusica().toString());
        } else {
            lblMusicaAtual.setText("Playlist vazia");
        }
    }
}
