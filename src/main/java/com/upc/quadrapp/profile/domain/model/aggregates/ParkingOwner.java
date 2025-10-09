package com.upc.quadrapp.profile.domain.model.aggregates;

import com.upc.quadrapp.profile.domain.model.commands.CreateParkingOwnerProfileCommand;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import com.upc.quadrapp.profile.domain.model.valueobjects.Ruc;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Entity
public class ParkingOwner {

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
    private String companyName;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Ruc ruc;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Long userId;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Date createdAt;

    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private Date updatedAt;

    public ParkingOwner() {}

    public ParkingOwner(CreateParkingOwnerProfileCommand command) {
        this.fullName = command.fullName();
        this.city = command.city();
        this.country = command.country();
        this.phone = command.phone();
        this.companyName = command.companyName();
        this.ruc = command.ruc();
        this.userId = command.userId();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
