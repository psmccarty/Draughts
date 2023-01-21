import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;


public class Menu extends JFrame{

	private JButton playButton;
	private String ICONPATH = "images/checkersIcon.png";
	private String LOGO = "images/menuLogo.png";

	public Menu(){

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ImageIcon image = new ImageIcon(ICONPATH);
		this.setIconImage(image.getImage());
		this.setLayout(null);
		this.setTitle("Draughts");
		this.setResizable(false);
		this.getContentPane().setBackground(Color.red);

		JLabel label = new JLabel();
		label.setBounds(105, 0, 1340, 312);
		ImageIcon logo = new ImageIcon(LOGO);
		label.setIcon(logo);


		this.playButton = new JButton("Play");
		playButton.setBounds(700, 512, 150, 50);
		playButton.setFont(new Font("Public Pixel", Font.PLAIN, 20));
		playButton.setBackground(new Color(0xD62E21));
		playButton.setForeground(Color.black);
		playButton.setBorder(BorderFactory.createEtchedBorder());
		playButton.addActionListener(e -> {
			this.dispose();
			new GameRunner(new GameLogic(new CheckersBoard()));
		});
		playButton.setFocusable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(label);
		this.add(playButton);
		System.out.println(this.getSize());
	}
}
