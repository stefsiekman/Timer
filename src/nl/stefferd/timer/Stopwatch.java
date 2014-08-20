package nl.stefferd.timer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Stopwatch implements Runnable {
	
	private static final long MILLISECOND = 1000000;
	private static final long SECOND = MILLISECOND * 1000;
	private static final long MINUTE = SECOND * 60;
	private static final long HOUR = MINUTE * 60;
	private static final long DAY = HOUR * 24;
	private static final long YEAR = DAY * 365;
	
	private boolean running = false;
	private long passed = 0L;
	
	private Thread thread;
	
	private JLabel time;
	
	public Stopwatch(JPanel panel, int index) {
		JLabel name = new JLabel("#" + (index + 1));
		name.setFont(new Font("Consolas", Font.PLAIN, 70));
		panel.add(name, "center, span 1 3");
		
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onStart();
			}
		});
		panel.add(start, "center, width 100%");
		
		time = new JLabel("000:00:00:00.000000000");
		time.setFont(new Font("Consolas",
				name.getFont().getStyle(), 36));
		panel.add(time, "wrap, center, span 1 3, width 100%");
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onStop();
			}
		});
		panel.add(stop, "center, width 100%, wrap");
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onReset();
			}
		});
		panel.add(reset, "center, width 100%, wrap");
		
		thread = new Thread(this, "stopwatch-" + index);
		thread.start();
	}
	
	long lastTime = System.nanoTime();
	public void run() {
		while (true) {
			long now = System.nanoTime();
			
			if (running) {
				passed += now - lastTime;
				updateText();
			}
			
			lastTime = now;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.err.println("Could not sleep");
			}
		}
	}
	
	private void onStart() {
		running = true;
	}
	
	private void onStop() {
		running = false;
	}
	
	private void onReset() {
		passed = 0;
		updateText();
	}
	
	private void updateText() {
		String millis = leading0s(passed % SECOND, 9);
		String secs = leading0s(passed % MINUTE / SECOND, 2);
		String mins = leading0s(passed % HOUR / MINUTE, 2);
		String hours = leading0s(passed % DAY / HOUR, 2);
		String days = leading0s(passed % YEAR / DAY, 3);
		
		String s = ":";
		
		time.setText(days + s + hours + s + mins + s + secs
				+ "." + millis);
	}
	
	private String leading0s(long l, int zeros) {
		int digits = 0;
		if (l < 10)
			digits = 1;
		else if (l < 100)
			digits = 2;
		else if (l < 1000)
			digits = 3;
		else if (l < 10000)
			digits = 4;
		else if (l < 100000)
			digits = 5;
		else if (l < 1000000)
			digits = 6;
		else if (l < 10000000)
			digits = 7;
		else if (l < 100000000)
			digits = 8;
		else if (l < 1000000000)
			digits = 9;
		
		String res = "";
		for (int i = 0; i < zeros - digits; i++)
			res += "0";
		
		return res + l;
	}

}
