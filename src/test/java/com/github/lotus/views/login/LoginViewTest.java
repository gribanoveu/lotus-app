package com.github.lotus.views.login;

import com.github.lotus.security.AuthenticatedUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
class LoginViewTest extends BaseBrowserTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void login() throws InterruptedException {
        webDriver.get("http://localhost:" + serverPort);
        Thread.sleep(5000);
        webDriver.findElement(By.name("username")).click();
        webDriver.findElement(By.name("username")).sendKeys("admin");
        webDriver.findElement(By.name("password")).click();
        webDriver.findElement(By.name("password")).sendKeys("admin");
        webDriver.findElement(By.xpath("//vaadin-button[contains(text(),'Log in')]")).click();
        Thread.sleep(10000);

    }
}