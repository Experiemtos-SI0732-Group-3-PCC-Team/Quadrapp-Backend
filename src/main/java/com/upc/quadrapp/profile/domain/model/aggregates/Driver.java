package com.upc.quadrapp.profile.domain.model.aggregates;

import com.upc.quadrapp.profile.domain.model.commands.CreateDriverProfileCommand;
import com.upc.quadrapp.profile.domain.model.valueobjects.Dni;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Entity
public class Driver {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String city;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String country;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Phone phone;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Dni dni;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Long userId;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    @Getter
    private Date createdAt;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    @Getter
    private Date updatedAt;

    public Driver() {}

    public Driver(CreateDriverProfileCommand command) {
        this.fullName = command.fullName();
        this.city = command.city();
        this.country = command.country();
        this.phone = command.phone();
        this.dni = command.dni();
        this.userId = command.userId();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
