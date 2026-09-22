package com.projects.retail.bulk_order.controllers;

import com.projects.retail.bulk_order.dto.request.user.UserRequestDTO;
import com.projects.retail.bulk_order.dto.response.GeneralResponseDTO;
import com.projects.retail.bulk_order.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponseDTO> createAccount(@RequestBody UserRequestDTO userRequestDTO){
        return userService.createAccount(userRequestDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody UserRequestDTO userRequestDTO){
        return userService.updateAccount(userRequestDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestBody UserRequestDTO userRequestDTO){
        return userService.deleteAccount(userRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequestDTO){
        return userService.login(userRequestDTO);
    }
}
