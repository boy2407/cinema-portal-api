package com.uit.cinemaportalapi.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "SCREEN")
public class Screen {
    @Id
    @GeneratedValue(generator = "SCREEN_ID_GENERATOR")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "SCREEN_CODE", length = 255)
    private String ScreenCode;

    @Column(name = "TYPE_SCEEN", length = 255)
    private String typeScreen;

    @Column(name = "NUMBER_OF_NORMAL_SEAT", length = 3)
    private Integer numberOfNormalSeat;

    @Column(name = "NUMBER_OF_VIP_SEAT", length = 3)
    private Integer numberOfVipSeat;

    @Column(name = "NUMBER_OF_COUPLE_SEAT", length = 3)
    private Integer numberOfCoupleSeat;

}
