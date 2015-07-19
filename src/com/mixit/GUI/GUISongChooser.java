package com.mixit.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUISongChooser extends JPanel implements ActionListener,KeyListener{
	Image i = new ImageIcon(
			"C:/AngelHacks/MyMix/src/com/mixit/GUI/SongChooser.jpg").getImage();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int width = (int) screenSize.getWidth();
	public int height = (int) screenSize.getHeight();
	JTextField jtf = new JTextField("Enter Name of Song", 25);
	JTextArea jta = new JTextArea("", 50,50);
	public String finalPath="";
	String musicList="";
	File f = new File("C:/MyoMusicList");
	static String songName="aspldfasdf";
	boolean textFieldDone=false;
	
	
	int count=0;
	public GUISongChooser() {
		
		setOpaque(true);
		jtf.addActionListener(this);
	add(jtf);
	jta.setBackground(new Color(52,152,219));
		jta.setEditable(false);
		Font fa = new Font("Segoe Print", Font.BOLD, 12);
		requestFocus();
		jta.setFont(fa);
		add(jta);
	recieveInput();

		
	}
	
	public void paintComponent(Graphics g) {
		count++;
		g.drawImage(i, 0, 0, width/2, height-30, null);
		
	//	g.drawString(musicList, width/2-300, height/2-200);
	}

	public void actionPerformed(ActionEvent arg0) {
		
		songName = jtf.getText();
		textFieldDone=true;
	
		String initPath = "C:/MyoMusicList/";
		finalPath = initPath +songName +".mp3";
		GUIFirstPanel.setText();
		
		
	
	}

public String getName(){
	return songName;
}

public void listFilesForFolder(final File folder, String suffix) {
	
	try{
    for (final File fileEntry : folder.listFiles()) {
    	
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry, suffix);
        } 
        else if(fileEntry.getName().contains(suffix)){
           musicList = musicList + fileEntry.getName()+"\n";
           jta.setText(musicList);
        }
       
       
    }
    repaint();
	}
	catch(NullPointerException npe){
		System.out.println("Sorry, directory not found: " + folder);
	
	}
}
public void recieveInput(){
listFilesForFolder(f, ".mp3");
}

public void keyPressed(KeyEvent arg0) {
	// TODO Auto-generated method stub
	if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
		jtf.setEditable(false);
		
	}
		
}

public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}


}