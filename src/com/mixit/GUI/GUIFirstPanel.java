package com.mixit.GUI;

import com.jsyn.*;          // JSyn and Synthesizer classes
import com.jsyn.swing.*;    // Swing tools like knobs and JAppletFrame
import com.jsyn.unitgen.*;  // Unit generators like SineOscillator
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;



import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GUIFirstPanel extends JPanel {
	Image i = new ImageIcon(
			"C:/AngelHacks/MyMix/src/com/mixit/GUI/FirstPanel.jpg").getImage();
	

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int width = (int) screenSize.getWidth();
	public int height = (int) screenSize.getHeight();
	public String songName = "adsfqplwowk";
	static JTextField jt = new JTextField("", 30);
	public GUIFirstPanel() {
		setOpaque(false);
		add(jt);
		
		
		
	}
	public static void setText(){
		jt.setText(GUISongChooser.songName);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(i, 0, 0, width/2, height-30, null);
		
		
		g.setColor(Color.CYAN);
		g.fillRect(684, 468, 60, 84);
		//for(double volumeValue=0.0;volumeValue<=0.99;volumeValue+=0.00001){
		g.fillRect(53,(int)(302+(410-(410*0.8))),283,711);
	
		//}
	
	}

}



