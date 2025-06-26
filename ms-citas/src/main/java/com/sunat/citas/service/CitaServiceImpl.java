package com.sunat.citas.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sunat.citas.entities.Cita;
import com.sunat.citas.entities.Usuario;
import com.sunat.citas.exception.RecursoNoProcesableException;
import com.sunat.citas.feign.MedicoFeignClient;
import com.sunat.citas.repository.CitaRepository;
import com.sunat.citas.repository.UsuarioRepository;

@Service
public class CitaServiceImpl implements ICitaService {

	private final CitaRepository citaRepository;

	private final UsuarioRepository usuarioRepository;

	private final MedicoFeignClient medicoFeignClient;

	public CitaServiceImpl(MedicoFeignClient medicoFeignClient, CitaRepository citaRepository,
			UsuarioRepository usuarioRepository) {
		this.citaRepository = citaRepository;
		this.medicoFeignClient = medicoFeignClient;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public List<Cita> listarTodos() {
		return citaRepository.findAll();
	}

	@Override
	public Cita guardarCita(Cita cita) {
		MedicoFeignClient.Medico medico = null;
		medico = medicoFeignClient.obtenerMedicoPorId(cita.getIdMedico());
		
		Optional<Usuario> usuario = usuarioRepository.findById(cita.getIdUsuario());
		if (usuario == null || usuario.isEmpty())
			throw new RecursoNoProcesableException("El usuario no existe.");
		
		ZoneId zona = ZoneId.of("America/Lima");
	    LocalDate localDate = cita.getFecha().toInstant().atZone(zona).toLocalDate();

	    Date inicio = Date.from(localDate.atStartOfDay(zona).toInstant());
	    Date fin = Date.from(localDate.atTime(23, 59, 59).atZone(zona).toInstant());
		List<Cita> citas = citaRepository.buscarPorUsuarioMedicoYFechaDia(cita.getIdUsuario(), cita.getIdMedico(), inicio, fin);
		
		if(!citas.isEmpty())
			throw new RecursoNoProcesableException("El usuario ya tiene una cita registrada en el día.");

		return citaRepository.save(cita);

	}

}
