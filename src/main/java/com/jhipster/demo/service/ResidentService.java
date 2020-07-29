package com.jhipster.demo.service;

import com.jhipster.demo.domain.Resident;
import com.jhipster.demo.repository.ResidentRepository;
import com.jhipster.demo.service.dto.ResidentDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository repo;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    public List<Resident> findAll(){
        return repo.findAll();
    }

    public Resident findByEmail(String email){
        return repo.findByEmail(email);
    }

    public Resident createResident(ResidentDTO residentDTO){
        Resident resident = new Resident();
        resident.setEmail(residentDTO.getEmail().toLowerCase());
        resident.setFirstName(residentDTO.getFirstName());
        resident.setLastName(residentDTO.getLastName());
        resident.setImageUrl(residentDTO.getImageUrl());
        repo.save(resident);
        log.debug("Created Information for Resident: {}", resident);
        return resident;
    }

}
