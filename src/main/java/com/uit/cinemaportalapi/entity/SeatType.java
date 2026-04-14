package com.uit.cinemaportalapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SEAT_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name; // Ví dụ: "STANDARD", "VIP", "SWEETBOX"

    @Column(name = "SURCHARGE", nullable = false)
    private Double surcharge; // Số tiền phụ thu thêm cho loại ghế này
}