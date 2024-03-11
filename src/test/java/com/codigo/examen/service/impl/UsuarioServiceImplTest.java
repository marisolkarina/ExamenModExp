package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void crearPersonaSuccess() {
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setUsername("lola");
        usuarioNuevo.setPassword("password");
        usuarioNuevo.setEmail("lola@gmail.com");
        usuarioNuevo.setTelefono("7654321");

        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());

        ResponseEntity<Usuario> responseEntity = usuarioService.createUsuario(usuarioNuevo);

        assertDoesNotThrow(() -> {
            ResponseEntity<Usuario> response = usuarioService.createUsuario(usuarioNuevo);
        });
    }
    @Test
    void crearPersonaError() {
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setUsername("usuario1");

        when(usuarioRepository.findByUsername(usuarioNuevo.getUsername())).thenReturn(Optional.of(usuarioNuevo));

        ResponseEntity<Usuario> responseEntity = usuarioService.createUsuario(usuarioNuevo);

        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void obtenerUsuarioSuccess() {
        long userId = 1L;
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setIdUsuario(userId);
        usuarioEsperado.setUsername("usuario1");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEsperado));

        ResponseEntity<Usuario> responseEntity = usuarioService.getUsuarioById(userId);

        assertEquals(usuarioEsperado, responseEntity.getBody());
    }

    @Test
    void obtenerUsuarioNoExiste() {
        long userId = 2L;

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> responseEntity = usuarioService.getUsuarioById(userId);

        assertEquals(null, responseEntity.getBody());
    }
    @Test
    void actualizarUsuarioSuccess() {
        Long userId = 3L;
        Usuario existingUsuarioMock = new Usuario();
        existingUsuarioMock.setIdUsuario(userId);
        existingUsuarioMock.setUsername("usuarioExistente");

        Usuario updatedUsuarioMock = new Usuario();
        updatedUsuarioMock.setIdUsuario(userId);
        updatedUsuarioMock.setUsername("usuarioActualizado");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUsuarioMock));
        when(usuarioRepository.findByUsername(updatedUsuarioMock.getUsername())).thenReturn(Optional.empty());
        when(usuarioRepository.save(updatedUsuarioMock)).thenReturn(updatedUsuarioMock);

        ResponseEntity<Usuario> responseEntity = usuarioService.updateUsuario(userId, updatedUsuarioMock);

        assertEquals(updatedUsuarioMock, responseEntity.getBody());
    }

    @Test
    void eliminarUsuarioSuccess() {
        Long userId = 4L;
        Usuario existingUsuarioMock = new Usuario();
        existingUsuarioMock.setIdUsuario(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUsuarioMock));

        ResponseEntity<Usuario> responseEntity = usuarioService.deleteUsuario(userId);

        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void eliminarUsuarioNoExiste() {
        Long userId = 5L;

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> responseEntity = usuarioService.deleteUsuario(userId);

        assertEquals(null, responseEntity.getBody());
    }

}
