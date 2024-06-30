package com.studyretro.service;

import com.studyretro.entity.Address;
import com.studyretro.exceptions.AddressNotFoundException;
import com.studyretro.exceptions.InvalidUserException;

import java.util.List;

public interface AddressService {
    Address addAddress(Long userId, Address address) throws InvalidUserException;
    Address updateAddress(int addressId, Address address) throws AddressNotFoundException;
    void deleteAddress(int addressId) throws AddressNotFoundException;
    Address getAddressById(int addressId) throws AddressNotFoundException;
    List<Address> getAllAddressesForUser(int userId) throws InvalidUserException;
}