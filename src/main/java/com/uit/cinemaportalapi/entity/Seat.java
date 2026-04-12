package com.uit.cinemaportalapi.entity;


import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "SEAT")
public class Seat {
    @Id
    @GeneratedValue(generator = "SEAT_ID_GENERATOR")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "TYPE", length = 255)
    private String type;

    @Column(name = "NUMBER", length = 2)
    private Integer number;

    @Column(name = "SEAT_ROW", length = 1)
    private String row;

    @Column(name = "BOOKED", length = 1)
    private Boolean isBooked;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SHOW_TIME_ID")
    private ShowTime showtime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;
}
