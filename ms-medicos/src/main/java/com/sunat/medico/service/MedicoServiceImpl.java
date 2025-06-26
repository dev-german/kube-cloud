package com.sunat.medico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sunat.medico.entities.Medico;
import com.sunat.medico.repository.MedicoRepository;


@Service
public class MedicoServiceImpl implements IMedicoService {
	
	private final MedicoRepository medicoRepository;
	
	public MedicoServiceImpl(MedicoRepository medicoRepository) {
		this.medicoRepository = medicoRepository;
	}

	@Override
	public List<Medico> listarTodos() {
		return medicoRepository.findAll();
	}

	@Override
	public Medico guardarMedico(Medico medico) {
		return medicoRepository.save(medico);
	}

	@Override
	public Optional<Medico> obtenerMedicoPorId(Long id) {
		return medicoRepository.findById(id);
	}

}
