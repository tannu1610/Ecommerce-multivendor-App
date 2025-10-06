package NayaBazzar.repository;

import NayaBazzar.model.Cart;
import NayaBazzar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import NayaBazzar.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);


}
