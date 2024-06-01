package africashop.be.services.Member;

import africashop.be.dtos.CartItemsDto;
import africashop.be.dtos.OrderDto;

import java.util.List;

public interface OrderService {
    List<CartItemsDto> validationOrder(Long userId);
    OrderDto getOrderByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
}
