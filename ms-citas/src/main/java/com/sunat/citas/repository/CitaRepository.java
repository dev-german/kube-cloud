package com.sunat.citas.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sunat.citas.entities.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>  { 	
	@Query("SELECT c FROM Cita c WHERE c.idUsuario = :idUsuario AND c.idMedico = :idMedico AND c.fecha BETWEEN :inicio AND :fin")
	List<Cita> buscarPorUsuarioMedicoYFechaDia(
	    @Param("idUsuario") Long idUsuario,
	    @Param("idMedico") Long idMedico,
	    @Param("inicio") Date inicio,
	    @Param("fin") Date fin
	);
}
