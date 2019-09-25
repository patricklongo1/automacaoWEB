package automacaoWEB.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DSL {
	private WebDriver driver;
	
	public DSL(WebDriver driver) {
		this.driver = driver;
	}
	
	public void escreve(String css_campo, String texto) {
		driver.findElement(By.cssSelector(css_campo)).sendKeys(texto);;
	}
	
	public void obterValorCampo(String css_campo) {
		driver.findElement(By.cssSelector(css_campo)).getAttribute("value");
	}
}
