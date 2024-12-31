import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

import java.util.List;
import java.io.*;
import java.net.*;

public class ElPaisScraper {
    public static void main(String[] args) {
        // Initialize the WebDriver (Assume Chrome is installed)
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=es");  // Ensuring Spanish language
        WebDriver driver = new ChromeDriver(options);
        
        try {
            // Open the El País website
            driver.get("https://elpais.com");

            // Wait for the page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("header")));

            // Navigate to the Opinion section
            WebElement opinionSection = driver.findElement(By.xpath("//a[@href='/opinion/']"));
            opinionSection.click();

            // Wait for the opinion section to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section.opinion")));

            // Get the first 5 articles in the Opinion section
            List<WebElement> articles = driver.findElements(By.cssSelector("article h2 a"));
            for (int i = 0; i < 5; i++) {
                String articleTitle = articles.get(i).getText();
                String articleLink = articles.get(i).getAttribute("href");
                System.out.println("Title in Spanish: " + articleTitle);

                // Fetch the article content and print it
                driver.get(articleLink);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.entry-content")));

                String articleContent = driver.findElement(By.cssSelector("div.entry-content")).getText();
                System.out.println("Content in Spanish: " + articleContent);

                // If available, download and save the cover image
                List<WebElement> imageElements = driver.findElements(By.cssSelector("article img"));
                if (!imageElements.isEmpty()) {
                    String imageUrl = imageElements.get(0).getAttribute("src");
                    saveImage(imageUrl, "article_" + i + "_image.jpg");
                }

                System.out.println("---------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void saveImage(String imageUrl, String fileName) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(fileName);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
        System.out.println("Image saved as " + fileName);
    }
}
