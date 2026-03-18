package playlist.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import playlist.FilaCircularPlaylist;
import playlist.Musica;
import playlist.Nodo;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final transient FilaCircularPlaylist playlist;
    private transient Nodo musicaAtual;

    private MusicPlayerPanel painelMusica;
    private PlaylistPanel painelPlaylist;
    private ControlPanel painelControle;

    public GUI() {
        playlist = new FilaCircularPlaylist();
        musicaAtual = null;
        initializeUI();
        carregarMusicasExemplo();
    }

    private void initializeUI() {
        setTitle("Playlist Circular - Player");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        painelMusica = new MusicPlayerPanel();
        painelPlaylist = new PlaylistPanel(playlist);
        painelControle = new ControlPanel();

        add(painelMusica, BorderLayout.NORTH);
        add(painelPlaylist, BorderLayout.CENTER);
        add(painelControle, BorderLayout.SOUTH);

        configurarEventos();
    }

    private void configurarEventos() {
        painelControle.getBtnAnterior().addActionListener((ActionEvent e) -> {
            navegarAnterior();
        });

        painelControle.getBtnProxima().addActionListener((ActionEvent e) -> {
            navegarProxima();
        });

        painelControle.getBtnAdicionar().addActionListener((ActionEvent e) -> {
            adicionarMusica();
        });

        painelControle.getBtnRemover().addActionListener((ActionEvent e) -> {
            removerMusica();
        });

        painelPlaylist.getListPlaylist().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && painelPlaylist.getListPlaylist().getSelectedIndex() != -1) {
                selecionarMusica(painelPlaylist.getListPlaylist().getSelectedIndex());
            }
        });
    }

    private void carregarMusicasExemplo() {
        playlist.enqueue(new Musica("Bohemian Rhapsody", "Queen", 354));
        playlist.enqueue(new Musica("Stairway to Heaven", "Led Zeppelin", 482));
        playlist.enqueue(new Musica("Imagine", "John Lennon", 184));
        playlist.enqueue(new Musica("Hotel California", "Eagles", 390));
        painelPlaylist.atualizarListaPlaylist();
        if (!playlist.isEmpty()) {
            musicaAtual = playlist.getHead();
            painelMusica.atualizarMusicaAtual(musicaAtual);
            int index = painelPlaylist.getIndexOf(musicaAtual);
            if (index != -1) {
                painelPlaylist.getListPlaylist().setSelectedIndex(index);
                painelPlaylist.getListPlaylist().ensureIndexIsVisible(index);
            }
        }
    }

    private void navegarProxima() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist está vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        musicaAtual = musicaAtual.getProximo();
        painelMusica.atualizarMusicaAtual(musicaAtual);
        int index = painelPlaylist.getIndexOf(musicaAtual);
        if (index != -1) {
            painelPlaylist.getListPlaylist().setSelectedIndex(index);
            painelPlaylist.getListPlaylist().ensureIndexIsVisible(index);
        }
    }

    private void navegarAnterior() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist está vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Nodo atual = playlist.getHead();
        while (atual.getProximo() != musicaAtual) {
            atual = atual.getProximo();
        }
        musicaAtual = atual;
        painelMusica.atualizarMusicaAtual(musicaAtual);
        int index = painelPlaylist.getIndexOf(musicaAtual);
        if (index != -1) {
            painelPlaylist.getListPlaylist().setSelectedIndex(index);
            painelPlaylist.getListPlaylist().ensureIndexIsVisible(index);
        }
    }

    private void adicionarMusica() {
        MusicDialog dialog = new MusicDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            try {
                if (!dialog.isValidInput()) {
                    JOptionPane.showMessageDialog(this, "Título e artista são obrigatórios!", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Musica novaMusica = dialog.getMusica();
                playlist.enqueue(novaMusica);
                painelPlaylist.atualizarListaPlaylist();

                if (musicaAtual == null) {
                    musicaAtual = playlist.getHead();
                    painelMusica.atualizarMusicaAtual(musicaAtual);
                    int index = painelPlaylist.getIndexOf(musicaAtual);
                    if (index != -1) {
                        painelPlaylist.getListPlaylist().setSelectedIndex(index);
                        painelPlaylist.getListPlaylist().ensureIndexIsVisible(index);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerMusica() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist está vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a música atual?", "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            int posicao = painelPlaylist.getIndexOf(musicaAtual);
            playlist.removeAt(posicao);

            if (playlist.isEmpty()) {
                musicaAtual = null;
            } else {
                musicaAtual = playlist.getHead();
            }

            painelPlaylist.atualizarListaPlaylist();
            painelMusica.atualizarMusicaAtual(musicaAtual);

            if (musicaAtual != null) {
                int index = painelPlaylist.getIndexOf(musicaAtual);
                if (index != -1) {
                    painelPlaylist.getListPlaylist().setSelectedIndex(index);
                    painelPlaylist.getListPlaylist().ensureIndexIsVisible(index);
                }
            }
        }
    }

    private void selecionarMusica(int index) {
        if (playlist.isEmpty()) {
            return;
        }

        Nodo atual = playlist.getHead();
        for (int i = 0; i < index; i++) {
            atual = atual.getProximo();
        }
        musicaAtual = atual;
        painelMusica.atualizarMusicaAtual(musicaAtual);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}
