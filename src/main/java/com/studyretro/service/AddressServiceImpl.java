package com.studyretro.service;

import com.studyretro.entity.Address;
import com.studyretro.entity.Users;
import com.studyretro.exceptions.AddressNotFoundException;
import com.studyretro.exceptions.InvalidUserException;
import com.studyretro.repository.AddressRepository;
import com.studyretro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService{

    @Autowired
    private final AddressRepository addressRepository;

    @Autowired
    private final UserRepository userRepository;
    @Override
    public Address addAddress(Long userId, Address address) throws InvalidUserException {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User with ID " + userId + " not found"));
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(int addressId, Address addressDetails) throws AddressNotFoundException {
        Address address = addressRepository.findById((long) addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + addressId + " not found"));
        address.setLine1(addressDetails.getLine1());
        address.setLine2(addressDetails.getLine2());
        address.setCity(addressDetails.getCity());
        address.setDistrict(addressDetails.getDistrict());
        address.setZip(addressDetails.getZip());
        address.setState(addressDetails.getState());
        address.setCountry(addressDetails.getCountry());
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(int addressId) throws AddressNotFoundException {
        Address address = addressRepository.findById((long) addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + addressId + " not found"));
        addressRepository.delete(address);
    }

    @Override
    public Address getAddressById(int addressId) throws AddressNotFoundException {
        return addressRepository.findById((long) addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + addressId + " not found"));
    }

    @Override
    public List<Address> getAllAddressesForUser(int userId) throws InvalidUserException {
        Users user = userRepository.findById((long) userId)
                .orElseThrow(() -> new InvalidUserException("User with ID " + userId + " not found"));
        return addressRepository.findByUser(user);
    }
}
