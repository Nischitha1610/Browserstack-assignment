DesiredCapabilities caps = new DesiredCapabilities();
caps.setCapability("browserName", "chrome");
caps.setCapability("os", "Windows");
caps.setCapability("os_version", "10");
caps.setCapability("browser_version", "latest");

// Define other capabilities based on your desired browser and OS combination
String browserstackURL = "https://<username>:<access-key>@hub-cloud.browserstack.com/wd/hub";
WebDriver driver = new RemoteWebDriver(new URL(browserstackURL), caps);
