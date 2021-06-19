package snakeProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class RadiPanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 1300;
	static final int SCREEN_HEIGHT = 750;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	static final int SPEED = 175;
	final int familyX[] = new int[GAME_UNITS];
	final int familyY[] = new int[GAME_UNITS];
	int familyMembers = 1;
	int kittensAdopted;
	int kittenX;
	int kittenY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	static boolean gameOn = false;
	static boolean gameOver = false;
	
	ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("cat.png"));
	ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("RadiPixels.jpg"));
	ImageIcon icon3 = new ImageIcon(getClass().getClassLoader().getResource("cat2.png"));

	RadiPanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(246, 204, 255));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(SPEED, this);
		timer.start();
	}

	public void pause() {
		RadiPanel.gameOn = true;
		timer.stop();
	}

	public void resume() {
		RadiPanel.gameOn = false;
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {

			g.drawImage(icon.getImage(), kittenX, kittenY, this);

			for (int i = 0; i < familyMembers; i++) {
				if (i == 0) {
					g.drawImage(icon2.getImage(), familyX[i], familyY[i], this);
				} else {
					g.drawImage(icon3.getImage(), familyX[i], familyY[i], this);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Monospaced bold", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Kittens adopted: " + kittensAdopted,
					(SCREEN_WIDTH - metrics.stringWidth("Kittens adopted: " + kittensAdopted)) / 2,
					g.getFont().getSize());
		} else {
			gameOver(g);
			gameOver = true;
		}

	}

	public void newApple() {
		kittenX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		kittenY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		for (int i = familyMembers; i > 0; i--) {
			familyX[i] = familyX[i - 1];
			familyY[i] = familyY[i - 1];
		}

		switch (direction) {
		case 'U':
			familyY[0] = familyY[0] - UNIT_SIZE;
			break;
		case 'D':
			familyY[0] = familyY[0] + UNIT_SIZE;
			break;
		case 'L':
			familyX[0] = familyX[0] - UNIT_SIZE;
			break;
		case 'R':
			familyX[0] = familyX[0] + UNIT_SIZE;
			break;
		}

	}

	public void checkKitten() {
		if ((familyX[0] == kittenX) && (familyY[0] == kittenY)) {
			familyMembers++;
			kittensAdopted++;
			newApple();
		}
	}

	public void crash() {
		// проверява дали главата се удря в тялото
		for (int i = familyMembers; i > 0; i--) {
			if ((familyX[0] == familyX[i]) && (familyY[0] == familyY[i])) {
				running = false;
			}
		}
		// проверява дали главата се удря в лявата граница
		if (familyX[0] < 0) {
			familyX[0] = SCREEN_WIDTH - UNIT_SIZE;
			
		}
		// проверява дали главата се удря в дясната граница
		if (familyX[0] > SCREEN_WIDTH) {
			familyX[0] = 0;
			
		}
		// проверява дали главата се удря в горната граница
		if (familyY[0] < 0) {
			familyY[0] = SCREEN_HEIGHT - UNIT_SIZE;
			
		}
		// проверява дали главата се удря в долната граница
		if (familyY[0] > SCREEN_HEIGHT) {
			familyY[0] = 0;
			
		}

		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		// колко котета са осиновени

		g.setColor(Color.red);
		g.setFont(new Font("Monospaced Bold", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Kittens adopted: " + kittensAdopted,
				(SCREEN_WIDTH - metrics1.stringWidth("Kittens adopted: " + kittensAdopted)) / 2, g.getFont().getSize());
		// надпис "Sorry, too many kittens adopted :("
		g.setColor(Color.red);
		g.setFont(new Font("Monospaced Bold", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());		
		
		g.drawString("Sorry, too many kittens adopted ;(",
				(SCREEN_WIDTH - metrics2.stringWidth("Sorry, too many kittens adopted ;(")) / 2, SCREEN_HEIGHT / 2);
	}
		

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkKitten();
			crash();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {

			case KeyEvent.VK_ENTER:
				if (gameOver == true) {
					kittensAdopted = 0;
					familyMembers = 1;
					familyX[0] = 0;
					familyY[0] = 0;
					gameOver = false;
					running = true;
					direction = 'R';
					startGame();
					repaint();
				}
				break;
			case KeyEvent.VK_SPACE:
				if (RadiPanel.gameOn) {
					resume();
				} else {
					pause();
				}
				break;
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}