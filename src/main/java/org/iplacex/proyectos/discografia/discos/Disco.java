package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("discos")
public class Disco {
    @Id
    @Field("_id")               public String _id;              // id del disco
    @Field("idArtista")         public String idArtista;        // id del artista (referencia)
    @Field("nombre")            public String nombre;           // nombre del disco
    @Field("anioLanzamiento")   public int anioLanzamiento;     // año de lanzamiento
    @Field("canciones")         public List<String> canciones;  // lista de canciones
}