package com.myproject.ecommerce.Products;

import com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore.FakeStoreProductDTO;
import com.myproject.ecommerce.DTO.GenericProductDTO;
import com.myproject.ecommerce.Models.Product;
import com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore.FakeStoreServiceThirdPartyClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//@Component
@Primary  // Primary and Qualifier, if we dont give @qualifier, by default the class with annotation @Primary will be called.
@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{


    private FakeStoreServiceThirdPartyClient thirdPartyProductServiceClient;

    public FakeStoreProductService(FakeStoreServiceThirdPartyClient thirdPartyProductServiceClient){
        this.thirdPartyProductServiceClient = thirdPartyProductServiceClient;
    }


    @Override
    public GenericProductDTO createProduct(GenericProductDTO product) {
        return thirdPartyProductServiceClient.createProduct(product);
    }


    @Override
    public ResponseEntity<Product> getProductByID(Long id){
        return thirdPartyProductServiceClient.getProductByID(id);
    }

    @Override
    public List<GenericProductDTO> getAllProducts() {
        return thirdPartyProductServiceClient.getAllProducts();
    }

    @Override
    public GenericProductDTO deleteProduct(Long id){
        return thirdPartyProductServiceClient.deleteProduct(id);
    }

    public FakeStoreProductDTO updateProduct(Long id, GenericProductDTO genericProductDTO){
        return thirdPartyProductServiceClient.updateProduct(id, genericProductDTO);
    }

}
