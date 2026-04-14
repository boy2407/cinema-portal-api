package com.uit.cinemaportalapi.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(
        name = "SCREEN",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_SCREEN_CINEMA_CODE",
                columnNames = {"CINEMA_ID", "SCREEN_CODE"}
        )
)
@Builder
public class Screen {
    @Id
    @GeneratedValue(generator = "SCREEN_ID_GENERATOR")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "SCREEN_CODE", length = 255)
    private String screenCode;

    @Column(name = "TYPE_SCREEN", length = 255)
    private String typeScreen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CINEMA_ID", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "screen", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RoomSeat> roomSeats;
}

