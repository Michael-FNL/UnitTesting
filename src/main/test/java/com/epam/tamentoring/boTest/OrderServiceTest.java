package com.epam.tamentoring.boTest;

import com.epam.tamentoring.bo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private DiscountUtility discountUtility;

    @InjectMocks
    private OrderService orderService;

    private UserAccount johnSmith;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Product product = new Product(1, "Item", 10.0, 1);
        ShoppingCart cart = new ShoppingCart(Collections.singletonList(product));
        johnSmith = new UserAccount("John", "Smith", LocalDate.of(1990, 10, 10).toString(), cart);
    }

    @Test
    void testOrderPriceWithDiscount() {
        when(discountUtility.calculateDiscount(johnSmith)).thenReturn(3.0);

        double finalPrice = orderService.getOrderPrice(johnSmith);

        assertEquals(7.0, finalPrice);
        verify(discountUtility, times(1)).calculateDiscount(johnSmith);
        verifyNoMoreInteractions(discountUtility);
    }

}
