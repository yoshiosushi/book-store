package com.example.shop.service;

import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.model.entity.Address;
import com.example.shop.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public List<Address> findByUserId(Long userId){
         return addressRepository.findByUserId(userId);
    }

    public Address findByAddressId(Long addressId){
        if(addressId==null){
            return null;
        }
        Optional<Address> addressOpt = addressRepository.findByAddressId(addressId);
        if(addressOpt.isPresent()){
            return addressOpt.get();
        }else{
            throw new EntityNotFoundException("該当するアドレスが存在しませんでした。");
        }
    }

    public void addAddress(Long userId, String postalCodeF, String postalCodeB, String forwardAddress,
                           String lastName, String firstName, String phone){
        addressRepository.insertAddress(userId, postalCodeF, postalCodeB, forwardAddress,
                lastName, firstName, phone);
    }

    public void updateAddress(Long addressId, String postalCodeF, String postalCodeB, String forwardAddress,
                              String lastName, String firstName, String phone){
        addressRepository.updateAddress(addressId, postalCodeF, postalCodeB, forwardAddress,
                lastName, firstName, phone);
    }

    public void deleteAddress(Long addressId, String email){
        addressRepository.deleteAddress(addressId);
        if(addressId.equals(userService.findByEmail(email).getDefaultAddressId())){
            userService.updateDefaultAddress(email, null);
        }
    }

}
