package com.jhipster.demo.service;

import com.jhipster.demo.domain.Facility;
import com.jhipster.demo.domain.Room;
import com.jhipster.demo.repository.FacilityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private RoomService roomService;

    public List<Facility> findAll(){
        return facilityRepository.findAll();
    }

    @Transactional
    public boolean addRoom(long roomNo, long facilityId){
        Room room = roomService.findById(roomNo);
        Facility facility = facilityRepository.getOne(facilityId);
        facility.getRooms().add(room);
        return true;
    }
}
