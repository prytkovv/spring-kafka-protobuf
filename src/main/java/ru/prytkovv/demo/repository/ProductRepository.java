package ru.prytkovv.demo.repository;

import org.springframework.stereotype.Repository;
import ru.prytkovv.demo.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


@Repository
public class ProductRepository {

    private final ConcurrentMap<UUID, Product> products;

    public ProductRepository() {
        this.products = new ConcurrentHashMap<>();
    }

    public void save(Product product) {
        products.put(product.id(), product);
    }

    public Product findById(UUID id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findAll(int limit) {
        return products.values().stream().limit(limit).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        products.remove(id);
    }
}
