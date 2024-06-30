package com.studyretro.repository;

import com.studyretro.entity.Address;
import com.studyretro.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    List<Address> findByUser(Users user);
}
