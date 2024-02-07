package com.valeria.bookshop.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.valeria.bookshop.db.entity.UserEntity;
import com.valeria.bookshop.db.entity.UserRole;
import com.valeria.bookshop.db.repository.BucketRepository;
import com.valeria.bookshop.db.repository.UserRepository;
import com.valeria.bookshop.dto.UserDTO;
import com.valeria.bookshop.exception.ApiException;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.UserMapper;
import com.valeria.bookshop.request.CreateUserRequest;
import com.valeria.bookshop.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FirebaseAuth firebaseAuth;
    private BucketRepository bucketRepository;

    public List<UserDTO> getAllUsers() {
        return userMapper.mapToDTOList(userRepository.findAll());
    }

    public void addNewUserIfNeed(JwtAuthentication jwtAuthentication) {
        Optional<UserEntity> userEntityOptional = userRepository.findByFirebaseIdentityId(jwtAuthentication.getPrincipal());
        if (userEntityOptional.isEmpty()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(jwtAuthentication.getEmail());
            userEntity.setFirebaseIdentityId(jwtAuthentication.getPrincipal());
            userEntity.setRole(UserRole.ROLE_USER);
            userRepository.save(userEntity);
        }
    }

    public Set<UserRole> getRolesForUserByIdentityId(String uid) {
        return userRepository.findByFirebaseIdentityId(uid)
                .map(userEntity -> Set.of(userEntity.getRole()))
                .orElse(Set.of());
    }

    public UserEntity getCurrentAuthorizedUserEntity() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByFirebaseIdentityId(authentication.getPrincipal())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    public List<UserEntity> getAllUsersByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }


    public UserDTO addNewUser(CreateUserRequest request) throws FirebaseAuthException {
        userRepository.findByEmail(request.email()).ifPresent(userEntity -> {
            throw new BadRequestException("Пользователь с почтой " + request.email() + " уже существует");
        });
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(request.email())
                .setPassword(request.password());
        UserRecord userRecord = firebaseAuth.createUser(createRequest);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRecord.getEmail());
        userEntity.setFirebaseIdentityId(userRecord.getUid());
        userEntity.setRole(request.role());
        userEntity = userRepository.save(userEntity);
        return userMapper.mapToDTO(userEntity);
    }


    public void deleteUser(Long userId) throws FirebaseAuthException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not exists"));
        bucketRepository.deleteAll(bucketRepository.findAllByUserEntity(userEntity));
        userRepository.delete(userEntity);
        firebaseAuth.deleteUser(userEntity.getFirebaseIdentityId());
    }

    @PostConstruct
    void postConstruct() {
        String adminEmail = "admin@admin.admin";
        String adminPass = "Admin12345";
        CreateUserRequest request = new CreateUserRequest(adminEmail, adminPass, UserRole.ROLE_ADMIN);
        try {
            addNewUser(request);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }
/*
    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists){
            throw new IllegalStateException("user with id " + userId + "does not exist");
        }
        userRepository.deleteById(userId);
    }
    @Transactional
    public void updateUser(Long userId, String login, String role) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("user with id " + userId + " does not exist")
        );
        if(login != null && !login.isEmpty() && !Objects.equals(user.getLogin(), login)) {
            UserEntity findByLoginUser = userRepository.findUserByLogin(login);
            //System.out.println(findByLoginUser);
            if(findByLoginUser != null){
                throw new IllegalStateException("this login is using, you can't use it");
            }
            user.setLogin(login);
        }
        if(role != null && !role.isEmpty() && !Objects.equals(user.getROLE(), role)) {
            user.setROLE(UserRole.valueOf(role));
        }
    }*/
}


