package automacaoWEB;

import java.text.Normalizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import bean.BeanUsuario;

public class WebAutomation {
	private static WebDriver driver;
	private static BeanUsuario usuarioSalvo;

	public static void main(String[] args) {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
			WebDriver navegador = new ChromeDriver();
			navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			navegador.manage().window().maximize();
			navegador.get("https://www.google.com/");
			driver = navegador;
			acessarGeradorDeUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Verificar instalação/versões do Navegador e WebDriver!");
		}
	}

	
	
	public static void acessarGeradorDeUsuario() {
		try {
			driver.get("https://www.fakenamegenerator.com/");
			gerarUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Verifique o link informado!");
		}

	}

	public static void gerarUsuario() {
		try {
			driver.findElement(By.id("n")).sendKeys("Brazil");
			driver.findElement(By.id("c")).sendKeys("Brazil");
			driver.findElement(By.id("genbtn")).click();
			salvarDadosUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Verificar elementos da página!");
		}
	}

	public static void salvarDadosUsuario() {
		try {
			BeanUsuario user = new BeanUsuario();
			String nome = driver
					.findElement(By.cssSelector("#details > div.content > div.info > div > div.address > h3"))
					.getText();
			String[] splitNome = nome.split(" ");
			user.setNome(splitNome[0].replace("´", ""));
			user.setSobrenome(splitNome[2]);
			String usuario = deAccent(splitNome[0]) + deAccent(splitNome[2]);
			user.setUsuario(usuario.toLowerCase());
			user.setSenha(user.getUsuario() + ".123");
			user.setTelefone(driver
					.findElement(By
							.cssSelector("#details > div.content > div.info > div > div.extra > dl:nth-child(5) > dd"))
					.getText());
			usuarioSalvo = user;
			acessarGmail();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Verificar elementos da página!");
		}
	}

	public static void acessarGmail() {
		try {
			driver.get("https://www.google.com/intl/pt/gmail/about/#");
			cadastrarUserNoGmail();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Verifique o link informado!");
		}
	}

	public static void cadastrarUserNoGmail() {
		try {
			driver.findElement(By.cssSelector(
					"body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
							+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a"))
					.click();
			Thread.sleep(3000);
			controlarNovaAba(driver, driver.getWindowHandle());
			driver.findElement(By.id("firstName")).sendKeys(usuarioSalvo.getNome());
			driver.findElement(By.id("lastName")).sendKeys(usuarioSalvo.getSobrenome());
			driver.findElement(By.id("username")).sendKeys(usuarioSalvo.getUsuario());
			driver.findElement(By.cssSelector("#passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input"))
					.sendKeys(usuarioSalvo.getSenha());
			driver.findElement(By.cssSelector("#confirm-passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input"))
					.sendKeys(usuarioSalvo.getSenha());
			driver.findElement(By.cssSelector("#accountDetailsNext > span")).click();
			Thread.sleep(3000);
			driver.findElement(By.cssSelector("#yDmH0d")).sendKeys("99999");
			driver.findElement(By.cssSelector("#accountDetailsNext > span")).click();

			if (elementoExiste(
					"#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > div:nth-child(2) > div.fQxwff.cDSmF.OcVpRe > div > div.LXRPh > div.dEOOab.RxsGPe > div",
					driver)
					|| elementoExiste(
							"#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > div:nth-child(2) > div:nth-child(2) > div",
							driver)) {
				Thread.sleep(3000);
				driver.findElement(By.id("username")).clear();
				Thread.sleep(1000);
				driver.findElement(By.id("username")).sendKeys("xx" + usuarioSalvo.getUsuario() + "xx1");
				driver.findElement(By.cssSelector("#accountDetailsNext > span > span")).click();
				driver.findElement(By.id("phoneNumberId")).sendKeys("99999");
				driver.findElement(By.cssSelector("#accountDetailsNext > span")).click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public static void controlarNovaAba(WebDriver driver, String oldTab) {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	private static boolean elementoExiste(String cssSelector, WebDriver driver) throws Exception {
		boolean elemento = driver.findElement(By.cssSelector(cssSelector)).isDisplayed();
		return elemento;
	}
}
