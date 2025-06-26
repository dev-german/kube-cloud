package com.sunat.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunat.citas.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>  { 

}
