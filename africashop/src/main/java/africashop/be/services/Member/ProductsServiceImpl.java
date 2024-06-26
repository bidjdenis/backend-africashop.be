package africashop.be.services.Member;

import africashop.be.Repositories.ProductRepo;
import africashop.be.dtos.ProductDetailDto;
import africashop.be.dtos.ProductDto;
import africashop.be.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsServices{

    private final ProductRepo productRepo;

    public ProductsServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductDto> getAllProductsPagination(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        Page<Product> productPage = this.productRepo.findAll(pageable);
        List<ProductDto> productDtoList = productPage.getContent().stream()
                .map(Product::getDto)
                .collect(Collectors.toList());
        return productDtoList;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductByCountry(Long id) {
        List<Product>  products = productRepo.findByCountryId(id);
        return products.stream().map(Product :: getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductByCategory(Long id) {
        List<Product> products = productRepo.findByCategoryId(id);
        return products.stream().map(this::converToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto converToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setWeight(product.getWeight());
        productDto.setByteImg(product.getImg());
        productDto.setCategoryName(product.getCategory().getName());
        productDto.setCountryName(product.getCountry().getName());
        return productDto;
    }

    @Override
    public ProductDetailDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isPresent()){
            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductDto(optionalProduct.get().getDto());
            return productDetailDto;
        }
        return null;
    }

    @Override
    public List<ProductDto> getProductsSortedByPrice(int pageNumber, boolean ascending) {
        Sort.Order priceOrder = ascending ? Sort.Order.asc("price") : Sort.Order.desc("price");
        Sort sort = Sort.by(priceOrder);

        Pageable pageable = PageRequest.of(pageNumber, 12, sort);

        Page<Product> productPage = this.productRepo.findAll(pageable);

        List<ProductDto> productDtoList = productPage.getContent().stream()
                .map(Product::getDto)
                .collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public Product getProductDetail(Long id) {
        Optional<Product> product = productRepo.findById(id);
        return product.get();
    }


}
