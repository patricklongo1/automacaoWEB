package automacaoWEBTest;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import automacaoWEB.core.DSL;
import automacaoWEB.core.DriverFactory;
import bean.BeanUsuario;


public class WebAutomationTest {
	private static WebDriver driver;
	private static BeanUsuario usuarioSalvo;
	private static DSL dsl;
	
	@Before
	public void inicializa() {
		driver = DriverFactory.getDriver();
		 DSL dslInstanciado = new DSL(driver);		// Inicializa navegador maximizado
		 dsl = dslInstanciado;
	}

	@After
	public void finaliza() {
		DriverFactory.killDriver();               // Mata instancia do navegador
	}
	
	@Test
	public void initAcessarGerador() {
		dsl.acessar("https://www.fakenamegenerator.com");
		Assert.assertEquals("Generate a Random Name - Fake Name Generator", driver.getTitle()); //Assert para checkar acesso ao link
	}
	
	@Test
	public void initGerarUsuario() {
		dsl.acessar("https://www.fakenamegenerator.com");
		// Testing...
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		Assert.assertTrue(dsl.checarElementoCss("#details > div.content > div.info > div > div.address > h3")); //Assert para checkar se gerou usuario BR
	}
	
	@Test
	public void initAcessarGmail() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		// Testing...
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		Assert.assertEquals("Gmail − Armazenamento e email da Google gratuitos", driver.getTitle()); //Assert para checkar titulo do GMail
	}
	
	@Test
	public void initCriarNovaConta() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		// Testing...
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		Assert.assertEquals("Criar sua Conta do Google", driver.getTitle()); //Assert para checkar titulo da tela de cadastro do gmail
	}
	
	@Test
	public void initInsertNomeCadastro() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		// Testing...
		dsl.escreveCss("#firstName", usuarioSalvo.getNome());
		dsl.clickCss("#accountDetailsNext > span > span");
		Assert.assertFalse(dsl.checarElementoCss("#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > "
				+ "div.m6Azde.DbQnIe.OcVpRe > div:nth-child(1) > div > div.LXRPh > div.dEOOab.RxsGPe > div")); // Assert checkar erro campo nome
	}
	
	@Test
	public void initInsertSobrenomeCadastro() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		dsl.escreveCss("#firstName", usuarioSalvo.getNome());
		// Testing...
		dsl.escreveCss("#lastName", usuarioSalvo.getSobrenome());
		dsl.clickCss("#accountDetailsNext > span > span");
		Assert.assertFalse(dsl.checarElementoCss("#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > div.m6Azde.DbQnIe.OcVpRe > "
				+ "div:nth-child(2) > div > div.LXRPh > div.dEOOab.RxsGPe > div"));// Assert checkar erro campo sobrenome
	}
	
	@Test
	public void initInsertUserCadastro() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		dsl.escreveCss("#firstName", usuarioSalvo.getNome());
		dsl.escreveCss("#lastName", usuarioSalvo.getSobrenome());
		// Testing...
		dsl.escreveCss("#username", usuarioSalvo.getUsuario());
		dsl.clickCss("#accountDetailsNext > span > span");
		Assert.assertFalse(dsl.checarElementoCss("#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > div:nth-child(2) > "
				+ "div.fQxwff.cDSmF.OcVpRe > div > div.LXRPh > div.dEOOab.RxsGPe > div")); // Assert checkar erro campo Usuario
	}
	
	@Test
	public void initInsertSenhaCadastro() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		dsl.escreveCss("#firstName", usuarioSalvo.getNome());
		dsl.escreveCss("#lastName", usuarioSalvo.getSobrenome());
		dsl.escreveCss("#username", usuarioSalvo.getUsuario());
		// Testing...
		dsl.escreveCss("#passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input", usuarioSalvo.getSenha());
		dsl.clickCss("#accountDetailsNext > span > span");
		Assert.assertFalse(dsl.checarElementoCss("#passwd > div.LXRPh > div.dEOOab.RxsGPe > div")); // Assert checkar erro campo Senha
	}
	
	@Test
	public void initInsertConfirmSenhaCadastro() {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		dsl.escreveCss("#firstName", usuarioSalvo.getNome());
		dsl.escreveCss("#lastName", usuarioSalvo.getSobrenome());
		dsl.escreveCss("#username", usuarioSalvo.getUsuario());
		dsl.escreveCss("#passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input", usuarioSalvo.getSenha());
		// Testing...
		dsl.escreveCss("#confirm-passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input", usuarioSalvo.getSenha());
		dsl.clickCss("#accountDetailsNext > span > span");
		Assert.assertFalse(dsl.checarElementoCss("#confirm-passwd > div.LXRPh > div.dEOOab.RxsGPe > div")); // Assert checkar erro campo Confirmar Senha 
	}
	
	@Test
	public void initInsertTelefoneCadastro() throws Exception {
		dsl.acessar("https://www.fakenamegenerator.com");
		dsl.escreveId("n", "Brazil");
		dsl.escreveId("c", "Brazil");
		dsl.clickId("genbtn");
		salvarDadosUsuario();
		dsl.acessar("https://www.google.com/intl/pt/gmail/about/#");
		dsl.clickCss("body > div.hercules-header.h-c-header.h-c-header--product-marketing-one-tier.header--desktop > div.h-c-header__bar > div.h-c-header__cta > "
				+ "ul.h-c-header__cta-list.header__nav--ltr > li.h-c-header__cta-li.h-c-header__cta-li--primary > a");
		controlarNovaAba(driver, driver.getWindowHandle());
		dsl.escreveCss("#firstName", usuarioSalvo.getNome());
		dsl.escreveCss("#lastName", usuarioSalvo.getSobrenome());
		dsl.escreveCss("#username", usuarioSalvo.getUsuario());
		dsl.escreveCss("#passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input", usuarioSalvo.getSenha());
		dsl.escreveCss("#confirm-passwd > div.aCsJod.oJeWuf > div > div.Xb9hP > input", usuarioSalvo.getSenha());
		dsl.clickCss("#accountDetailsNext > span > span");
		Thread.sleep(3000);
		if (elementoExiste(
				"#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > div:nth-child(2) > div.fQxwff.cDSmF.OcVpRe > div > div.LXRPh > div.dEOOab.RxsGPe > div",
				driver)
				|| elementoExiste(
						"#view_container > form > div.mbekbe.bxPAYd > div > div.RCum0c > div:nth-child(2) > div:nth-child(2) > div",
						driver)) {
			dsl.limparCampoId("username");
			Thread.sleep(1000);
			dsl.escreveId("username", "xx" + usuarioSalvo.getUsuario() + "xx1");
			dsl.clickCss("#accountDetailsNext > span > span");
			Thread.sleep(3000);
			// Testing...
			dsl.escreveCss("#phoneNumberId", "99999");
			dsl.clickCss("#gradsIdvPhoneNext > span > span");
			Thread.sleep(1000);
		} else {
			// Testing...
			dsl.escreveCss("#phoneNumberId", "99999");
			dsl.clickCss("#gradsIdvPhoneNext > span > span");
			Thread.sleep(1000);
		}
		Assert.assertNotEquals("Este formato de número de telefone não é válido. Verifique o país e o número."
				, dsl.valorDoCampoCss("#view_container > form > div.mbekbe.bxPAYd > div > div.lqByzd.OcVpRe > "
						+ "div.qqYQWe.G2JKS > div.rFrNMe.RSJo4e.uIZQNc.og3oZc."   // Assert checkar erro campo Telefone (Falha induzida)
						+ "zKHdkd.sdJrJc.Tyc9J.CDELXb.k0tWj.IYewr > div.LXRPh > div.dEOOab.RxsGPe"));
	}
	
	// Metodo para injetar dados no objeto usuário.
	public static void salvarDadosUsuario() {
		try {
			BeanUsuario user = new BeanUsuario();
			String nome = driver.findElement(By.cssSelector("#details > div.content > div.info > div > div.address > h3")).getText();
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
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Verificar elementos da página!");
		}
	}
	
	// Metodo para remover caracteres especiais dos nomes para formatar usuário/senha
	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
	
	// Metodo para controlar nova aba no browser
	public static void controlarNovaAba(WebDriver driver, String oldTab) {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}
	
	// Metodo para verificar se elemento existe na página
	private static boolean elementoExiste(String cssSelector, WebDriver driver) throws Exception {
		boolean elemento = driver.findElement(By.cssSelector(cssSelector)).isDisplayed();
		return elemento;
	}
}
