package ee.richja.musicianbakery.repository;

import ee.richja.musicianbakery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT PRODUCT_PRICE FROM PRODUCTS WHERE PRODUCT_ABBREVIATION = ?1", nativeQuery = true)
    BigDecimal findPriceByAbbreviation(char productAbbreviation);

    @Query(value = "SELECT PRODUCT_STOCK FROM PRODUCTS WHERE PRODUCT_ABBREVIATION = ?1", nativeQuery = true)
    int getProductStock(char productAbbreviation);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PRODUCTS SET PRODUCT_STOCK=?1 WHERE PRODUCT_ABBREVIATION = ?2", nativeQuery = true)
    void updateProductStock(int newStockValue, char productAbbreviation);
}
