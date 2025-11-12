package org.iplacex.proyectos.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    // Conecta el repositorio de artistas
    @Autowired 
    private IArtistaRepository artistaRepo;

    // POST /api/artista -> crea un nuevo artista
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        try {
            Artista creado = artistaRepo.insert(artista);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear artista", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/artistas -> lista todos los artistas
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        List<Artista> lista = artistaRepo.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // GET /api/artista/{id} -> busca un artista por su id
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> temp = artistaRepo.findById(id);
        if (temp.isPresent()) {
            return new ResponseEntity<>(temp.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
    }

    // PUT /api/artista/{id} -> actualiza un artista existente
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable("id") String id,
                                                             @RequestBody Artista datos) {
        try {
            if (!artistaRepo.existsById(id)) {
                return new ResponseEntity<>("Artista no existe", HttpStatus.NOT_FOUND);
            }
            datos._id = id;
            Artista actualizado = artistaRepo.save(datos);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar artista", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE /api/artista/{id} -> elimina un artista
    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
        try {
            if (!artistaRepo.existsById(id)) {
                return new ResponseEntity<>("Artista no existe", HttpStatus.NOT_FOUND);
            }
            artistaRepo.deleteById(id);
            return new ResponseEntity<>("Artista eliminado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar artista", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}