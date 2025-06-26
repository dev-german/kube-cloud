package com.sunat.medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunat.medico.entities.Medico;


public interface MedicoRepository extends JpaRepository<Medico, Long>  { 

}
