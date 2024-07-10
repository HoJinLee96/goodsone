package trash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//NaverAddressSearch

//@RestController
//@RequestMapping("/api")
public class NASController {
  
  private NaverAddressSearchService NasServices;

//  @Autowired
  public NASController(NaverAddressSearchService nasServices) {
    super();
    NasServices = nasServices;
  } 
  
//  @PostMapping("/search/address")
  public ResponseEntity<?> searchAddress(@RequestParam String query){
    
    NasServices.searchAddress(query);
    return null;
  }
  
}
