package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    // Repositorio de discos
    @Autowired
    private IDiscoRepository discoRepo;

    // Repositorio de artistas (para validar idArtista)
    @Autowired
    private IArtistaRepository artistaRepo;

    // POST /api/disco -> crea un disco (valida que el artista exista)
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        try {
            // Rechaza si no viene idArtista o el artista no existe
            if (disco.idArtista == null || !artistaRepo.existsById(disco.idArtista)) {
                return new ResponseEntity<>("El artista no existe (idArtista inválido)", HttpStatus.BAD_REQUEST);
            }
            Disco creado = discoRepo.insert(disco);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear disco", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/discos -> lista todos los discos
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> lista = discoRepo.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // GET /api/disco/{id} -> busca un disco por id
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> temp = discoRepo.findById(id);
        if (temp.isPresent()) {
            return new ResponseEntity<>(temp.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND);
    }

    // GET /api/artista/{id}/discos -> lista los discos de un artista
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
        List<Disco> discos = discoRepo.findDiscosByIdArtista(idArtista);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}