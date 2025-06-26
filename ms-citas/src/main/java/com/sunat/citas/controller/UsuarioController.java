package com.sunat.citas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunat.citas.entities.Usuario;
import com.sunat.citas.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private IUsuarioService usuarioService;

	public UsuarioController(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public Map<String, Object> listarTodos() {
		return Map.of("usuarios", usuarioService.listarTodos());
	}

	@PostMapping
	public ResponseEntity<Usuario> agregaUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
	}

}
