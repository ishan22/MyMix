package com.mixit.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GUILogo extends JPanel implements KeyListener {
	Image i = new ImageIcon(
			"C:/AngelHacks/MyMix/src/com/mixit/GUI/MyoLogoFinal.jpg")
			.getImage();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int width = (int) screenSize.getWidth();
	public int height = (int) screenSize.getHeight();

	public GUILogo() {
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(i, 0, 0, width, height-30, null);
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}