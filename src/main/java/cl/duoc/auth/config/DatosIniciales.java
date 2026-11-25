package cl.duoc.auth.config;

import cl.duoc.auth.model.Usuario;
import cl.duoc.auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!prod")
public class DatosIniciales {
  @Bean
  CommandLineRunner usuarioInicial(UsuarioRepository repo, PasswordEncoder encoder) {
    return args -> {
      Usuario admin = repo.findByCorreoIgnoreCase("admin@veterinariasanmarcos.cl").orElseGet(Usuario::new);
      admin.setNombre("Administrador San Marcos");
      admin.setCorreo("admin@veterinariasanmarcos.cl");
      admin.setPassword(encoder.encode("Cambiar123!"));
      admin.setRegion("Región del Libertador General Bernardo O'Higgins");
      admin.setComuna("Rancagua");
      admin.setRol("ADMIN");
      admin.setActivo(true);
      repo.save(admin);

      Usuario recep = repo.findByCorreoIgnoreCase("recepcion@veterinariasanmarcos.cl").orElseGet(Usuario::new);
      recep.setNombre("Recepcionista Clínica");
      recep.setCorreo("recepcion@veterinariasanmarcos.cl");
      recep.setPassword(encoder.encode("Recepcion123!"));
      recep.setRegion("Región del Libertador General Bernardo O'Higgins");
      recep.setComuna("Rancagua");
      recep.setRol("RECEPCIONISTA");
      recep.setActivo(true);
      repo.save(recep);

      Usuario user = repo.findByCorreoIgnoreCase("cliente@veterinariasanmarcos.cl").orElseGet(Usuario::new);
      user.setNombre("Cliente Demo");
      user.setCorreo("cliente@veterinariasanmarcos.cl");
      user.setPassword(encoder.encode("Cliente123!"));
      user.setRegion("Región del Libertador General Bernardo O'Higgins");
      user.setComuna("Rancagua");
      user.setRol("USER");
      user.setActivo(true);
      repo.save(user);

      Usuario vet = repo.findByCorreoIgnoreCase("veterinario@veterinariasanmarcos.cl").orElseGet(Usuario::new);
      vet.setNombre("Dr. Carlos Mendoza");
      vet.setCorreo("veterinario@veterinariasanmarcos.cl");
      vet.setPassword(encoder.encode("Veterinario123!"));
      vet.setRegion("Región del Libertador General Bernardo O'Higgins");
      vet.setComuna("Rancagua");
      vet.setRol("VETERINARIO");
      vet.setActivo(true);
      repo.save(vet);
    };
  }
}