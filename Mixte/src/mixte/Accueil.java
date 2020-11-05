/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mixte;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author chaima
 */
public class Accueil extends JFrame {

    private String chemin_bf;
    private String chemin_br;

    public String getChemin_bf() {
        return chemin_bf;
    }

    public void setChemin_bf(String chemin_bf) {
        this.chemin_bf = chemin_bf;
    }

    public String getChemin_br() {
        return chemin_br;
    }

    public void setChemin_br(String chemin_br) {
        this.chemin_br = chemin_br;
    }

    public Accueil() {
        super();

        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        JPanel p0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p6 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel l1 = new JLabel("Inference Engine order 0");

        JLabel l11 = new JLabel("Choose a fact base");
        JLabel l12 = new JLabel("Choose a rule base");

        JLabel l2 = new JLabel("Choose type of chaining");
        JLabel l3 = new JLabel("forward chaining");
        JLabel l4 = new JLabel("backward chaining");
        JLabel l5 = new JLabel("mixte chaining");
        JButton b1 = new JButton("ok");
        JButton b2 = new JButton("ok");
        JButton b3 = new JButton("ok");
        JButton bou1 = new JButton("browse");
        JButton bou2 = new JButton("browse");

        p0.add(l1);
        p1.add(l11);
        p1.add(bou1);
        p2.add(l12);
        p2.add(bou2);
        p3.add(l2);
        p4.add(l3);
        p4.add(b1);
        p5.add(l4);
        p5.add(b2);
        p6.add(l5);
        p6.add(b3);


        bou1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

//intitulé du bouton
                chooser.setApproveButtonText("file chosen");
//affiche la boite de dialogue 
                chooser.showOpenDialog(null);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    chemin_bf = chooser.getSelectedFile().getAbsolutePath();
                }
            }
        });
        bou2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

//intitulé du bouton
                chooser.setApproveButtonText("file chosen");
//affiche la boite de dialogue 
                chooser.showOpenDialog(null);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    chemin_br = chooser.getSelectedFile().getAbsolutePath();
                }
            }
        });
        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Avant a = new Avant();
                a.setChem_bf(chemin_bf);
                a.setChem_br(chemin_br);
            }
        });
        
         b2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Arriere a = new Arriere();
                a.setChem_bf(chemin_bf);
                a.setChem_br(chemin_br);
            }
        });
         
          b3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                FenetreMixte a = new FenetreMixte();
                a.setChem_bf(chemin_bf);
                a.setChem_br(chemin_br);
            }
        });

setLayout(new GridLayout(7,1));
        add(p0);
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);
        add(p6);
        setVisible(true);


    }

    public static void main(String[] args) {
        Accueil a = new Accueil();


    }
}
