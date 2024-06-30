package com.studyretro.controllers;

import com.studyretro.entity.Address;
import com.studyretro.exceptions.AddressNotFoundException;
import com.studyretro.exceptions.InvalidUserException;
import com.studyretro.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    @Autowired
    private final AddressService addressService;

    @PostMapping("/addAddress/{userId}")
    public ResponseEntity<?> addAddress(@PathVariable int userId, @RequestBody Address address) {
        try {
            Address newAddress = addressService.addAddress((long) userId, address);
            return ResponseEntity.ok(newAddress);
        } catch (InvalidUserException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable int addressId, @RequestBody Address address) {
        try {
            Address updatedAddress = addressService.updateAddress(addressId, address);
            return ResponseEntity.ok(updatedAddress);
        } catch (AddressNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable int addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok().body("Address deleted successfully");
        } catch (AddressNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable int addressId) {
        try {
            Address address = addressService.getAddressById(addressId);
            return ResponseEntity.ok(address);
        } catch (AddressNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllAddressesForUser(@PathVariable int userId) {
        try {
            List<Address> addresses = addressService.getAllAddressesForUser(userId);
            return ResponseEntity.ok(addresses);
        } catch (InvalidUserException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
