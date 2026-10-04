package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int productId) {

        products.removeIf(product ->
                product.getId() == productId
        );
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {

        double total = 0;

        for (Product product : products) {
            total += product.getPrice();
        }

        return total;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void clear() {
        products.clear();
    }
}