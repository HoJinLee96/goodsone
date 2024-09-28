package api;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dto.AddressDto;
import exception.NotFoundException;
import service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

  @Autowired
  AddressService addressService;
  
  @PostMapping("/register")
  public ResponseEntity<?> registerAddress(int userSeq, AddressDto addressDto) {
    try {
      addressService.registerAddress(userSeq, addressDto);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  @PostMapping("/get")
  public ResponseEntity<AddressDto> getAddress(int addressSeq) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=UTF-8");
    
    try {
      AddressDto addressDto = addressService.getAddressDtoByAddressSeq(addressSeq);
      return ResponseEntity.status(HttpStatus.OK).body(addressDto);
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } catch (NotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  @PostMapping("/getList")
  public ResponseEntity<List<AddressDto>> getListAddress(int userSeq) {
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=UTF-8");
    
    try {
      List<AddressDto> list = addressService.getListByUserSeq(userSeq);
      
      return ResponseEntity.status(HttpStatus.OK).body(list);
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } catch (NotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  @PostMapping("/update")
  public ResponseEntity<?> updateAddress(AddressDto addressDto) {
    try {
      addressService.updateAddress(addressDto);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  @PostMapping("/delete")
  public ResponseEntity<?> updateAddress(int addressSeq) {
    try {
      addressService.deleteAddress(addressSeq);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  
}
