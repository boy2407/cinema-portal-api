package com.uit.cinemaportalapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BookingSeatsRequest {
    @Schema(description = "id suất chiếu", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long showtimeID;

    @Schema(description = "id user", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userID;

    @Schema(description = "danh sách id seat", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> seatIDs;

    @Schema(description = "Tổng tiền", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal subtotal;
}
