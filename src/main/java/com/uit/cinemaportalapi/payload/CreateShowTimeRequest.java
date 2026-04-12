package com.uit.cinemaportalapi.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CreateShowTimeRequest {

    @ApiModelProperty(value = "id rạp phim", required = true)
    private Long cinemaID;

    @ApiModelProperty(value = "id phim", required = true)
    private Long movieID;

    @ApiModelProperty(value = "số phòng chiếu ", required = true)
    private String screen;

    @ApiModelProperty(value = "Giá 1 vé", required = true)
    private BigDecimal price;

    @ApiModelProperty(example = "2023-12-01T17:16", value = "ngày bắt đầu chiếu", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+7")
    private Date startTime;

    @ApiModelProperty(example = "2023-12-01T17:17", value = "ngày kết thúc chiếu", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+7")
    private Date endTime;
}
