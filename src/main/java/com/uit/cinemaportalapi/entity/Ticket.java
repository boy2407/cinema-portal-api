package com.uit.cinemaportalapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(generator = "TICKET_ID_GENERATOR")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Seat> seats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "QR_CODE", length = 32)
    private String qrCode;

    @Column(name = "BOOKING_NUMBER", length = 32)
    @GeneratedValue(generator = "TICKET_BOOKING_NUMBER_GENERATOR")
    private String bookingNumber;

    @Column(name = "SUBTOTAL", length = 9)
    private BigDecimal subtotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+7")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+7")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;
}
