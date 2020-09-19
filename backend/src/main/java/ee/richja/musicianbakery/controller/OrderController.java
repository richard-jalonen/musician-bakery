package ee.richja.musicianbakery.controller;

import ee.richja.musicianbakery.model.Order;
import ee.richja.musicianbakery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/{orderRequest}")
    public ResponseEntity getOrder(@PathVariable String orderRequest) {
        if (!orderService.isValidInput(orderRequest))
            return new ResponseEntity<>("Invalid order input", HttpStatus.BAD_REQUEST);
        Order order = orderService.generateOrder(orderRequest);
        if (!orderService.allItemsInStock(order))
            return new ResponseEntity<>("Not enough stock", HttpStatus.CONFLICT);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
