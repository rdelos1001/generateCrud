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

			import java.util.GregorianCalendar;
			import java.util.List;
			import java.util.Optional;
			import java.util.stream.Collectors;
			
			import org.apache.logging.log4j.LogManager;
			import org.apache.logging.log4j.Logger;
			import org.springframework.http.HttpStatus;
			import org.springframework.http.ResponseEntity;
			import org.springframework.web.bind.annotation.DeleteMapping;
			import org.springframework.web.bind.annotation.GetMapping;
			import org.springframework.web.bind.annotation.PatchMapping;
			import org.springframework.web.bind.annotation.PathVariable;
			import org.springframework.web.bind.annotation.PostMapping;
			import org.springframework.web.bind.annotation.PutMapping;
			import org.springframework.web.bind.annotation.RequestBody;
			import org.springframework.web.bind.annotation.RequestHeader;
			import org.springframework.web.bind.annotation.RequestMapping;
			import org.springframework.web.bind.annotation.RequestParam;
			import org.springframework.web.bind.annotation.RestController;
			
			import {packageName}.auth.service.TokenService;
			import {packageName}.models.entity.ErrorType;
			import {packageName}.models.entity.{name};
			import {packageName}.models.entity.User;
			import {packageName}.models.service.IErrorType;
			import {packageName}.models.service.IUser;
			import {packageName}.utils.Utils;
			
			import io.swagger.v3.oas.annotations.Operation;
			import io.swagger.v3.oas.annotations.Parameter;
			import io.swagger.v3.oas.annotations.enums.ParameterIn;
			import io.swagger.v3.oas.annotations.responses.ApiResponse;

			import {packageName}.models.dto.{name}DTO;
			import {packageName}.models.service.I{name};

			@RestController
			@RequestMapping("/api/v1/{name}/")
			public class {name}Controller {

				private I{name} service;
				
				private TokenService tokenService;
				private IErrorType errorService;
				private IUser userService;
				private static final Logger logger = LogManager.getLogger( {name}Controller.class );
				
				public {name}Controller(I{name} service, TokenService tokenService, IErrorType errorService, IUser userService) {
					this.service = service;
					this.tokenService = tokenService;
					this.errorService = errorService;
					this.userService = userService;
				}
				
				/**
					 * Get all 
					 * @see {name}DTO
					 * @return
				*/
				@Operation(summary = "Get all {name}", tags = {"{name}"},
					responses = {
							@ApiResponse(responseCode = "200", description = "List of {name}", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class)))
					},
					parameters = {
							@Parameter(name = "fields", description = "Optional parameter to specify which fields to include in the response.", in = ParameterIn.QUERY)
					}
				)
				@GetMapping
				@Sort
				@FieldFilter
				public ResponseEntity<List<{name}DTO>> findAll(@RequestParam("fields") Optional<String[]> fieldsOPT,@RequestParam("sort_by") Optional<String> sortByOpt){
					List<{name}> list = service.findAll();
					
					List<{name}DTO> dtos = list.stream().map( {name}::toDTO ).collect(Collectors.toList());
					
					return ResponseEntity.ok( dtos );
				}
				
				/**
				 * Get {name} by id
				 * @param id
				 * @param fieldsOPT Fields to filter
				 * @return
				 */
			
				@Operation(summary = "Get {name} by id", tags = {"{name}"},
					responses = {
							@ApiResponse(responseCode = "200", description = "{name} found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class))),
							@ApiResponse(responseCode = "404", description = "{name} not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class)))
					},
					parameters = {
							@Parameter(name = "fields", description = "Optional parameter to specify which fields to include in the response.", in = ParameterIn.QUERY)
					}
				)
				@GetMapping("{id}/")
				public ResponseEntity<?> findById(@PathVariable Long id){
					{name} entity = service.findById(id);
					
					if(entity == null) {
						ErrorType error = this.errorService.findByError("RESOURCE_NOT_FOUND");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).body(error);
					}
					
					return ResponseEntity.ok(entity.toDTO());
				}
				
				/**
				 * Create a new {name}
				 * @param dto
				 * @return
				 */
				@Operation(summary = "Create a new {name}", tags = {"{name}"},
					responses = {
							@ApiResponse(responseCode = "200", description = "{name} created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class))),
							@ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
							@ApiResponse(responseCode = "400", description = "Bad request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
							@ApiResponse(responseCode = "409", description = "Conflict", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class)))
					}
				)
				@PostMapping
				public ResponseEntity<?> create(@RequestBody {name}DTO dto, @RequestHeader("Authorization") String token){

					String userId = tokenService.findUserIdByToken(token);
					if (userId == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						
						return ResponseEntity.status(error.getStatus()).body( error );
					}
			
					User user = userService.findById(userId);
			
					if (user == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).body( error );
					}
					{name} entity = new {name}();
					//TODO: Set the entity attributes.
					Long id = service.save(entity);
					logger.info("The user {} created a new {name} with id {}", user.getId(), id);
					{name}DTO response = new {name}DTO();
					response.setId(id);
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
				}
				
				
				/**
				 * Update a {name}
				 * @param id
				 * @param dto
				 * @return
				 */
				@Operation(summary = "Update a {name}", tags = {"{name}"},
					responses = {
						@ApiResponse(responseCode = "200", description = "{name} updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class))),
						@ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
						@ApiResponse(responseCode = "404", description = "{name} not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
						@ApiResponse(responseCode = "400", description = "Bad request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class)))
					},
					parameters = {
							@Parameter(name = "Authorization", description = "Token", required = true, in = ParameterIn.HEADER)
					},
					requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class)), required = true)
				)
				@PutMapping("{id}/")
				public ResponseEntity<?> update( @PathVariable Long id, @RequestBody {name}DTO dto, @RequestHeader("Authorization") String token ){

					String userId = tokenService.findUserIdByToken(token);
					if (userId == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						
						return ResponseEntity.status(error.getStatus()).body( error );
					}
			
					User user = userService.findById(userId);
			
					if (user == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).body( error );
					}
					{name} entity = service.findById(id);
					if( entity == null) {
						ErrorType error = this.errorService.findByError("RESOURCE_NOT_FOUND");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).body(error);
					}
					
					//TODO: Set the entity attributes.
					service.save(entity);
					logger.info("The user {} updated the {name} with id {}", user.getId(), id);
					return ResponseEntity.ok(entity.toDTO());
				}
				/**
				 * Update a {name} partially
				 * @param id
				 * @param dto
				 * @param token
				 * @return
				 */
			 	@Operation(summary = "Update a {name} partially", tags = {"{name}"},
					responses = {
							@ApiResponse(responseCode = "200", description = "{name} updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class))),
							@ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
							@ApiResponse(responseCode = "404", description = "{name} not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
							@ApiResponse(responseCode = "400", description = "Bad request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class))),
							@ApiResponse(responseCode = "409", description = "Conflict", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class)))	
					},
					parameters = {
							@Parameter(name = "Authorization", description = "Token", required = true, in = ParameterIn.HEADER)
					},
					requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = {name}DTO.class)), required = true)
				)
				@PatchMapping("{id}/")
				public ResponseEntity<?> patch( @PathVariable Long id, @RequestBody {name}DTO dto, @RequestHeader("Authorization") String token ){
					String userId = tokenService.findUserIdByToken(token);
					if (userId == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						
						return ResponseEntity.status(error.getStatus()).body( error );
					}
			
					User user = userService.findById(userId);
					if (user == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).body( error );
					}
					{name} entity =	service.findById(id);
					if( entity == null) {
						ErrorType error = this.errorService.findByError("RESOURCE_NOT_FOUND");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).body(error);
					}
					
					//TODO: Set the entity attributes.
					
					service.save(entity);
					logger.info("The user {} updated the {name} with id {}", user.getId(), id);
					
					return ResponseEntity.ok(entity.toDTO());
				}
				/**
				 * Delete a {name} by id
				 * @param id
				 * @return
				 */
				@Operation(summary = "Delete a {name} by id", tags = {"{name}"},
					responses = {
							@ApiResponse(responseCode = "204", description = "{name} deleted"),
							@ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorType.class)))
					}
				)
				@DeleteMapping("{id}/")
				public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestHeader("Authorization") String token){
					String userId = tokenService.findUserIdByToken(token);
					if (userId == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						
						return ResponseEntity.status(error.getStatus()).build();
					}
			
					User user = userService.findById(userId);
			
					if (user == null) {
						ErrorType error = errorService.findByError("AUTHENTICATION_FAILED");
						logger.error(error.getMessage());
						return ResponseEntity.status(error.getStatus()).build();
					}
					service.deleteById(id);
					logger.info("The user {} deleted the {name} with id {}", user.getId(), id);
					return ResponseEntity.noContent().build();
				}
			}
							""";
	
	private String DTO ="""
			package {packageName}.models.dto;
			import com.fasterxml.jackson.annotation.JsonInclude;
			import {packageName}.models.entity.{name};
			
			@JsonInclude(JsonInclude.Include.NON_NULL)
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

			import {packageName}.models.dto.{name}DTO;
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
					dao.deleteById(id);
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
