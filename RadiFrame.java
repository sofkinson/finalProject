package snakeProject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RadiFrame extends JFrame{

	RadiPanel game = new RadiPanel();	
	ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("cat3.png"));
	
	RadiFrame(){
		this.add(game);
		this.setTitle("Adoption Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(icon.getImage());

		
	}
	
	
}