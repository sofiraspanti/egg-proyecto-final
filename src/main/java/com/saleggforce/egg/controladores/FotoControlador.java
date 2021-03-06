package com.saleggforce.egg.controladores;

import com.saleggforce.egg.entidades.Producto;
import com.saleggforce.egg.servicios.ProductoServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fotos")
public class FotoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/fotoproducto/{id}")
    public ResponseEntity<byte[]> fotoProducto(@PathVariable String id) throws Exception {

        Producto producto = productoServicio.getOne(id);
        if (producto == null) {
            throw new Exception("No se encontro ningun producto");
        }

        try {
            if (producto.getFoto() == null) {
                throw new Exception("El producto no tiene una foto asignada.");
            }
            byte[] foto = producto.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
