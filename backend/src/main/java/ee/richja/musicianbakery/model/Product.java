package ee.richja.musicianbakery.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    int id;
    String productName;
    BigDecimal productPrice;
    int productStock;
    char productAbbreviation;
}
