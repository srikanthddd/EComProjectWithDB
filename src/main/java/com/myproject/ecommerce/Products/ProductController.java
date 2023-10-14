package com.myproject.ecommerce.Products;

import com.myproject.ecommerce.thirdPartyClients.ProductService.FakeStore.FakeStoreProductDTO;
import com.myproject.ecommerce.DTO.GenericProductDTO;
import com.myproject.ecommerce.Exceptions.ErrorDTO;
import com.myproject.ecommerce.Exceptions.ProductNotFoundException;
import com.myproject.ecommerce.Models.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    //@Autowired   --> //Injecting via Constructor injection is recommended.

    private ProductService productService;

    /*@Value("${productService.type}")
    private String productServiceType;*/

    //Constuctor injection
    public ProductController(@Qualifier("FakeStoreProductService") ProductService productService){
        this.productService = productService;
    }

    //Setter injection
    /*public void setProductService(ProductService productService){
        this.productService = productService;
    }*/

    @PostMapping()
    public GenericProductDTO createProduct(@RequestBody GenericProductDTO genericProductDTO){
        return productService.createProduct(genericProductDTO);
    }

    @PutMapping("{id}")
    public FakeStoreProductDTO updateProduct(@PathVariable("id") Long id, @RequestBody GenericProductDTO genericProductDTO){
        return productService.updateProduct(id, genericProductDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDTO> deleteProductById(@PathVariable("id") Long id){

        //We could directly return GenericProductDTO, but using ResponseEntity, we can set few other parameters and return.

        ResponseEntity<GenericProductDTO> responseEntity = new ResponseEntity<>(
                productService.deleteProduct(id),
                HttpStatus.ACCEPTED
        );
        return responseEntity;
    }


    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return productService.getProductByID(id);
    }

    @GetMapping("getAllProducts")
    public List<GenericProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
        ErrorDTO error = new ErrorDTO(1234, productNotFoundException.getMessage());
        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
        return responseEntity;
    }

}
