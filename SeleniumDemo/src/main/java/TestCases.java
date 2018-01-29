/**
 * This JUnit Script File, Testing Wishlist and Login Functionality in 8 Stages.
 */

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//Fixing the run order in JUnit 4
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCases {


    public static WebDriver driver;
    //This variable will be used for storing selected product name since it can change depending to time.
    public static String productName;

    //setUp() Method Will be Run Once At the Beginning of Class
    @BeforeClass
    public static void setUp(){

        //Referencing chromedriver.exe
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();

    }

    //Checking the title to verify Home Page Loading event.
    @Test
    public void stage1_homePageLoad(){

        driver.get("http://www.amazon.com");
        Assert.assertTrue(driver.getTitle().equals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more"));
    }


    //Completing Login Step
    @Test
    public void stage2_login(){

        WebElement signIn = driver.findElement(By.linkText("Sign in")); //Finds the Element which has "Sign in" LinkText

        signIn.click();

        WebElement emailField = driver.findElement(By.id("ap_email")); //Finds the E-Mail field which is identified by "ap_email"

        emailField.sendKeys("testaccinsider@gmail.com"); //Giving the e-mail address of test account.

        WebElement passwordField = driver.findElement(By.id("ap_password")); //Finds the Password field which is identified by "ap_password"

        passwordField.sendKeys("TestAccInsider44?"); //Giving the password of test account.


        WebElement login = driver.findElement(By.id("signInSubmit")); //Find the button which has identified by "signInSubmit"

        login.click();

        Assert.assertFalse(driver.getTitle().equals("Amazon Sign In")); //If login fails, the title remains same. So test will be failed.
    }

    @Test
    public void stage3_searchSamsung(){

        WebElement searchBar = driver.findElement(By.id("twotabsearchtextbox")); //Finds the search bar on main page

        searchBar.sendKeys("samsung"); //Giving input as "samsung"
        searchBar.submit();


        Assert.assertFalse(driver.getPageSource().contains("did not match any products.")); //Test fails if there is no result for search query "samsung"
    }

    @Test
    public void stage4_goToPage2(){

        WebElement pageTwo = driver.findElement(By.xpath("//*[@id=\"pagn\"]/span[3]/a")); //Going the second page of result by xpath

        pageTwo.click();

        Assert.assertTrue(driver.getTitle().equals("Amazon.com: samsung"));

    }

    @Test
    public void stage5_selectThirdItem(){

        WebElement thirdProduct = driver.findElement(By.xpath("//*[@id=\"result_18\"]/div/div/div/div[2]/div[1]/div[1]/a/h2")); //Selecting the 3rd result on the 2nd page

        productName = thirdProduct.getText(); //Storing name of the product to compare later

        thirdProduct.click();

        Assert.assertTrue(driver.getTitle().contains(productName)); //Check the page title, if its the selected product.

    }
    @Test
    public void stage6_addToWishList(){

        WebElement addToList = driver.findElement(By.id("add-to-wishlist-button-submit")); //Clicks the Add to Wishlish button.
        addToList.click();
        driver.get("http://www.amazon.com");
    }


    @Test
    public void stage7_checkWishList(){
        WebElement dropDownMenu = driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")); //Finding the dropdown menu container

        Actions builder = new Actions(driver);
        builder.moveToElement(dropDownMenu).build().perform(); //Hover on the dropdown menu
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"nav-flyout-wl-items\"]/div/a[1]"))); //Waiting for the Wish List link
        WebElement wishListButton = driver.findElement(By.xpath("//*[@id=\"nav-flyout-wl-items\"]/div/a[1]")); //Find the Wish List link
        wishListButton.click();

        Assert.assertTrue(driver.getPageSource().contains(productName)); //If stored product was added to wishlist, Test succeeds.

    }

    @Test
    public void stage8_deleteWishListItem(){

        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"a-autoid-5\"]/span/input")); //Delete the item from wishlist.
        deleteButton.click();

        Assert.assertFalse(driver.getPageSource().contains("Deleted")); //Check if the item deleted from wishlist.

    }


}
