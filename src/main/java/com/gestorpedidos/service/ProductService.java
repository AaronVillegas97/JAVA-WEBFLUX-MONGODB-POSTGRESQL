package com.gestorpedidos.service;

import com.gestorpedidos.dto.ProductDto;
import com.gestorpedidos.entity.ProductEntity;
import com.gestorpedidos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository  productRepository;

    public Flux<ProductDto> listProducts(){
        return  productRepository.findByStatus(true)
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getStatus(), product.getStock()));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto){
        return productDto
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
        .flatMap(product -> {
            ProductEntity newProduct = new ProductEntity();
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setStock(product.getStock());

            return productRepository.save(newProduct)
                    .map(savedProduct -> {
                        ProductDto savedProductDto = new ProductDto();
                        savedProductDto.setId(savedProduct.getId());
                        savedProductDto.setName(savedProduct.getName());
                        savedProductDto.setPrice(savedProduct.getPrice());
                        savedProductDto.setStock(savedProduct.getStock());
                        return savedProductDto;
                    });
        });
    }

    public Mono<ProductDto> getProduct(String id){
        Integer nId = Integer.parseInt(id);
        return productRepository.findById(nId)
                .flatMap(product ->{
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    productDto.setPrice(product.getPrice());
                    productDto.setStock(product.getStock());
                    productDto.setStatus(product.getStatus());
                    return Mono.just(productDto);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")));
    }

    public Mono<Boolean> deleteProduct(String id) {
        return productRepository.findById(Integer.parseInt(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                .flatMap(product -> {
                    product.setStatus(false);
                    return productRepository.save(product);
                })
                .thenReturn(true);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
        return  productDtoMono.flatMap(productDto-> {
            return productRepository.findById(Integer.parseInt(id))
                    .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                    .flatMap(productData -> {
                        productData.setName(productDto.getName());
                        productData.setPrice(productDto.getPrice());
                        productData.setStatus(productDto.getStatus());
                        productData.setStock(productDto.getStock());
                        return productRepository.save(productData)
                                .flatMap(save -> Mono.just(productDto));
                    });
        });
    }
}
