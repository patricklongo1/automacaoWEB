package automacaoWEBTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import automacaoWEB.core.DSL;
import automacaoWEB.core.DriverFactory;


public class WebAutomationTest {
	private WebDriver driver;
	private DSL dsl = new DSL(driver);
	
	@Before
	public void inicializa() {
		//driver = DriverFactory.getDriver().get("https://www.fakenamegenerator.com");;
	}

	@After
	public void finaliza() {
		DriverFactory.killDriver();
	}
	
	
	@Test
	public void initGerarUsuario() {
		driver.findElement(By.id("n")).sendKeys("Brazil");
		driver.findElement(By.id("c")).sendKeys("Brazil");
		driver.findElement(By.id("genbtn")).click();
		//salvarDadosUsuario();
	}
	
}
