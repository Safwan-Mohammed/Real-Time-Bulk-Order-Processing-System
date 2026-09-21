package com.projects.retail.bulk_order.service;

import com.projects.retail.bulk_order.dto.request.user.UserRequestDTO;
import com.projects.retail.bulk_order.dto.response.GeneralResponseDTO;
import com.projects.retail.bulk_order.entity.UserEntity;
import com.projects.retail.bulk_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private <T> ResponseEntity<T> returnResponseEntity(HttpStatus status, T message){
        return ResponseEntity.status(status).body(message);
    }

    public ResponseEntity<GeneralResponseDTO> createAccount(UserRequestDTO userRequestDTO){
        try{
            UserEntity user = userRepository.findByEmail(userRequestDTO.getEmail());
            if(user != null){
                return returnResponseEntity(HttpStatus.BAD_REQUEST, GeneralResponseDTO.builder().message("The provided email already exists!").build());
            }
            String hashedPassword = bCryptPasswordEncoder.encode(userRequestDTO.getPassword());

            user = UserEntity.builder()
                    .email(userRequestDTO.getEmail())
                    .password(hashedPassword)
                    .name(userRequestDTO.getName())
                    .build();

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(GeneralResponseDTO.builder().message("Account Created Successfully!").build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error in the Operation : " + e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> updateAccount(UserRequestDTO userRequestDTO){
        try{
            UserEntity user = userRepository.findByEmail(userRequestDTO.getEmail());
            if(user == null){
                return returnResponseEntity(HttpStatus.BAD_REQUEST, GeneralResponseDTO.builder().message("Account does not exist!").build());
            }
            if(!bCryptPasswordEncoder.matches(userRequestDTO.getPassword(), user.getPassword()))
                user.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));
            if(!userRequestDTO.getName().equals(user.getName()))
                user.setName(userRequestDTO.getName());
            userRepository.save(user);
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("Account Updated Successfully").build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occured : " + e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> deleteAccount(UserRequestDTO userRequestDTO){
        try{
            UserEntity user = userRepository.findByEmail(userRequestDTO.getEmail());
            if(user == null)
                return returnResponseEntity(HttpStatus.NOT_FOUND, GeneralResponseDTO.builder().message("Account Does Not Exist!").build());
            if(!bCryptPasswordEncoder.matches(userRequestDTO.getPassword(), user.getPassword()))
                return returnResponseEntity(HttpStatus.BAD_REQUEST, GeneralResponseDTO.builder().message("Invalid Credentials").build());
            userRepository.delete(user);
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("Account Deleted Successfully!").build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occured : "+e.getMessage()).build());
        }
    }

}