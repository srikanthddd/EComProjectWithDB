package com.myproject.ecommerce.Products;

import com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore.FakeStoreProductDTO;
import com.myproject.ecommerce.DTO.GenericProductDTO;
import com.myproject.ecommerce.Models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {


    public ResponseEntity<Product> getProductByID(Long id);

    public GenericProductDTO createProduct(GenericProductDTO product);

    public List<GenericProductDTO> getAllProducts();

    public GenericProductDTO deleteProduct(Long id);

    public FakeStoreProductDTO updateProduct(Long id, GenericProductDTO genericProductDTO);
}
