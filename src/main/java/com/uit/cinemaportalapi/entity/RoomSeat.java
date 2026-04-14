package com.uit.cinemaportalapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(
        name = "ROOM_SEATS",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_ROOM_SEAT_SCREEN_ROW_NUMBER",
                columnNames = {"SCREEN_ID", "SEAT_ROW", "SEAT_NUMBER"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "SEAT_ROW")
    private String seatRow; // Ví dụ: A, B, C

    @Column(name = "SEAT_NUMBER")
    private Integer seatNumber; // Ví dụ: 1, 2, 3

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEAT_TYPE_ID", nullable = false)
    private SeatType type; // VIP, NORMAL, SWEETBOX

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCREEN_ID", nullable = false)
    private Screen screen; // Ghế cố định theo từng phòng chiếu

    @OneToMany(mappedBy = "roomSeat", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ShowSeat> showSeats;

}