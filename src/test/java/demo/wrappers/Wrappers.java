package demo.wrappers;

import java.util.*;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    WebDriver driver;
    WebDriverWait wait;
    public Wrappers(WebDriver driver , WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //Open URL
    public void openURL(String URL){
        driver.get(URL);
    }

    //Search for product
    public void searchWrapper(By locator , String productName){
        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        searchButton.sendKeys(productName,Keys.ENTER);

    }

    public void clickWrapper(By locator){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        button.click();
    }

    //Get all the product on current page
    public List<WebElement> getAllProducts(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='yKfJKb row']")));
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
        return products;
    }

    //Get the count of items with rating less than or equal to 4 stars.
    public int getCountOfWashingMachineWithRatingAtMostFourStars() throws InterruptedException {
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
//        wait.until(ExpectedConditions.urlContains("popularity"));
        int count =0;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='yKfJKb row']")));
            List<WebElement> allProducts = getAllProducts();
            //List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
            //List<WebElement> ratings = driver.findElements(By.xpath("//div[@class='yKfJKb row']//span[contains(@id,'productRating')]/div"));
            for (WebElement element : allProducts) {
                 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='yKfJKb row']//span[contains(@id,'productRating')]/div")));
                //String str = productRating.getText();
          WebElement productRating = element.findElement(By.xpath(".//span[contains(@id,'productRating')]/div"));
          String str = productRating.getText();
                Double rating = Double.parseDouble(str);
                //System.out.println(rating);
                if (0<rating && rating<= 4) {
                    count++;
                }
            }
        }catch (Exception e){
            System.out.println("Exception occurred: " + e.getClass().getName());
            System.out.println("Testcase failed : Checking product ratings");
        }
        return count;
    }

    public void printItemsWithMoreThan17PercentDiscount(){
        HashMap<String,Integer> map = new HashMap<>();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='yKfJKb row']")));
        List<WebElement> allProducts = getAllProducts();
        //List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
        for (WebElement element : allProducts) {
            WebElement productTile = element.findElement(By.xpath(".//div[@class='KzDlHZ']"));
            String  titleText = productTile.getText();
            WebElement discountPercent = element.findElement(By.xpath(".//div[@class='UkUFwK']/span"));
            String[] splitDiscountPercent = discountPercent.getText().split("% ");
            Integer discount = Integer.parseInt(splitDiscountPercent[0]);
            map.put(titleText,discount);
        }

        Iterator<Map.Entry<String,Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String ,Integer> entry = iterator.next();
            if(entry.getValue()>17){
                System.out.println("Product title: "+ entry.getKey() + ", Product discount : " + entry.getValue());
            }
        }

    }

    public void selectCheckboxOption(String value, By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        List<WebElement> checkboxOptions = driver.findElements(locator);
        //((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",checkboxOptions);
        for(WebElement element : checkboxOptions){
            if(element.getText().trim().contains(value)){
                element.click();
                break;
            }
        }
    }

    public void printTop5ReviewedItems(){
        List<WebElement> allCoffeMugResults = driver.findElements(By.xpath("//div[contains(@data-id,'MUG')]"));
        List<Product> productList = new ArrayList<>();

        for(WebElement element:allCoffeMugResults){
            WebElement titleElement = element.findElement(By.xpath(".//a[@class='wjcEIp']"));
            String title = titleElement.getText();

            WebElement reviewCountElement = element.findElement(By.xpath(".//span[@class='Wphh3N']"));
            String reviewCount = reviewCountElement.getText();
            int count = Integer.parseInt(reviewCount.substring(1,reviewCount.length()-1).replace(",",""));
            //System.out.println(count);

            WebElement imageURL = element.findElement(By.xpath(".//img[@class='DByuf4']"));
            String url = imageURL.getAttribute("src");

            Product product = new Product(title,count , url);
            productList.add(product);
        }

        Collections.sort(productList);

        for(int i=0;i<5;i++){
            Product product = productList.get(i);
            System.out.println("Product title : "+product.title+" || Product URL: "+ product.url);
        }

    }

    public static List<String> getTopFiveKeys(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
