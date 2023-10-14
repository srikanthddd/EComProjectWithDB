package com.myproject.ecommerce.thirdPartyClients.ProductService;

import com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore.FakeStoreProductDTO;
import com.myproject.ecommerce.DTO.GenericProductDTO;
import com.myproject.ecommerce.Models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ThirdPartyProductService {

    ResponseEntity<Product> getProductByID(Long id);

    List<GenericProductDTO> getAllProducts();

    GenericProductDTO createProduct(GenericProductDTO product);

    FakeStoreProductDTO updateProduct(Long id, GenericProductDTO genericProductDTO);

    GenericProductDTO deleteProduct(Long id);

}
