package ee.richja.musicianbakery.service;

import ee.richja.musicianbakery.model.Order;
import ee.richja.musicianbakery.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {
    @InjectMocks
    PaymentService paymentService;
    @Mock
    ProductRepository productRepository;
    Order order;

    @Before
    public void init() {
        initDummyOrder();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCheckoutAmountReturnsValidCheckoutAmount() {
        when(productRepository.findPriceByAbbreviation(anyChar())).thenReturn(new BigDecimal("1"));
        assertEquals(new BigDecimal("4"), paymentService.getCheckoutAmount(order));
    }

    @Test
    public void payForOrderGivesCorrectAmountOfChange() throws Exception {
        when(productRepository.getProductStock(anyChar())).thenReturn(5);
        assertEquals(new BigDecimal("0.50"), paymentService.payForOrder(new BigDecimal("9"), new BigDecimal("9.50"), order));
    }

    private void initDummyOrder() {
        order = new Order();
        Map<String, Long> orderMap = new HashMap<>();
        orderMap.put("B", 1L);
        orderMap.put("C", 1L);
        orderMap.put("W", 1L);
        orderMap.put("M", 1L);
        order.setOrder(orderMap);
    }
}