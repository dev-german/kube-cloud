package com.sunat.citas.service;

import java.util.List;

import com.sunat.citas.entities.Usuario;

public interface IUsuarioService {
	public List<Usuario> listarTodos() ;

	public Usuario guardarUsuario(Usuario usuario);
}
