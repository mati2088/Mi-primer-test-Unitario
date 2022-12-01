package com.app.test.productostest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest //indicamos que vamos hacer una prueba unitaria para una entidad
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoTest {

    @Autowired
    private ProductoRepositorio productoRepositorio; //Inyeccion de dependencias

    @Test  //indicamos que vamos hacer un test
    @Rollback(value = false)
    @Order(1)
    public void testGuardarProducto(){
        Producto producto=new Producto("producto 1",10);
        Producto productoGuardado= productoRepositorio.save(producto);
        assertNotNull(productoGuardado);

    }
    @Test
    @Order(2)
    public void testBuscarProductoPorNombre(){
        String nombre="producto 1";
        Producto producto=productoRepositorio.findByNombre(nombre);
        assertThat(producto.getNombre()).isEqualTo(nombre);
    }
    @Test //Da error si existe,ya que el valor tiene que ser nulo
    @Order(3)
    public void testBuscarProductoPorNombreNoExistente(){
        String nombre="producto no existe";
        Producto producto=productoRepositorio.findByNombre(nombre);
        assertNull(producto);
    }
    @Test
    @Rollback(value = false)
    @Order(4)
    public void testActualizarProducto(){
        String nombreProducto="producto 1 modificado";//nuevo valor
        Producto producto=new Producto(nombreProducto,1);//valores nuevos and actualizar
        producto.setId(9);//Id del producto que vamos a modificar

        productoRepositorio.save(producto);//guardamos los cambios

        Producto productoActualizado=productoRepositorio.findByNombre(nombreProducto);
        assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);
    }

    @Test
    @Order(5)
    public void testListarProductos(){
        List<Producto>productos=  (List<Producto>) productoRepositorio.findAll();
        for (Producto producto : productos){
            System.out.println(producto);
        }
        assertThat(productos).size().isGreaterThan(0);
    }
    @Test
    @Rollback(value = false)
    @Order(6)
    public void testeliminarProducto(){
       Integer id=13;
       boolean esExistenteAntesDeEliminar=productoRepositorio.findById(id).isPresent();//si existe te va a dar true

       productoRepositorio.deleteById(id);

        boolean noEsExisteDespuesDeEliminar=productoRepositorio.findById(id).isPresent();//cuando ya no exista te va a dar false

        assertTrue(esExistenteAntesDeEliminar);
        assertFalse(noEsExisteDespuesDeEliminar);
    }


}
