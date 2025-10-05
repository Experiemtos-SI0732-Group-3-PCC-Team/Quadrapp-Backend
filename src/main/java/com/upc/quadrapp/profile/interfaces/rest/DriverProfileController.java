package com.upc.quadrapp.profile.interfaces.rest;

import com.upc.quadrapp.profile.application.internal.commandservices.DriverProfileCommandServiceImpl;
import com.upc.quadrapp.profile.application.internal.queryservices.DriverProfileQueryServiceImpl;
import com.upc.quadrapp.profile.domain.model.queries.GetDriverProfileByIdQuery;
import com.upc.quadrapp.profile.interfaces.rest.resources.CreateDriverProfileResource;
import com.upc.quadrapp.profile.interfaces.rest.transform.CreateDriverProfileResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver-profiles")
public class DriverProfileController {

    private final DriverProfileCommandServiceImpl commandService;
    private final DriverProfileQueryServiceImpl queryService;

    public DriverProfileController(DriverProfileCommandServiceImpl commandService, DriverProfileQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverProfileById(@PathVariable Long id) {
        var result = queryService.handle(new GetDriverProfileByIdQuery(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createDriverProfile(@RequestBody CreateDriverProfileResource resource) {
        var command = CreateDriverProfileResourceFromEntityAssembler.toCommand(resource);
        Long id = commandService.createDriver(command);
        return ResponseEntity.ok(id);
    }
}
