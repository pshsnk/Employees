package com.innowise.employees.web;

import com.innowise.employees.dto.UserDTO;
import com.innowise.employees.mappers.UserMapper;
import com.innowise.employees.models.User;
import com.innowise.employees.services.UserService;
import com.innowise.employees.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;


    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUserProfile() {

        List<UserDTO> userDTOList = userService.getAllUsers()
                .stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult,
                                             @PathVariable String id) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, Long.parseLong(id));
        UserDTO userUpdated = UserMapper.INSTANCE.toDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
