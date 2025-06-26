package com.sunat.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sunat.citas.entities.Usuario;
import com.sunat.citas.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

}
