package africashop.be.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CouponDto {

    private Long id;
    private String name;
    private String code;
    private Long discount;
    private String description;
    private Date expirationDate;
}
