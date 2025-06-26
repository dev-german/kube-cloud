package com.sunat.medico.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunat.medico.entities.Medico;
import com.sunat.medico.service.IMedicoService;


@RestController
@RequestMapping("/medico")
public class MedicoController {

	private IMedicoService medicoService;

	public MedicoController(IMedicoService medicoService) {
		this.medicoService = medicoService;
	}

	@GetMapping
	public Map<String, Object> listarTodos() {
		return Map.of("medicos", medicoService.listarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerMedicoPorId(@PathVariable("id") Long id) {
		return medicoService.obtenerMedicoPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); 
	}

	@PostMapping
	public ResponseEntity<Medico> agregarMedico(@RequestBody Medico medico) {
		return ResponseEntity.ok(medicoService.guardarMedico(medico));
	}

}
