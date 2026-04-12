package com.uit.cinemaportalapi.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(generator = "USER_ID_GENERATOR")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "USERNAME", length = 32)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD" , length = 2000)
    private String password;

    @Column(name = "PASSWORD_REMINDER_TOKEN", length = 128)
    private String passReminderToken;

    @Column(name = "PASSWORD_REMINDER_EEXPIRE")
    private Date passReminderExpire;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "Enabled")
    private Boolean enabled;

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
