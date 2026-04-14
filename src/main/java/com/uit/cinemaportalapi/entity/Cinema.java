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
@Table(name = "CINEMA")
public class Cinema {
    @Id
    @GeneratedValue(generator = "CINEMA_ID_GENERATOR")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "CODE", length = 255)
    private String code;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "LOCATION", length = 255)
    private String location;

    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Screen> screens;
}
