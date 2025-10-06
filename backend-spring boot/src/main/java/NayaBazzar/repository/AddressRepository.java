package NayaBazzar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import NayaBazzar.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
