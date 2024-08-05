package com.org.project.shreyaa;

import com.org.project.shreyaa.dto.ProductRequest;
import com.org.project.shreyaa.dto.ProductResponse;
import com.org.project.shreyaa.model.Product;
import com.org.project.shreyaa.repository.ProductRepository;
import com.org.project.shreyaa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductRequest productRequest;
    private Product product;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest("iPhone 13", "Latest iPhone model", BigDecimal.valueOf(999.99));
        product = new Product("1", "iPhone 13", "Latest iPhone model", BigDecimal.valueOf(999.99));
        productResponse = ProductResponse.builder()
                .id("1")
                .name("iPhone 13")
                .description("Latest iPhone model")
                .price(BigDecimal.valueOf(999.99))
                .build();

        given(productRepository.findAll()).willReturn(Collections.singletonList(product));
    }

    @Test
    void createProduct_ShouldSaveProduct() {
        given(productRepository.save(any(Product.class))).willReturn(product);

        productService.createProduct(productRequest);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProductResponses() {
        List<ProductResponse> productResponses = productService.getAllProducts();

        assertFalse(productResponses.isEmpty());
        assertEquals(1, productResponses.size());

        ProductResponse actualResponse = productResponses.get(0);
        assertEquals(productResponse.getId(), actualResponse.getId());
        assertEquals(productResponse.getName(), actualResponse.getName());
        assertEquals(productResponse.getDescription(), actualResponse.getDescription());
        assertEquals(0, productResponse.getPrice().compareTo(actualResponse.getPrice()));
    }
}
