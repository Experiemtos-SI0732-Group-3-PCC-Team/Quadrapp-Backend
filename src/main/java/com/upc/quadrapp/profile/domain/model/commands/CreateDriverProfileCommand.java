package com.upc.quadrapp.profile.domain.model.commands;

import com.upc.quadrapp.profile.domain.model.valueobjects.Dni;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;

public record CreateDriverProfileCommand(String fullName,
                                         String city,
                                         String country,
                                         Phone phone,
                                         Dni dni,
                                         Long userId) {
}
