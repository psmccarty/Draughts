import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Component;


/**
 * Main menu for the application. Gives the player options on the mode
 */
public class Menu{

	JFrame frame;
	JButton p1;
	JButton p2;
	JLabel title;

	public Menu(){
		frame = new JFrame();

		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		p1 = new JButton("1 Player");
		p1.setAlignmentX(Component.CENTER_ALIGNMENT);

		p2 = new JButton("2 Players");
		p2.addActionListener(e -> {
			frame.dispose();
			new CheckersBoard();
		});
		p2.setAlignmentX(Component.CENTER_ALIGNMENT);

		title = new JLabel("Game Modes");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("haha");
		frame.setSize(420, 420);

		frame.add(title);
		frame.add(p1);
		frame.add(p2);

		frame.setVisible(true);



	}



}
