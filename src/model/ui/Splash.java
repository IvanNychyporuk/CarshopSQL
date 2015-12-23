package model.ui;

import java.awt.*;

public class Splash {
	
	public static void startSplash () throws InterruptedException{
		SplashScreen splash = SplashScreen.getSplashScreen();
//		Thread.sleep(5000);
		
		Graphics2D g = splash.createGraphics();
		g.setColor(Color.WHITE);
		for (int i = 0; i < 301; i++) {
			g.fillRect(23, 160, i, 10);
			Thread.sleep(1);
			splash.update();
		}




//		Thread.sleep(5000);


		splash.close();
	}

	public static void main(String[] args)throws Exception {
		startSplash();
	}

}
