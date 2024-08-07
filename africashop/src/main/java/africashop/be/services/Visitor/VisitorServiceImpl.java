package africashop.be.services.Visitor;

import africashop.be.Repositories.*;
import africashop.be.dtos.BlogDto;
import africashop.be.dtos.OrderDto;
import africashop.be.dtos.ProductDetailDto;
import africashop.be.dtos.ProductDto;
import africashop.be.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitorServiceImpl implements VisitorService{
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CouponRepo couponRepo;
    private final OrderRepo orderRepo;

    private final BlogRepo blogRepo;
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepo.findAll();
        return products.stream().map(Product :: getDto).collect(Collectors.toList());
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
    public List<Category> getAllCategories() {
        return this.categoryRepo.findAll();
    }

    @Override
    public List<Coupon> getAllCoupons() {return couponRepo.findAll();
    }

    @Override
    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> optionalOrder = orderRepo.findByTrackingId(trackingId);
        if(optionalOrder.isPresent()){
            return optionalOrder.get().getOrderDto();
        }
        return null;
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        List<Blog> blogs = blogRepo.findAll();
        return blogs.stream().map(Blog::getDto).collect(Collectors.toList());
    }

    @Override
    public Blog getBlogById(Long id) {
        Optional<Blog> blog = blogRepo.findById(id);
        return blog.get();
    }

}
