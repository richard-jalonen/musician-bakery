package ee.richja.musicianbakery.controller;

import ee.richja.musicianbakery.model.Order;
import ee.richja.musicianbakery.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/checkout-amount")
    public BigDecimal getCheckoutAmount(@RequestBody Order order) {
        return paymentService.getCheckoutAmount(order);
    }

    @PostMapping("/pay")
    public ResponseEntity postOrderPayment(@RequestParam BigDecimal checkoutAmount,
                                           @RequestParam BigDecimal amountPaid,
                                           @RequestBody Order order) throws Exception {
        if (checkoutAmount.compareTo(amountPaid) > 0) {
            return new ResponseEntity<>("Not enough money", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(paymentService.payForOrder(checkoutAmount, amountPaid, order), HttpStatus.OK);
    }
}
