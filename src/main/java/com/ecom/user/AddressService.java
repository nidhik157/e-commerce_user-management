package com.ecom.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AddressService {
	
 private final AddressRepository addressRepository;
 private final UserRepository userRepository;
 

 public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
	this.addressRepository = addressRepository;
	this.userRepository = userRepository;
	}
 


 public Address createAddress(Address address) {
	 System.out.println(address);
     // Add validation logic here, if needed
	 Address newAddress=new Address();
	 newAddress.setCity(address.getCity());
	 newAddress.setCountry(address.getCountry());
	 newAddress.setFlatNo(address.getFlatNo());
	 System.out.println(address.getIsDefault());
	 newAddress.setIsDefault(address.getIsDefault());
	 newAddress.setLandmark(address.getLandmark());
	 newAddress.setName(address.getName());
	 newAddress.setPhoneNumber(address.getPhoneNumber());
	 newAddress.setPincode(address.getPincode());
	 newAddress.setState(address.getState());
	 newAddress.setStreetAddress(address.getStreetAddress());
	 newAddress.setUserId(address.getUserId());
	 
     return addressRepository.save(newAddress);
 }

 public Address updateAddress(Address address) {
     // Add validation and authorization logic here, if needed
     return addressRepository.save(address);
 }

 public void deleteAddress(Long id) {
     // Add authorization logic here, to check if the user has permission to delete
     addressRepository.deleteById(id);
 }

 public List<Address> getUserAddresses(Long userId) {
	 Optional<User> user = userRepository.findById(userId);

     if (user.isPresent()) {
         // If the user is found, get their addresses
         return addressRepository.findByUserId(userId);
     } else {
         // Handle if the user with the given ID is not found
         return new ArrayList<>(); // or throw an exception
     }
 }

public Optional<Address> findById(Long id) {
	// TODO Auto-generated method stub
	return addressRepository.findById(id);
}



}
