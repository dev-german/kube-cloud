package com.sunat.medico.service;

import java.util.List;
import java.util.Optional;

import com.sunat.medico.entities.Medico;


public interface IMedicoService {
	public List<Medico> listarTodos() ;

	public Medico guardarMedico(Medico medico);

	public Optional<Medico> obtenerMedicoPorId(Long id);
}
