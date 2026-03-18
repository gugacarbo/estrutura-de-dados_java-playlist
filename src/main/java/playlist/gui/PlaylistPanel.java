package playlist.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import playlist.FilaCircularPlaylist;
import playlist.Nodo;

public class PlaylistPanel extends JPanel {
    private final FilaCircularPlaylist playlist;
    private JList<String> listPlaylist;
    private DefaultListModel<String> listModel;

    public PlaylistPanel(FilaCircularPlaylist playlist) {
        this.playlist = playlist;
        initializeUI();
    }

    private void initializeUI() {
        setBorder(BorderFactory.createTitledBorder("Playlist"));
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        listPlaylist = new JList<>(listModel);
        listPlaylist.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(listPlaylist);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void atualizarListaPlaylist() {
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

    public JList<String> getListPlaylist() {
        return listPlaylist;
    }

    public int getIndexOf(Nodo nodo) {
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
}
