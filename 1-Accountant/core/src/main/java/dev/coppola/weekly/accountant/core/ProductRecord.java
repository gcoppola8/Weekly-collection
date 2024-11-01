package dev.coppola.weekly.accountant.core;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gennaro Coppola "coppola612@gmail.com"
 */
public class ProductRecord {

    private Product product;
    private Map<LocalDate, Integer> record;

    public ProductRecord(Product product) {
        this.product = product;
        this.record = new HashMap<>();
    }

    public Product getProduct() {
        return product;
    }

    public Map<LocalDate, Integer> getRecord() {
        return record;
    }

}
