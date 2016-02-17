package main;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;

import twitter4j.TwitterException;

public class Main extends Thread {

	private final int SECONDS_DELAY = 36;

	public static void main(String[] args) {
		new Main();
	}

	private boolean stop = false;

	public Main() {

		start();

		JOptionPane.showMessageDialog(null, "QuoteGen is now running\nPress OK to stop");

		stop = true;
	}

	@Override
	public void run() {

		System.out.println("Starting");

		while (!stop) {

			postQuote();

			keepComputerAwake();

			try {
				Thread.sleep(SECONDS_DELAY * 1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void postQuote() {

		String quote = SentenceGenerator.generateSentence();

		while (quote.length() > 140) {
			quote = SentenceGenerator.generateSentence();
		}

		try {
			TweetFunctions.tweet(quote);

			for (int i = 0; i < 150; i++) {
				quote += " ";
			}

			quote = quote.substring(0, 145);

			writeToLog("Successfuly updated to: \"" + quote + "\"");
		} catch (TwitterException e) {

			for (int i = 0; i < 150; i++) {
				quote += " ";
			}

			quote = quote.substring(0, 145);

			writeToLog("Failed to Post: \"" + quote + "\" BECAUSE: " + e.getMessage());
		}
	}

	private int mouseX, mouseY;

	private void writeToLog(String message) {

		FileWriter writer = null;

		try {
			writer = new FileWriter("C:/Users/Anyone/Desktop/QuoteGenLog.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter bufferedWriter = new BufferedWriter(writer);

		Date cur = new Date();

		String toWrite = "At : " + cur.toString() + " : " + message;

		try {
			bufferedWriter.write(toWrite);
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Robot robot;

	private void keepComputerAwake() {

		int toMoveX = (int) (Math.random() * 100);
		int toMoveY = (int) (Math.random() * 100);

		Point newMouse = MouseInfo.getPointerInfo().getLocation();

		if (newMouse.x != mouseX || newMouse.y != mouseY) {
			mouseX = newMouse.x;
			mouseY = newMouse.y;
			return;
		}

		if (robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}

		robot.mouseMove(toMoveX, toMoveY);

		mouseX = toMoveX;
		mouseY = toMoveY;

	}
}
