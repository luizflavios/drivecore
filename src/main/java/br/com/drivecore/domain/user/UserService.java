package br.com.drivecore.domain.user;

import br.com.drivecore.domain.user.exception.UserNotFoundException;
import br.com.drivecore.domain.user.mapper.UserMapper;
import br.com.drivecore.domain.user.model.CreateUserRequestDTO;
import br.com.drivecore.infrastructure.authentication.provider.PasswordProvider;
import br.com.drivecore.infrastructure.persistence.user.UserRepository;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordProvider passwordProvider;
    private final UserRepository userRepository;

    public UserEntity create(CreateUserRequestDTO createUserRequestDTO) {
        var userEntity = UserMapper.INSTANCE.toEntity(createUserRequestDTO);

        userEntity.setPassword(passwordProvider.encodePassword(userEntity.getPassword()));

        return userRepository.save(userEntity);
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }


}
