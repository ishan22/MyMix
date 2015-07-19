package com.mixit.GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIMain extends JFrame  implements KeyListener{

	static CardLayout cl = new CardLayout();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int width = (int) screenSize.getWidth();
	public int height = (int) screenSize.getHeight();
	static JPanel pane = new JPanel();
	public GUIMain() {
		super("MyMix");
		
		add(pane);
		pane.setLayout(cl);
		
		GUILogo gl = new GUILogo();
		GUICombiner gc = new GUICombiner();
		
		
		pane.add(gl, "Logo");
		pane.add(gc, "Combiner");
		
		
		addKeyListener(this);
		
		setSize((int) (width), (int) height-30);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		GUIMain g = new GUIMain();

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			cl.next(pane);
		}
		//else if(gsc.textFieldDone==true){
		//cl.next(pane);
		//}
	}

	public void keyReleased(KeyEvent arg0) {
		
		
	}

	public void keyTyped(KeyEvent arg0) {
		
		
	}

}