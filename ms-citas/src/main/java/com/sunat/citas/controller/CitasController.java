package com.sunat.citas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunat.citas.entities.Cita;
import com.sunat.citas.service.ICitaService;

@RestController
@RequestMapping("/citas")
public class CitasController {
	private ICitaService citaService;
	
	public CitasController(ICitaService citaService) {
		this.citaService = citaService;
	}
	
	@GetMapping
	public Map<String, Object> listarTodos() {
		return Map.of("citas", citaService.listarTodos());
	}
	
	@PostMapping
	public ResponseEntity<Cita> agregaCita(@RequestBody Cita cita) {
		return ResponseEntity.ok(citaService.guardarCita(cita));
	}

}
