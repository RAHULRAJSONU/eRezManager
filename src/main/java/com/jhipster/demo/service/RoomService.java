package com.jhipster.demo.service;


import com.jhipster.demo.domain.Resident;
import com.jhipster.demo.domain.Room;
import com.jhipster.demo.repository.ResidentRepository;
import com.jhipster.demo.repository.RoomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ResidentRepository residentRepository;

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room findById(Long id){
        return roomRepository.findById(id).get();
    }

    @Transactional
    public boolean assignResident(String email, Long roomNo){
        Resident resident = residentRepository.findByEmail(email);
        Room room = findById(roomNo);
        room.setResident(resident);
        return true;
    }

    @Transactional
    public boolean removeResident(Long roomNo){
        Room room = findById(roomNo);
        room.setResident(null);
        return true;
    }

    public Room createRoom(Room room){
        return roomRepository.save(room);
    }
}
