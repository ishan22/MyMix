package com.mixit.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GUICombiner extends JPanel{
	public GUICombiner(){
		setLayout(new GridLayout(1,2));

		GUISongChooser gsc = new GUISongChooser();
		GUIFirstPanel gfp = new GUIFirstPanel();

		add(gsc);
		add(gfp);
		
		
		
	}
}
