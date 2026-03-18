package estrutura.dados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private FilaCircularPlaylist playlist;
    private Nodo musicaAtual;
    private JLabel lblMusicaAtual;
    private JList<String> listPlaylist;
    private DefaultListModel<String> listModel;

    public MainGUI() {
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

        JPanel painelMusica = new JPanel(new BorderLayout());
        painelMusica.setBorder(BorderFactory.createTitledBorder("Música Atual"));
        lblMusicaAtual = new JLabel("Nenhuma música selecionada", SwingConstants.CENTER);
        lblMusicaAtual.setFont(new Font("Arial", Font.BOLD, 16));
        painelMusica.add(lblMusicaAtual, BorderLayout.CENTER);

        JPanel painelNavegacao = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAnterior = new JButton("← Anterior");
        JButton btnProxima = new JButton("Próxima →");
        painelNavegacao.add(btnAnterior);
        painelNavegacao.add(btnProxima);

        JPanel painelControle = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAdicionar = new JButton("Adicionar Música");
        JButton btnRemover = new JButton("Remover Atual");
        painelControle.add(btnAdicionar);
        painelControle.add(btnRemover);

        listModel = new DefaultListModel<>();
        listPlaylist = new JList<>(listModel);
        listPlaylist.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(listPlaylist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Playlist"));

        add(painelMusica, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(painelNavegacao, BorderLayout.SOUTH);
        add(painelControle, BorderLayout.EAST);

        btnAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarAnterior();
            }
        });

        btnProxima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarProxima();
            }
        });

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarMusica();
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerMusica();
            }
        });

        listPlaylist.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listPlaylist.getSelectedIndex() != -1) {
                selecionarMusica(listPlaylist.getSelectedIndex());
            }
        });
    }

    private void carregarMusicasExemplo() {
        playlist.enqueue(new Musica("Bohemian Rhapsody", "Queen", 354));
        playlist.enqueue(new Musica("Stairway to Heaven", "Led Zeppelin", 482));
        playlist.enqueue(new Musica("Imagine", "John Lennon", 184));
        playlist.enqueue(new Musica("Hotel California", "Eagles", 390));
        atualizarListaPlaylist();
        if (!playlist.isEmpty()) {
            musicaAtual = playlist.getHead();
            atualizarMusicaAtual();
        }
    }

    private void atualizarListaPlaylist() {
        listModel.clear();
        if (playlist.isEmpty()) {
            return;
        }

        Nodo atual = playlist.getHead();
        for (int i = 0; i < playlist.size(); i++) {
            listModel.addElement((i + 1) + ". " + atual.getMusica().toString());
            atual = atual.getProximo();
        }
    }

    private void atualizarMusicaAtual() {
        if (musicaAtual != null) {
            lblMusicaAtual.setText(musicaAtual.getMusica().toString());
            int index = getIndexOf(musicaAtual);
            if (index != -1) {
                listPlaylist.setSelectedIndex(index);
                listPlaylist.ensureIndexIsVisible(index);
            }
        } else {
            lblMusicaAtual.setText("Playlist vazia");
        }
    }

    private int getIndexOf(Nodo nodo) {
        if (playlist.isEmpty()) {
            return -1;
        }

        Nodo atual = playlist.getHead();
        for (int i = 0; i < playlist.size(); i++) {
            if (atual == nodo) {
                return i;
            }
            atual = atual.getProximo();
        }
        return -1;
    }

    private void navegarProxima() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist está vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        musicaAtual = musicaAtual.getProximo();
        atualizarMusicaAtual();
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
        atualizarMusicaAtual();
    }

    private void adicionarMusica() {
        JTextField txtTitulo = new JTextField(20);
        JTextField txtArtista = new JTextField(20);
        JTextField txtDuracao = new JTextField(5);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.add(new JLabel("Título:"));
        painel.add(txtTitulo);
        painel.add(new JLabel("Artista:"));
        painel.add(txtArtista);
        painel.add(new JLabel("Duração (seg):"));
        painel.add(txtDuracao);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Adicionar Música", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String titulo = txtTitulo.getText().trim();
                String artista = txtArtista.getText().trim();
                int duracao = Integer.parseInt(txtDuracao.getText().trim());

                if (titulo.isEmpty() || artista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Título e artista são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                playlist.enqueue(new Musica(titulo, artista, duracao));
                atualizarListaPlaylist();

                if (musicaAtual == null) {
                    musicaAtual = playlist.getHead();
                    atualizarMusicaAtual();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerMusica() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist está vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a música atual?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            int posicao = getIndexOf(musicaAtual);
            playlist.removeAt(posicao);

            if (playlist.isEmpty()) {
                musicaAtual = null;
            } else {
                musicaAtual = playlist.getHead();
            }

            atualizarListaPlaylist();
            atualizarMusicaAtual();
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
        atualizarMusicaAtual();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}
