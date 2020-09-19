package ee.richja.musicianbakery.service;

import ee.richja.musicianbakery.model.Order;
import ee.richja.musicianbakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    ProductRepository productRepository;

    public BigDecimal getCheckoutAmount(Order order) {
        BigDecimal sum = new BigDecimal(0);
        for (Map.Entry<String, Long> entry : order.getOrder().entrySet()) {
            BigDecimal itemPrice = productRepository.findPriceByAbbreviation(entry.getKey().charAt(0));
            sum = sum.add(new BigDecimal(entry.getValue()).multiply(itemPrice));
        }
        return sum;
    }

    public BigDecimal payForOrder(BigDecimal checkoutAmount, BigDecimal amountPaid, Order order) throws Exception {
        updateProductStock(order);
        return amountPaid.subtract(checkoutAmount);
    }

    private void updateProductStock(Order order) throws Exception {
        for (Map.Entry<String, Long> entry : order.getOrder().entrySet()) {
            int currentStock = productRepository.getProductStock(entry.getKey().charAt(0));
            int newStock = currentStock - entry.getValue().intValue();
            if (newStock < 0) {
                throw new Exception("Out of stock");
            }
            productRepository.updateProductStock(newStock, entry.getKey().charAt(0));
        }
    }
}
