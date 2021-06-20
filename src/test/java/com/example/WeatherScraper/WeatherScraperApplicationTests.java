package com.example.WeatherScraper;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class WeatherScraperApplicationTests {
	static {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\prana\\Downloads\\chromedriver_win32\\chromedriver.exe");
	}

	WebDriver driver = new ChromeDriver();
	String url = "http://localhost:8080/weatherScraper";

	@Test
	void test() {
		driver.get(url);
		String expectedTitle = "WeatherScraper";
		String actualTitle = driver.getTitle();
		assertEquals(expectedTitle, actualTitle);
		driver.close();
	}

	@Test
	void test1() {
		driver.get(url);
		assertTrue(driver.findElement(By.name("place")).isDisplayed());
		assertTrue(driver.findElement(By.name("submit")).isDisplayed());
		assertTrue(driver.findElement(By.className("jumbotron")).isDisplayed());
		assertTrue(driver.findElement(By.name("submit")).isEnabled());
		String text = driver.findElement(By.id("a")).getText();
		assertEquals("Welcome to the Weather Scraper Website",text);
		driver.close();
	}
	@Test
	void test2() {
		driver.get(url);
		driver.findElement(By.name("submit")).click();
		String text = driver.findElement(By.id("jj")).getText();
		assertEquals("Please enter a city name",text);
		driver.close();
	}
	@Test
	//Testing for an invalid user entry like an invalid city name
	void test3() {
		driver.get(url);
		driver.findElement(By.name("place")).sendKeys("DHOLAKPUR");
		driver.findElement(By.name("submit")).click();
		String text = driver.findElement(By.id("jj")).getText();
		assertEquals("Please enter a valid City",text);
		driver.close();
	}
	@Test
	//Testing a valid output like Seattle
	void test4(){
		driver.get(url);
		driver.findElement(By.name("place")).sendKeys("seattle");
		driver.findElement(By.name("submit")).click();
		String text = driver.findElement(By.id("kk")).getText();
		assertEquals("Today's weather:\n" +
				"Description: few clouds\n" +
				"Temperature: 296.28 Fahrenheit\n" +
				"Max Temperature: 299.39 Fahrenheit\n" +
				"Minimum Temperature: 293.53 Fahrenheit\n" +
				"Pressure: 1017 Pascals\n" +
				"Humidity: 63",text);
	}
	@Test
	//Testing if the Application is Case Sensitive
	void test5(){
		driver.get(url);
		driver.findElement(By.name("place")).sendKeys("SEATTLE");//all upper case
		driver.findElement(By.name("submit")).click();
		String text = driver.findElement(By.id("kk")).getText();
		assertEquals("Today's weather:\n" +
				"Description: few clouds\n" +
				"Temperature: 296.28 Fahrenheit\n" +
				"Max Temperature: 299.39 Fahrenheit\n" +
				"Minimum Temperature: 293.53 Fahrenheit\n" +
				"Pressure: 1017 Pascals\n" +
				"Humidity: 63",text);
		driver.navigate().refresh();
		driver.get(url);
		driver.findElement(By.name("place")).sendKeys("seATTlE");//all upper case
		driver.findElement(By.name("submit")).click();
		String text1 = driver.findElement(By.id("kk")).getText();
		assertEquals("Today's weather:\n" +
				"Description: few clouds\n" +
				"Temperature: 296.28 Fahrenheit\n" +
				"Max Temperature: 299.39 Fahrenheit\n" +
				"Minimum Temperature: 293.53 Fahrenheit\n" +
				"Pressure: 1017 Pascals\n" +
				"Humidity: 63",text);
				driver.close();
	}
}
