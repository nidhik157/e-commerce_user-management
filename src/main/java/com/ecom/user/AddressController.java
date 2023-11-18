package com.ecom.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;
    private UserService userService;
    private UserRepository userRepository;
 
    public AddressController(AddressService addressService, UserService userService, UserRepository userRepository) {
    	this.addressService=addressService;
        this.userService = userService;
        this.userRepository=userRepository;
    }
    
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Address> createAddress(@RequestHeader(name = "Authorization") String token,@RequestBody @Valid Address address) {
       
    	Long userId=userService.getUserIdFromToken(token, "main");
    	if(userId==null) {return ResponseEntity.notFound().build();}
        address.setUserId(userId);
        Address createdAddress = addressService.createAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Address> updateAddress(@RequestHeader(name = "Authorization") String token,@PathVariable Long id, @RequestBody @Valid Address address) {
    	Long userId=userService.getUserIdFromToken(token, "main");
    	if(userId==null) {return ResponseEntity.notFound().build();}
Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found");
        }
        
        Optional<Address> addressOptional = addressService.findById(id);
        if (addressOptional.isEmpty()) {
            throw new CustomNotFoundException("Address not found");
        }
        
        Address existingAddress = addressOptional.get();
        if (existingAddress == null) {
       	 throw new CustomNotFoundException("Address not found");
       }
        if(existingAddress.getUserId()==userId) {
        

       if(address.getCity()!=null)
       {
    	   existingAddress.setCity(address.getCity());
       }
       if(address.getState()!=null)
       {
    	   existingAddress.setState(address.getState());
       }
       if(address.getCountry()!=null)
       {
    	   existingAddress.setCountry(address.getCountry());
       }
       if(address.getFlatNo()!=null)
       {
    	   existingAddress.setFlatNo(address.getFlatNo());
       }
       if(address.getLandmark()!=null)
       {
    	   existingAddress.setLandmark(address.getLandmark());
       }
       if(address.getName()!=null)
       {
    	   existingAddress.setName(address.getName());
       }
       if(address.getPhoneNumber()!=null)
       {
    	   existingAddress.setPhoneNumber(address.getPhoneNumber());
       }
       if(address.getPincode()!=null)
       {
    	   existingAddress.setPincode(address.getPincode());
       }
       
       if(address.getStreetAddress()!=null)
       {
    	   existingAddress.setStreetAddress(address.getStreetAddress());
       }
       
        
        Address updatedAddress = addressService.updateAddress(existingAddress);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);}
        else {
        	 throw new CustomNotFoundException("Address not found");
        }
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public List<Address> getUserAddresses(@RequestHeader(name = "Authorization") String token) {
    	Long userId=userService.getUserIdFromToken(token, "main");
    	Optional<User> userOptional = userRepository.findById(userId);
    	        
    	        if (userOptional.isEmpty()) {
    	            throw new CustomNotFoundException("User not found");
    	        }
        return addressService.getUserAddresses(userId);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteAddress(@RequestHeader(name = "Authorization") String token,@PathVariable Long id) {
    	Long userId=userService.getUserIdFromToken(token, "main");
    	if(userId==null) {return ResponseEntity.notFound().build();}
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found");
        }
        Optional<Address> existingAddress = addressService.findById(id);
        if (existingAddress == null) {
        	 throw new CustomNotFoundException("Address not found");
        }

       Address address=existingAddress.get();
       if(address.getUserId()==userId)

        {addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
       else  throw new CustomNotFoundException("Address not found");
    }
}
