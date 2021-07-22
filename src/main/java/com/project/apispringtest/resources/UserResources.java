package com.project.apispringtest.resources;

import com.project.apispringtest.domain.User;
import com.project.apispringtest.dto.UserDTO;
import com.project.apispringtest.services.UserServices;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
public class UserResources {
    
    @Autowired
    private UserServices uservices;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> findAll(){
 
        List<User> list = uservices.findAll();
        List<UserDTO> listdto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listdto);
        
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)    
    public ResponseEntity<UserDTO> findById(@PathVariable String id){       
        User obj = uservices.findById(id);        
        return ResponseEntity.ok().body(new UserDTO(obj));
        
    }
    
    @RequestMapping(method=RequestMethod.POST) 
    public ResponseEntity<Void> insert(@RequestBody UserDTO objdto) {
        User obj = uservices.fromDTO(objdto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }
}
