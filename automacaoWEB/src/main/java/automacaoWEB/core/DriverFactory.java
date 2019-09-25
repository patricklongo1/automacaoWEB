package automacaoWEB.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory { // Classe Singleton para instanciar objeto WebDriver apenas 1 vez.
	private static WebDriver driver;
	
	private DriverFactory() {
	}
	
	// Instancia objeto
	public static WebDriver getDriver() {
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}
	
	// Destroi objeto
	public static void killDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
