package com.sunat.citas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "deployment-1-service", url = "${ms-medicos.url}")
public interface MedicoFeignClient {

	@GetMapping("/medico/{id}")
	Medico obtenerMedicoPorId(@PathVariable("id") Long id);
	
	public class Medico {
		private Long id;	
		private String nombre;		
		private String especialidad;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getEspecialidad() {
			return especialidad;
		}

		public void setEspecialidad(String especialidad) {
			this.especialidad = especialidad;
		}
		
		

	}
}
