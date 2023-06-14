package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserCreateEditDto;
import com.example.dto.UserReadDto;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toUser)
                .map(userRepository::save)
                .map(userMapper::toUserDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userMapper.updateUserFromDto(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userMapper::toUserDto);
    }

    @Transactional
    public Optional<UserReadDto> login(LoginDto userDto) {
        return userRepository.findByUsernameAndPassword(
                userDto.getUsername(),
                userDto.getPassword()
        ).map(userMapper::toUserDto);
    }


    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
