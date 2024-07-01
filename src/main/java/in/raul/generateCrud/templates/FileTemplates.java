package in.raul.generateCrud.templates;

public class FileTemplates {

	public FileTemplates(String name, String packageName ) {
		this.CONTROLLER = CONTROLLER.replace("{name}", name);
		this.CONTROLLER = CONTROLLER.replace("{packageName}", packageName);
		
		this.DTO = DTO.replace("{name}", name);
		this.DTO = DTO.replace("{packageName}", packageName);

		this.DAO = DAO.replace("{name}", name);
		this.DAO = DAO.replace("{packageName}", packageName);

		this.ENTITY = ENTITY.replace("{name}", name);
		this.ENTITY = ENTITY.replace("{packageName}", packageName);

		this.IMPLEMENT = IMPLEMENT.replace("{name}", name);
		this.IMPLEMENT = IMPLEMENT.replace("{packageName}", packageName);

		this.SERVICE = SERVICE.replace("{name}", name);
		this.SERVICE = SERVICE.replace("{packageName}", packageName);
	}
	
	public String getControllerTemplate() {
		return CONTROLLER;
	}

	public String getDtoTemplate() {
		return DTO;
	}

	public String getDaoTemplate() {
		return DAO;
	}

	public String getEntityTemplate() {
		return ENTITY;
	}

	public String getImplementTemplate() {
		return IMPLEMENT;
	}

	public String getServiceTemplate() {
		return SERVICE;
	}

	private String CONTROLLER = """
			package {packageName}.controllers;

			import java.util.List;

			import org.springframework.http.ResponseEntity;
			import org.springframework.web.bind.annotation.DeleteMapping;
			import org.springframework.web.bind.annotation.GetMapping;
			import org.springframework.web.bind.annotation.PatchMapping;
			import org.springframework.web.bind.annotation.PathVariable;
			import org.springframework.web.bind.annotation.PostMapping;
			import org.springframework.web.bind.annotation.PutMapping;
			import org.springframework.web.bind.annotation.RequestBody;
			import org.springframework.web.bind.annotation.RequestMapping;
			import org.springframework.web.bind.annotation.RestController;

			import {packageName}.dto.{name}DTO;
			import {packageName}.models.entity.{name};
			import {packageName}.models.service.I{name};

			@RestController
			@RequestMapping("/api/v1/{name}")
			public class {name}Controller {

				private I{name} service;
				public {name}Controller(I{name} service) {
					this.service = service;
				}

				@GetMapping
				public ResponseEntity<List<{name}DTO>> findAll(){
					List<{name}> list = service.findAll();
					return ResponseEntity.ok( list.stream().map( {name}::toDTO ).toList() );
				}
				
				@GetMapping("{id}")
				public ResponseEntity<{name}DTO> findById(@PathVariable Long id){
					{name} entity = service.findById(id);
					
					if(entity == null) {
						return ResponseEntity.ok().build();
					}
					
					return ResponseEntity.ok(entity.toDTO());
				}
				
				@PostMapping
				public ResponseEntity<Long> create({name}DTO dto){
					Long id = service.save(dto.toEntity());
					
					return ResponseEntity.ok(id);
				}
				
				@PutMapping("/{id}")
				public ResponseEntity<{name}DTO> update( @PathVariable Long id, @RequestBody {name}DTO dto ){

					if(service.findById(id) == null) {
						return ResponseEntity.notFound().build();
					}
					
					{name} {name} = dto.toEntity();
					{name}.setId(id);
					
					service.save({name});
					return ResponseEntity.ok({name}.toDTO());
				}
				
				@PatchMapping("/{id}")
				public ResponseEntity<{name}DTO> patch( @PathVariable Long id, @RequestBody {name}DTO dto ){

					if(service.findById(id) == null) {
						return ResponseEntity.notFound().build();
					}
					
					//TODO Implements this method when you adds attributes
					throw new Error("Method not implement");
				}
				
				@DeleteMapping("{id}")
				public ResponseEntity<Void> deleteById(@PathVariable Long id){
					service.deleteById(id);
					return ResponseEntity.ok().build();
				}
			}
							""";
	
	private String DTO ="""
			package {packageName}.dto;

			import {packageName}.models.entity.{name};

			public class {name}DTO {
				private Long id;

				public {name}DTO() {
				}

				public Long getId() {
					return id;
				}

				public void setId(Long id) {
					this.id = id;
				}
				
				public {name} toEntity() {
					//TODO Implement this method when you add the attributes
					return new {name}();
				}
			}
							""";

	private String DAO = """
			package {packageName}.models.dao;

			import org.springframework.data.repository.CrudRepository;

			import {packageName}.models.entity.{name};

			public interface I{name}Dao extends CrudRepository<{name}, Long>{

			}

							""";
	
	private String ENTITY = """
			package {packageName}.models.entity;

			import {packageName}.dto.{name}DTO;
			import jakarta.persistence.Id;
			import jakarta.persistence.Entity;
			import jakarta.persistence.GeneratedValue;
			import jakarta.persistence.GenerationType;
			import jakarta.persistence.Table;

			@Entity
			@Table(name = "{name}")
			public class {name} {

				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				private Long id;

				public {name}() {
				}

				public Long getId() {
					return id;
				}

				public void setId(Long id) {
					this.id = id;
				}
				
				public {name}DTO toDTO() {
					//TODO Implements this method when you adds attributes
					return new {name}DTO();
				}
			}
							""";

	private String IMPLEMENT = """
			package {packageName}.models.implement;

			import java.util.List;

			import org.springframework.stereotype.Service;

			import {packageName}.models.dao.I{name}Dao;
			import {packageName}.models.entity.{name};
			import {packageName}.models.service.I{name};

			@Service
			public class I{name}Impl implements I{name}{

				private I{name}Dao dao;
				
				public I{name}Impl(I{name}Dao dao) {
					this.dao = dao;
				}
				
				@Override
				public List<{name}> findAll() {
					return (List<{name}>) dao.findAll();
				}

				@Override
				public {name} findById(Long id) {
					return dao.findById(id).orElse(null);
				}

				@Override
				public Long save({name} entity) {
					return dao.save(entity).getId();
				}

				@Override
				public void deleteById(Long id) {
					
				}

			}
							""";

	private String SERVICE = """
			package {packageName}.models.service;

			import java.util.List;

			import {packageName}.models.entity.{name};

			public interface I{name} {
				
				public List<{name}> findAll();
				public {name} findById(Long id);
				public Long save({name} entity);
				public void deleteById(Long id);
			}
							""";
}
