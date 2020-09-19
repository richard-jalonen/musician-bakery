package ee.richja.musicianbakery.model;

import lombok.Data;

import java.util.Map;

@Data
public class Order {
    Map<String, Long> order;
}
