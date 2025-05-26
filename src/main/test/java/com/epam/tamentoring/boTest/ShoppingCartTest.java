package com.epam.tamentoring.boTest;

import com.epam.tamentoring.bo.Product;
import com.epam.tamentoring.bo.ShoppingCart;
import com.epam.tamentoring.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;
    private Product apple;
    private Product banana;

    @BeforeEach
    void setUp() {
        apple = new Product(1, "Apple", 2.0, 3);
        banana = new Product(2, "Banana", 1.5, 2);
        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(apple);
        shoppingCart = new ShoppingCart(initialProducts);
    }

    @Test
    void testAddNewProductToCart() {
        shoppingCart.addProductToCart(banana);
        assertTrue(shoppingCart.getProducts().contains(banana));
        assertEquals(2, shoppingCart.getProducts().size());
    }

    @Test
    void testAddExistingProductIncreasesQuantity() {
        Product appleToAdd = new Product(1, "Apple", 2.0, 2);
        shoppingCart.addProductToCart(appleToAdd);
        Product resultApple = shoppingCart.getProductById(1);
        assertEquals(5.0, resultApple.getQuantity());
    }

    @Test
    void testRemoveExistingProductFromCart() {
        shoppingCart.removeProductFromCart(apple);
        assertFalse(shoppingCart.getProducts().contains(apple));
        assertEquals(0, shoppingCart.getProducts().size());
    }

    @Test
    void testRemoveNonExistingProductThrowsException() {
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            shoppingCart.removeProductFromCart(banana);
        });
        assertTrue(exception.getMessage().contains("Product is not found in the cart"));
    }

    @Test
    void testGetCartTotalPrice() {
        shoppingCart.addProductToCart(banana);
        double totalPrice = shoppingCart.getCartTotalPrice();
        assertEquals(9.0, totalPrice);
    }
}
