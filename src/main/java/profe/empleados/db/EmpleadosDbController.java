package profe.empleados.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import profe.empleados.model.Empleado;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/")
public class EmpleadosDbController {

	private Map<String, Empleado> mpEmpleados = new HashMap();
	
	private Logger logger = Logger.getLogger(EmpleadosDbController.class
			.getName());
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "empleados-db");
		SpringApplication.run(EmpleadosDbController.class, args);
	}

	public EmpleadosDbController() {
		mpEmpleados.put("23412312H", 
				new Empleado("23412312H", "Javier", "Pascual",
						23));
		mpEmpleados.put("123452435T", new Empleado("123452435T", "Esthela", "Ruíz", 54)); 
		mpEmpleados.put("223452435A", new Empleado("223452435A", "Manuel", "Alonso", 64));   
		mpEmpleados.put("323452435B", new Empleado("323452435B", "Mirkka", "Touko", 22));    
		mpEmpleados.put("523452435S", new Empleado("523452435S", "Ethan", "Hawk", 47));      
		mpEmpleados.put("623452435D", new Empleado("623452435D", "Jesús", "Gutiérrez", 81));

	}
	
	@GetMapping
	public Collection<Empleado> getAllEmpleados() {
		return mpEmpleados.values();
	}
	
	@GetMapping("/{cif}")
	public Empleado getEmpleado(@PathVariable String cif) {
		Empleado empleado = mpEmpleados.get(cif);
		logger.info("Recuperando el empleado " + empleado);
		return empleado;
	}
	
	@PostMapping
	public Empleado insertaEmpleado(@RequestBody Empleado emp) {
		mpEmpleados.put(emp.getCif(), emp);
		return emp;
	}
	
	@PutMapping("/{cif}")
	public Empleado modificaEmpleado(@PathVariable String cif, 
			@RequestBody Empleado emp) {
		mpEmpleados.remove(cif);
		return this.insertaEmpleado(emp);
	}
	
	@DeleteMapping("/{cif}")
	public Empleado deleteEmpleado(@PathVariable String cif, 
			HttpServletResponse response) {
		if (!mpEmpleados.containsKey(cif)) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		return mpEmpleados.remove(cif);
	}
	
	
	
	
	
	
	
	
}
