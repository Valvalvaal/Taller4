package uniandes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
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

import org.w3c.dom.events.MouseEvent;

import uniandes.interfaz.PanelEntradas;

import uniandes.dpoo.taller4.modelo.*;

public class VentanaLightsOut extends JFrame implements ActionListener, MouseListener {

    private Tablero tablero;
    // private PanelEntradas panelEntradas;
    private Top10 top10;
    private RegistroTop10 registroTop10;
    private int tamano;

    private JRadioButton easy, medium, hard;
    private JButton newGame, restartGame, showTop10, changePlayer;

    public VentanaLightsOut() {
        tablero = new Tablero(tamano);
        JFrame frame = new JFrame(); // creating instance of JFrame
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout()); // using no layout managers

        JPanel panel = getLightsOutGrid(5, 5);
        JPanel options = getGameOptions();
        JPanel info = getGameInfo();
        JPanel optionsPanel = getOptionsPanel();

        frame.add(panel, BorderLayout.CENTER);
        frame.add(options, BorderLayout.LINE_END);
        frame.add(info, BorderLayout.PAGE_END);
        frame.add(optionsPanel, BorderLayout.PAGE_START);

        frame.setTitle("Lights Out");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public JPanel getOptionsPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));

        JLabel label = new JLabel("Tamano");
        panel.add(label);

        String[] gridSizes = { "5x5", "6x6", "7x7", "8x8", "9x9", "10x10" };

        JComboBox comboBox = new JComboBox(gridSizes);
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
            // Dimension size = label.getPreferredSize();
            // label.setBounds(50, 30, size.width, size.height);
            panel.add(label);
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.blue));
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

        label = new JLabel("0");
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
        frame.setLayout(new GridLayout(11, 1)); // using no layout managers

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

    public static void main(String args[]) {
        VentanaLightsOut v = new VentanaLightsOut();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        e.getSource();

        if (e.getSource() == easy)
            System.out.println("easy");
        else if (e.getSource() == medium)
            System.out.println("medium");
        else if (e.getSource() == hard)
            System.out.println("hard");
        else if (e.getSource() == newGame)
            System.out.println("newGame");
        else if (e.getSource() == restartGame)
            System.out.println("restartGame");
        else if (e.getSource() == showTop10) {
            System.out.println("showTop10");
            getTop10();
        } else if (e.getSource() == changePlayer)
            System.out.println("changePlayer");
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

}