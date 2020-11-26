package be.howest.ti.mars.logic.data;


import be.howest.ti.mars.logic.domain.Product;

import java.util.List;

public interface ProductsRepository {
   void addProduct(Product product);
   List<Product> getProducts();
   List<Product> findProduct(String name);
}
