package NayaBazzar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import NayaBazzar.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	 Cart findByUserId(Long userId);
}
