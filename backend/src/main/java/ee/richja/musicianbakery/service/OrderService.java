package ee.richja.musicianbakery.service;

import ee.richja.musicianbakery.model.Order;
import ee.richja.musicianbakery.model.Product;
import ee.richja.musicianbakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    ProductRepository productRepository;

    public Order generateOrder(String userInput) {
        Order order = new Order();
        order.setOrder(Arrays.stream(userInput.split(",")).collect(Collectors.groupingBy(Function.identity(),
                Collectors.counting())));
        return order;
    }

    public boolean isValidInput(String userInput) {
        for (String input : userInput.split(",")) {
            if (input.trim().length() != 1 || getAllProducts().stream().noneMatch(product ->
                    product.getProductAbbreviation() == input.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    public boolean allItemsInStock(Order order) {
        for (Map.Entry<String, Long> entry : order.getOrder().entrySet()) {
            if (getAllProducts().stream().anyMatch(product -> product.getProductStock() < entry.getValue()))
                return false;
        }
        return true;
    }

    private List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.findAll());
    }
}
