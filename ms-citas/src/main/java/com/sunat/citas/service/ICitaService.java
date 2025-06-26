package com.sunat.citas.service;

import java.util.List;

import com.sunat.citas.entities.Cita;

public interface ICitaService {
	public List<Cita> listarTodos() ;

	public Cita guardarCita(Cita cita);
}
