package com.uit.cinemaportalapi.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CreateShowTimeRequest {

    @Schema(description = "id rạp phim", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cinemaID;

    @Schema(description = "id phim", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long movieID;

    @Schema(description = "id phòng chiếu ", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long screenID;

    @Schema(description = "Giá 1 vé", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @Schema(example = "2023-12-01T17:16", description = "ngày bắt đầu chiếu", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+7")
    private Date startTime;

    @Schema(example = "2023-12-01T17:17", description = "ngày kết thúc chiếu", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+7")
    private Date endTime;
}
