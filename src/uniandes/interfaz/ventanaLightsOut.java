package uniandes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.PriorityQueue;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.formdev.flatlaf.FlatLightLaf;

import uniandes.interfaz.PanelEntradas;

import uniandes.dpoo.taller4.modelo.*;

public class VentanaLightsOut extends JFrame implements ActionListener, MouseListener {

    private Tablero tablero;
    // private PanelEntradas panelEntradas;
    private Top10 top10;
    private int tamano = 5;
    private int dificultad;
    private JPanel lightsOutGrid;
    private JComboBox<String> comboBox;
    private JRadioButton easy, medium, hard;
    private JButton newGame, restartGame, showTop10, changePlayer;

    public VentanaLightsOut() throws FileNotFoundException, UnsupportedEncodingException {
        tablero = new Tablero(tamano);
        top10 = new Top10();
        JFrame frame = new JFrame(); // creating instance of JFrame
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout()); // using no layout managers

        lightsOutGrid = getLightsOutGrid(tamano, tamano);
        JPanel options = getGameOptions();
        JPanel info = getGameInfo();
        JPanel optionsPanel = getOptionsPanel();

        frame.add(lightsOutGrid, BorderLayout.CENTER);
        frame.add(options, BorderLayout.LINE_END);
        frame.add(info, BorderLayout.PAGE_END);
        frame.add(optionsPanel, BorderLayout.PAGE_START);

        frame.setTitle("Lights Out");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.setAction();

        // Esto se usa para que al cerrar la ventana se salven los resultados
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            }
        });
        top10.salvarRecords(new File("data/top10.csv"));

    }

    public JPanel getOptionsPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));

        JLabel label = new JLabel("Tamano");
        panel.add(label);

        String[] gridSizes = { "5x5", "6x6", "7x7", "8x8", "9x9", "10x10" };

        comboBox = new JComboBox<>(gridSizes);
        comboBox.addActionListener(this);
        label = new JLabel("Dificultad:");
        easy = new JRadioButton("Facil");
        medium = new JRadioButton("Medio");
        hard = new JRadioButton("Dificil");

        panel.add(comboBox);
        panel.add(label);
        panel.add(easy);
        panel.add(medium);
        panel.add(hard);

        panel.setBorder(BorderFactory.createLineBorder(Color.blue));

        return panel;
    }

    public JPanel getLightsOutGrid(int r, int c) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(r, c));
        HighlightMouseListener hml = new HighlightMouseListener();

        for (int i = 0; i < r * c; i++) {
            JLabel label = new JLabel(); // creating instance of JLabel
            label.setIcon(new ImageIcon("data/luz.png"));
            label.addMouseListener(hml);
            panel.add(label);
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return panel;

    }

    public JPanel getGameOptions() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        newGame = new JButton("Nuevo");
        restartGame = new JButton("Reiniciar");
        showTop10 = new JButton("Top 10");
        changePlayer = new JButton("Cambiar Jugador");

        panel.add(newGame);
        panel.add(restartGame);
        panel.add(showTop10);
        panel.add(changePlayer);

        panel.setBorder(BorderFactory.createLineBorder(Color.blue));

        return panel;
    }

    public JPanel getGameInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));

        JLabel label = new JLabel("Jugadas: ");
        panel.add(label);

        label = new JLabel(tablero.darJugadas() + "");
        panel.add(label);

        label = new JLabel("Jugador: ");
        panel.add(label);

        label = new JLabel("0");
        panel.add(label);

        panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        return panel;
    }

    public JFrame getTop10() {
        JFrame frame = new JFrame(); // creating instance of JFrame
        frame.setSize(250, 450);
        frame.setLayout(new GridLayout(11, 1));

        JLabel label = new JLabel("Jugador: Puntaje");
        frame.add(label);

        Collection<RegistroTop10> top10List = top10.darRegistros();

        for (RegistroTop10 registro : top10List) {
            label = new JLabel(registro.darNombre() + ": " + registro.darPuntos());
            frame.add(label);
        }

        frame.setTitle("Top 10");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }

    private void setAction() {
        // Config Options
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        // Game Options
        newGame.addActionListener(this);
        restartGame.addActionListener(this);
        showTop10.addActionListener(this);
        changePlayer.addActionListener(this);
    }

    public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException {
        VentanaLightsOut v = new VentanaLightsOut();
        // FlatLightLaf.install();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        e.getSource();

        if (e.getSource() == easy) {
            System.out.println("easy");
            this.dificultad = 1;
        } else if (e.getSource() == medium) {
            System.out.println("medium");
            this.dificultad = 2;
        } else if (e.getSource() == hard) {
            System.out.println("hard");
            this.dificultad = 3;
        } else if (e.getSource() == newGame) {
            System.out.println("newGame");
            tablero = new Tablero(tamano);
            tablero.desordenar(this.dificultad);
            lightsOutGrid.removeAll();
            lightsOutGrid = getLightsOutGrid(tamano, tamano);
            this.repaint();
        } else if (e.getSource() == restartGame) {
            System.out.println("restartGame");
            tablero.reiniciar();
        } else if (e.getSource() == showTop10) {
            getTop10();
            System.out.println("showTop10");
        } else if (e.getSource() == changePlayer)
            System.out.println("changePlayer");
        else if (e.getSource() == comboBox) {
            System.out.println(tamano);
            String selected = (String) comboBox.getSelectedItem();
            String[] size = selected.split("x");
            tamano = Integer.parseInt(size[0]);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    public class HighlightMouseListener extends MouseAdapter {
        private JLabel previous;

        @Override
        public void mouseClicked(MouseEvent e) {
            Component source = e.getComponent();
            if (!(source instanceof JLabel)) {
                return;
            }
            JLabel label = (JLabel) source;
            if (previous != null) {
                previous.setBackground(null);
                previous.setForeground(null);
                previous.setOpaque(false);
            }
            previous = label;
            label.setForeground(Color.WHITE);
            label.setBackground(Color.YELLOW);
            label.setOpaque(true);
        }

    }

}