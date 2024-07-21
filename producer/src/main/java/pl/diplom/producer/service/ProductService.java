package pl.diplom.producer.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Product;
import pl.diplom.common.repository.ProductRepository;
import pl.diplom.producer.dto.ProductUpdateDto;
import pl.diplom.producer.exception.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public HttpStatus createProduct(Product product){
        productRepository.save(product);
        return HttpStatus.CREATED;
    }

    public HttpStatus deleteProduct(String token, int id){
        Product product = findProductById(id);
        productRepository.delete(product);
        return HttpStatus.OK;
    }

    public Product findProductById(int id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("product with this id not found"));
    }

    public Product findProductByName(String name){
        return productRepository.findProductByName(name)
                .orElseThrow(() -> new ProductNotFoundException("product with this name not found"));
    }

    public HttpStatus updateProduct(Product product, ProductUpdateDto productUpdateDto){
         modelMapper.map(productUpdateDto, product);
         productRepository.save(product);
         return HttpStatus.OK;
    }
}