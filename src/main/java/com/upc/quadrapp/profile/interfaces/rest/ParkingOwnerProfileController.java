package com.upc.quadrapp.profile.interfaces.rest;

import com.upc.quadrapp.profile.domain.model.queries.GetParkingOwnerProfileByIdQuery;
import com.upc.quadrapp.profile.domain.services.ParkingOwnerProfileCommandService;
import com.upc.quadrapp.profile.domain.services.ParkingOwnerProfileQueryService;
import com.upc.quadrapp.profile.interfaces.rest.resources.CreateParkingOwnerProfileResource;
import com.upc.quadrapp.profile.interfaces.rest.transform.CreateParkingOwnerProfileResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingOwner-profiles")
public class ParkingOwnerProfileController {

    private final ParkingOwnerProfileCommandService commandService;
    private final ParkingOwnerProfileQueryService queryService;

    public ParkingOwnerProfileController(ParkingOwnerProfileCommandService commandService, ParkingOwnerProfileQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingOwnerProfileById(@PathVariable Long id) {
        var result = queryService.handle(new GetParkingOwnerProfileByIdQuery(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createParkingOwnerProfile(@RequestBody CreateParkingOwnerProfileResource resource) {
        var command = CreateParkingOwnerProfileResourceFromEntityAssembler.toCommand(resource);
        Long id = commandService.createParkingOwner(command);
        return ResponseEntity.ok(id);
    }
}
