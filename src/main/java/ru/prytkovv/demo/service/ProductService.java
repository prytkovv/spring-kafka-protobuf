package ru.prytkovv.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prytkovv.demo.conveter.TimestampConverter;
import ru.prytkovv.demo.model.Product;
import ru.prytkovv.demo.repository.ProductRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ProductService {

    private final TimestampConverter timestampConverter;
    private final ProductRepository productRepository;

    public ProductService(TimestampConverter timestampConverter,ProductRepository productRepository) {
        this.timestampConverter = timestampConverter;
        this.productRepository = productRepository;
    }

    public <T> List<T> getProducts(Integer limit, Function<Product, T> converter) {
        return productRepository.findAll(limit).stream().map(converter).collect(Collectors.toList());
    }

    public <T> T getProduct(UUID id, Function<Product, T> converter) {
        return converter.apply(productRepository.findById(id));
    }

    public void createProduct(ru.prytkovv.demo.proto.Product in) {
        UUID id = UUID.randomUUID();
        OffsetDateTime manufacturedAt = timestampConverter.toOffsetDateTime(in.getManufacturedAt());
        OffsetDateTime createdAt = timestampConverter.toOffsetDateTime(in.getCreatedAt());
        Product product = new Product(id, "/tmp/" + id, in.getBarcode(), in.getCategoryId(),
                                      in.getManufacturerId(), manufacturedAt, createdAt);
        productRepository.save(product);
        log.debug(String.format("Product %s was created", product.id()));
    }

    public void removeProduct(UUID id) {
        productRepository.delete(id);
        log.debug(String.format("Product %s was deleted", id));
    }

}
