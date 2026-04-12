package com.uit.cinemaportalapi.payload;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BookingSeatsRequest {
    @ApiModelProperty(value = "id suất chiếu", required = true)
    private Long showtimeID;

    @ApiModelProperty(value = "id user", required = true)
    private Long userID;

    @ApiModelProperty(value = "danh sách id seat", required = true)
    private List<Long> seatIDs;

    @ApiModelProperty(value = "Tổng tiền", required = true)
    private BigDecimal subtotal;
}
