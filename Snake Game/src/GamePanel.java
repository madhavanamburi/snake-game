import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GamePanel extends JPanel implements ActionListener{

	int screenWidth = 600;
	int screenHeight = 600;
	int delay = 75;
	int unitSize = 25;
	int gameSize = (screenWidth*screenHeight)/unitSize;
	int[] x = new int[gameSize];
	int[] y = new int[gameSize];
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX = 0;
	int appleY = 0;
	char direction = 'R';
	boolean running = false;
	Timer timer = new Timer(delay, this);;
	Random random = new Random();;
	
	
	GamePanel(){
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
		
	}

	public void startGame() {
		
		newApple();
		running = true;
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
	
		if(running) {
			
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, unitSize, unitSize);
			
			for(int i=0; i<bodyParts; i++) {
				
				if(i == 0) {
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], unitSize, unitSize);
				}
				else {
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], unitSize, unitSize);

				}
			}
			
			g.setColor(Color.RED);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (screenWidth - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
			
		}
		
		else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {
	
		appleX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
		appleY = random.nextInt((int)(screenHeight/unitSize))*unitSize;

	}
	
	public void move() {
			
			for(int i=bodyParts; i>0; i--) {
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			
			switch(direction) {
			case 'R':
				x[0] = x[0] + unitSize;
				break;
			case 'L':
				x[0] = x[0] - unitSize;
				break;
			case 'U':
				y[0] = y[0] - unitSize;
				break;
			case 'D':
				y[0] = y[0] + unitSize;
				break;
			}
		
	}

	public void checkApple() {
		
		if((x[0] == appleX) && (y[0] == appleY)) {
			
			newApple();
			applesEaten++;
			bodyParts++;
		}
		
	}
	
	public void checkCollision() {
	
//		checks if head collides with body
		for(int i=bodyParts; i>0; i--) {
			
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
//		checks if head collides with left border
		if(x[0] < 0) {
			running = false;
		}
		
//		checks if head collides with right border
		if(x[0] >= screenWidth) {
			running = false;
		}
		
//		checks if head collides with top border
		if(y[0] < 0) {
			running = false;
		}
		
//		checks if head collides with bottom border
		if(y[0] >= screenHeight) {
			running = false;
		}
		
		
	}
	
	public void gameOver(Graphics g) {
		
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (screenWidth - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight/2);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		else {
			timer.stop();

		}
		repaint();
		
	}
	
	
	public class MyKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
		
		
	}
	
	
	
}
