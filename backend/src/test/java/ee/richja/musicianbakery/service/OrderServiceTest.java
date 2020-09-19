package ee.richja.musicianbakery.service;

import ee.richja.musicianbakery.model.Order;
import ee.richja.musicianbakery.model.Product;
import ee.richja.musicianbakery.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
    @Mock
    ProductRepository productRepository;

    List<Product> dummyProductList;
    Order order;

    @Before
    public void init() {
        initDummyProductList();
        initDummyOrder();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isValidInputForCommaDelimitedString() {
        when(productRepository.findAll()).thenReturn(dummyProductList);
        assertTrue(orderService.isValidInput("B,M,C,W"));
    }

    @Test
    public void isInvalidInputForUnknownString() {
        when(productRepository.findAll()).thenReturn(dummyProductList);
        assertFalse(orderService.isValidInput("B,A,C,W"));
    }

    @Test
    public void isInvalidInputForEmptyString() {
        assertFalse(orderService.isValidInput(""));
    }

    @Test
    public void generateOrderCreatesAValidOrder() {
        Order order = orderService.generateOrder("W,W,W,C,B,B,M,M,C");
        assertEquals(4, order.getOrder().size());
        assertEquals(2, order.getOrder().get("C").intValue());
    }

    @Test
    public void allItemsInStockReturnsTrueWhenEnoughStock() {
        when(productRepository.findAll()).thenReturn(dummyProductList);
        assertTrue(orderService.allItemsInStock(order));
    }

    @Test
    public void allItemsInStockReturnsFalseWhenOutOfStock() {
        order.getOrder().replace("W", 10L);
        when(productRepository.findAll()).thenReturn(dummyProductList);
        assertFalse(orderService.allItemsInStock(order));
    }

    private void initDummyProductList() {
        dummyProductList = new ArrayList<>() {{
            add(new Product() {{
                setProductStock(5);
                setProductAbbreviation('B');
            }});
            add(new Product() {{
                setProductStock(4);
                setProductAbbreviation('M');
            }});
            add(new Product() {{
                setProductStock(3);
                setProductAbbreviation('C');
            }});
            add(new Product() {{
                setProductStock(2);
                setProductAbbreviation('W');
            }});
        }};
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