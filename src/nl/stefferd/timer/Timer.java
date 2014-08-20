package nl.stefferd.timer;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

public class Timer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public Timer() {
		setLayout(new MigLayout("", "[90px!][]", ""));
		new Stopwatch(this, 0);
		new Stopwatch(this, 1);
		new Stopwatch(this, 2);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Could not load system's look and feel");
		}
		
		JFrame frame = new JFrame("Timer");
		frame.setResizable(false);
		
		Timer panel = new Timer();
		frame.add(panel);
		frame.pack();
		
		try {
			frame.setIconImage(ImageIO.read(ClassLoader.getSystemResource("icon-small.png")));
		} catch (IOException e) {
			System.err.println("Could not load icon: " + e.getMessage());
		}
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}
	
}
