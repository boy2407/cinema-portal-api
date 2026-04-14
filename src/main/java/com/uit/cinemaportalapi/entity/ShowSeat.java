package com.uit.cinemaportalapi.entity;
import  jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "SHOW_SEATS",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_SHOW_SEAT_SHOWTIME_ROOMSEAT",
                columnNames = {"SHOW_TIME_ID", "ROOM_SEAT_ID"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOW_TIME_ID", nullable = false)
    private ShowTime showTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_SEAT_ID", nullable = false)
    private RoomSeat roomSeat;

    @Column(name = "IS_RESERVED")
    private Boolean isReserved; // True nếu đã có người đặt hoặc đang giữ chỗ

    @Column(name = "PRICE")
    private Double price; // Giá vé cho ghế này (có thể thay đổi tùy suất chiếu)

    @ManyToOne
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket; // Sẽ null nếu chưa có vé nào mua ghế này
}

