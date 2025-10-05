package com.upc.quadrapp.profile.domain.model.commands;

import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import com.upc.quadrapp.profile.domain.model.valueobjects.Ruc;

public record CreateParkingOwnerProfileCommand(String fullName,
                                               String city,
                                               String country,
                                               Phone phone,
                                               String companyName,
                                               Ruc ruc,
                                               Long userId) {
}
