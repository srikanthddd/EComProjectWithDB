package com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ecommerce.DTO.GenericProductDTO;
import com.myproject.ecommerce.Exceptions.ProductNotFoundException;
import com.myproject.ecommerce.Models.Category;
import com.myproject.ecommerce.Models.Product;
import com.myproject.ecommerce.thirdPartyClients.ProductService.ThirdPartyProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FakeStoreServiceThirdPartyClient implements ThirdPartyProductService {

    private RestTemplateBuilder restTemplateBuilder;

    private String getProductURL = "https://fakestoreapi.com/products/{id}";

    @Value("{fakestore.api.url}")
    private String deleteProductURL;

    @Value("{fakestore.api.url}")
    private String createProductURL;

    @Value("{fakestore.api.url}")
    private String updateProductURL;

    @Value("{fakestore.api.url}")
    private String getAllProductsURL;

    private ObjectMapper objectMapper = new ObjectMapper();

    public FakeStoreServiceThirdPartyClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }


    @Override
    public GenericProductDTO createProduct(GenericProductDTO product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDTO> responseEntity = restTemplate.postForEntity(createProductURL, product, GenericProductDTO.class);
        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Product is not created !!");
        }
        return responseEntity.getBody();
    }


    @Override
    public ResponseEntity<Product> getProductByID(Long id){

        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDTO> response = restTemplate.getForEntity(getProductURL, FakeStoreProductDTO.class, id);

        if(response.getStatusCode().is2xxSuccessful() && response.getBody()!=null && response.getBody().getId()!=null){
            FakeStoreProductDTO fakeStoreProductDTO = response.getBody();
            Product product = new Product();
            //product.setId(fakeStoreProductDTO.getId());
            String category = fakeStoreProductDTO.getCategory();
            Category categoryObj = new Category();
            categoryObj.setName(category);
            product.setCategory(categoryObj);
            product.setPrice(fakeStoreProductDTO.getPrice());
            product.setDescription(fakeStoreProductDTO.getDescription());
            product.setTitle(fakeStoreProductDTO.getTitle());
            product.setImage(fakeStoreProductDTO.getImage());

            return new ResponseEntity<Product>(
                    product,
                    HttpStatus.ACCEPTED
            );
        }else {
            throw new ProductNotFoundException("Product id "+ id +" not found : ");
            /*return new ResponseEntity<Product>(
                    HttpStatus.BAD_REQUEST
            );*/
        }
    }

    @Override
    public List<GenericProductDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDTO[]> response = restTemplate.getForEntity(getAllProductsURL,GenericProductDTO[].class );

        return List.of(response.getBody());
    }


    public GenericProductDTO deleteProduct(Long id){
        String url = deleteProductURL + "/" + id;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDTO> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, GenericProductDTO.class);
        return responseEntity.getBody();
    }

    public FakeStoreProductDTO updateProduct(Long id, GenericProductDTO genericProductDTO){
        String url = updateProductURL + "/" + id;
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<GenericProductDTO> requestEntity = new HttpEntity<>(genericProductDTO);
        ResponseEntity<GenericProductDTO> responseEntity =  restTemplate.exchange(url, HttpMethod.PUT, requestEntity, GenericProductDTO.class);
        GenericProductDTO genericProductDTO1 = responseEntity.getBody();

        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(genericProductDTO1.getId());
        fakeStoreProductDTO.setTitle(genericProductDTO1.getTitle());

        return  fakeStoreProductDTO;
    }

}
