package com.dv.app;

import javax.swing.ImageIcon;

public class Resource {
	
	public static ImageIcon getResource(Class name, String loc) {
		return new ImageIcon(name.getResource(loc));
	}

}
