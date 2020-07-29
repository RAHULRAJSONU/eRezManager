package com.jhipster.demo.web.rest;

import com.jhipster.demo.domain.Resident;
import com.jhipster.demo.domain.Room;
import com.jhipster.demo.security.AuthoritiesConstants;
import com.jhipster.demo.service.ResidentService;
import com.jhipster.demo.service.RoomService;
import com.jhipster.demo.service.dto.ResidentDTO;
import com.jhipster.demo.web.rest.errors.BadRequestAlertException;
import com.jhipster.demo.web.rest.errors.EmailAlreadyUsedException;
import io.github.jhipster.web.util.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facility")
public class FacilityResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private RoomService roomService;

    @Autowired
    private ResidentService residentService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/rooms")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Room> createRoom() throws URISyntaxException {
        log.debug("REST request to create Room");
        Room room = new Room();
        Room newRoom = roomService.createRoom(room);
        return ResponseEntity.created(new URI("/api/facility/rooms" + newRoom.getId()))
            .headers(HeaderUtil
                .createAlert(applicationName,
                    "A user is created with identifier " + newRoom.getId(),
                    newRoom.getId().toString()))
            .body(newRoom);
    }

    @PostMapping("/residents")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Resident> createResident(@Valid @RequestBody ResidentDTO residentDTO)
        throws URISyntaxException {
        log.debug("REST request to save resident : {}", residentDTO);

        if (residentDTO.getId() != null) {
            throw new BadRequestAlertException("A new resident cannot already have an ID",
                "userManagement", "idexists");
            // Lowercase the resident email before comparing with database
        } else if (residentService.findByEmail(residentDTO.getEmail().toLowerCase()) != null) {
            throw new EmailAlreadyUsedException();
        } else {
            Resident newResident = residentService.createResident(residentDTO);
            return ResponseEntity
                .created(new URI("/api/facility/residents/" + newResident.getEmail()))
                .headers(HeaderUtil.createAlert(applicationName,
                    "A resident is created with identifier " + newResident.getEmail(),
                    newResident.getEmail()))
                .body(newResident);
        }
    }

    @GetMapping("/residents/remove/{room}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> removeResident(@PathVariable String room) {
        log.debug("REST request to remove resident from room: {}", room);
        Long roomId = Long.parseLong(room);
        roomService.removeResident(roomId);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,
            "A resident is removed from room with identifier " + roomId, room)).build();
    }

    @GetMapping("/residents/assign/")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> assignResident(
        @RequestParam("room") String room,
        @RequestParam("resident") String resident
    ) {
        log.debug("REST request to assign resident: {} to room: {}", resident, room);
        Long roomId = Long.parseLong(room);
        roomService.assignResident(resident, roomId);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,
            "A resident: " + resident + " is assigned to room with identifier " + roomId, room))
            .build();
    }

    @GetMapping("/residents")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<Resident> findAllResident(){
        log.debug("REST request to get all residents");
        return residentService.findAll();
    }

    @GetMapping("/residents/{email}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Resident findResidentByEmail(@PathVariable String email){
        log.debug("REST request to get residents belongs to email: {}",email);
        return residentService.findByEmail(email);
    }

    @GetMapping("/room")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<Room> findAllRoom(){
        log.debug("REST request to get all room");
        return roomService.findAll();
    }

}
