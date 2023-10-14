package com.myproject.ecommerce.Products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore.FakeStoreProductDTO;
import com.myproject.ecommerce.DTO.GenericProductDTO;
import com.myproject.ecommerce.Exceptions.ProductNotFoundException;
import com.myproject.ecommerce.Models.Category;
import com.myproject.ecommerce.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Component
@Service("FakeStoreProductServiceOld")
public class FakeStoreProductServiceOld implements ProductService{

    private RestTemplateBuilder restTemplateBuilder;

    private String getProductURL = "https://fakestoreapi.com/products/{id}";

    private String deleteProductURL = "https://fakestoreapi.com/products";

    private String createProductURL = "https://fakestoreapi.com/products";

    private String updateProductURL = "https://fakestoreapi.com/products";

    private String getAllProductsURL = "https://fakestoreapi.com/products";

    private ObjectMapper objectMapper = new ObjectMapper();

    public FakeStoreProductServiceOld(RestTemplateBuilder restTemplateBuilder){
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

        /*
        #This below will also work

        ResponseEntity<List<GenericProductDTO>> response = restTemplate.exchange(
                getAllProductsURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GenericProductDTO>>() {
                }
        );*/

        return List.of(response.getBody());
    }

    /*@Override

    #This will also work..

    public List<GenericProductDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO[]> response = restTemplate.getForEntity(getAllProductsURL, FakeStoreProductDTO[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            FakeStoreProductDTO[] products = response.getBody();
            if (products != null) {
                // Convert from FakeStoreProductDTO to GenericProductDTO
                return Arrays.stream(products)
                        .map(this::convertToGenericProductDTO)
                        .collect(Collectors.toList());
            }
        }

        // Handle the case when the response is not successful or there are no products.
        return Collections.emptyList();
    }

    private GenericProductDTO convertToGenericProductDTO(FakeStoreProductDTO fakeStoreProductDTO) {
        GenericProductDTO product = new GenericProductDTO();
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setCategory(fakeStoreProductDTO.getCategory());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setImage(fakeStoreProductDTO.getImage());
        return product;
    }*/

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
