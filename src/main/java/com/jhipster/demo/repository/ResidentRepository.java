package com.jhipster.demo.repository;

import com.jhipster.demo.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident,Long> {

    Resident findByEmail(String email);
}
