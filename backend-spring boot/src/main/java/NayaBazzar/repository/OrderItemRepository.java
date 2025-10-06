package NayaBazzar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import NayaBazzar.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
