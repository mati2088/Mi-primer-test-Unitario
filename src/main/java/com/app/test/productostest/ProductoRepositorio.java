package com.app.test.productostest;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepositorio extends CrudRepository<Producto,Integer> {

       public      Producto findByNombre(String nombre);
}
