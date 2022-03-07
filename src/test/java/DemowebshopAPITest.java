import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DemowebshopAPITest {

    @Test
    void addToCartTest() {
        String document = given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("Nop.customer=16d5f2e3-1840-400f-9e7a-cf319a2733fd;")
                .when()
                .get("http://demowebshop.tricentis.com")
                .then()
                .extract().asString();

        String cartCty = document.substring(document.lastIndexOf("cart-qty") + 11, document.indexOf("span", document.lastIndexOf("cart-qty")) - 3);
        int countCart = Integer.parseInt(cartCty) + 1;

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=16d5f2e3-1840-400f-9e7a-cf319a2733fd;")
                .body("addtocart_31.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/catalog/31/1/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is(String.format("(%s)", countCart)));
    }
}