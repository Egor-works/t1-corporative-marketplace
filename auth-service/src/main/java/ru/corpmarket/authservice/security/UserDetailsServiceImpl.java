package ru.corpmarket.authservice.security;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.corpmarket.authservice.dto.EncodedPasswordDto;
import ru.corpmarket.authservice.model.UserCredentials;
import ru.corpmarket.authservice.service.ConsumerFeignClient;
import ru.corpmarket.authservice.service.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService = UserService.getInstance();
    private final ConsumerFeignClient customerFeignClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials userCredentials = userService.getUserCredentials();
        if (userCredentials.getEmail().equals(username)) {
            switch (userCredentials.getUserRole()) {
                case CONSUMER -> {
                    try {
                        EncodedPasswordDto encodedPasswordDto = customerFeignClient.getEncodedPasswordByEmail(username).getBody();
                        log.debug("CUSTOMER");
                        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                                .commaSeparatedStringToAuthorityList("ROLE_" + userCredentials.getUserRole());
                        return new User(userCredentials.getEmail(), encodedPasswordDto.getEncodedPassword(), grantedAuthorities);
                    }
                    catch (FeignException.NotFound e) {
                        throw new UsernameNotFoundException("User with email " + username + " is not found");
                    }
                    catch (FeignException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> throw new UsernameNotFoundException("Role " + userCredentials.getUserRole() + " does not exist");
            }
        }
        throw new UsernameNotFoundException("User with email " + username + " is not found");
    }
}
