package com.org.project.shreyaa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.project.shreyaa.controller.ProductController;
import com.org.project.shreyaa.dto.ProductRequest;
import com.org.project.shreyaa.dto.ProductResponse;
import com.org.project.shreyaa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;
    private ProductRequest testProductRequest;
    private List<ProductResponse> testProductResponses;
    private ProductResponse productResponse1;

    @BeforeEach
    void setUp() {

            testProductRequest = new ProductRequest();

            testProductRequest.setName("Test Product");
            testProductRequest.setDescription("This is a test product description");
            testProductRequest.setPrice(BigDecimal.valueOf(12000.00));
        ProductResponse productResponse2 = new ProductResponse();
        productResponse2.setId("2");
        productResponse2.setName("Test Product 2");
        productResponse2.setDescription("Description for product 2");
        productResponse2.setPrice(new BigDecimal("199.99"));

        testProductResponses = Arrays.asList(productResponse1, productResponse2);



        Mockito.doNothing().when(productService).createProduct(Mockito.any
                    (ProductRequest.class));
            Mockito.when(productService.getAllProducts()).thenReturn(testProductResponses);

    }

    @Test
    void createProductTest() throws Exception {
        ProductRequest productRequest = new ProductRequest();


        Mockito.doNothing().when(productService).createProduct(Mockito.any(ProductRequest.class));

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());

        Mockito.verify(productService, Mockito.times(1)).
                createProduct(Mockito.any(ProductRequest.class));
    }

    @Test
    void getAllProductsTest() throws Exception {
        List<ProductResponse> productResponses = Arrays.asList(new ProductResponse(),
                new ProductResponse());


        Mockito.when(productService.getAllProducts()).thenReturn(productResponses);

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(productResponses.size()));

        Mockito.verify(productService, Mockito.times(1)).
                getAllProducts();
    }
}
