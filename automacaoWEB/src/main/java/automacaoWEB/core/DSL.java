package automacaoWEB.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.google.common.eventbus.Subscribe;

public class DSL {
	private WebDriver driver;
	
	public DSL(WebDriver driver) {
		this.driver = driver;
	}
	
	public void escreveId(String id_campo, String texto) {
		driver.findElement(By.id(id_campo)).sendKeys(texto);;
	}
	
	public void acessar(String url) {
		driver.get(url);
	}
	
	public void escreveCss(String css_campo, String texto) {
		driver.findElement(By.cssSelector(css_campo)).sendKeys(texto);
	}
	
	public void clickId(String id_campo) {
		driver.findElement(By.id(id_campo)).click();
	}
	
	public void clickCss(String css_campo) {
		driver.findElement(By.cssSelector(css_campo)).click();
	}
	
	public void obterValorCampo(String css_campo) {
		driver.findElement(By.cssSelector(css_campo)).getAttribute("value");
	}
	
	public boolean checarElementoCss(String css_campo) {
		try {
			driver.findElement(By.cssSelector(css_campo));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String valorDoCampoCss(String css_campo) {
		String str = driver.findElement(By.cssSelector(css_campo)).getText();
		return str;
	}
	
	public void limparCampoId(String id_campo) {
		driver.findElement(By.id(id_campo)).clear();
	}
}
