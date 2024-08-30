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
import ru.corpmarket.authservice.model.UserRole;
import ru.corpmarket.authservice.service.AdminFeignClient;
import ru.corpmarket.authservice.service.ConsumerFeignClient;
import ru.corpmarket.authservice.service.ManagerFeignClient;
import ru.corpmarket.authservice.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService = UserService.getInstance();
    private final ConsumerFeignClient consumerFeignClient;
    private final ManagerFeignClient managerFeignClient;
    private final AdminFeignClient adminFeignClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials userCredentials = userService.getUserCredentials();
        if (userCredentials.getEmail().equals(username)) {
            if (Objects.requireNonNull(userCredentials.getUserRole()) == UserRole.CONSUMER){
                try {
                    EncodedPasswordDto encodedPasswordDto = consumerFeignClient.getEncodedPasswordByEmail(userCredentials.getEmail()).getBody();
                    log.debug("CONSUMER");
                    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                            .commaSeparatedStringToAuthorityList("ROLE_" + userCredentials.getUserRole());
                    return new User(userCredentials.getEmail(), userCredentials.getPassword(), grantedAuthorities);
                } catch (FeignException.NotFound e) {
                    throw new UsernameNotFoundException("User with email " + userCredentials.getEmail() + " is not found");
                } catch (FeignException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (Objects.requireNonNull(userCredentials.getUserRole()) == UserRole.MANAGER) {
                try {
                    EncodedPasswordDto encodedPasswordDto = managerFeignClient.getEncodedPasswordByEmail(userCredentials.getEmail()).getBody();
                    log.debug("MANAGER");
                    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                            .commaSeparatedStringToAuthorityList("ROLE_" + userCredentials.getUserRole());
                    return new User(userCredentials.getEmail(), Objects.requireNonNull(encodedPasswordDto).getEncodedPassword(), grantedAuthorities);
                } catch (FeignException.NotFound e) {
                    throw new UsernameNotFoundException("User with email " + userCredentials.getEmail() + " is not found");
                } catch (FeignException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (Objects.requireNonNull(userCredentials.getUserRole()) == UserRole.ADMIN){
                try {
                    EncodedPasswordDto encodedPasswordDto = adminFeignClient.getEncodedPasswordByEmail(userCredentials.getEmail()).getBody();
                    log.info("ADMIN");
                    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                            .commaSeparatedStringToAuthorityList("ROLE_" + userCredentials.getUserRole());
                    return new User(userCredentials.getEmail(), userCredentials.getPassword(), grantedAuthorities);
                } catch (FeignException.NotFound e) {
                    throw new UsernameNotFoundException("User with email " + userCredentials.getEmail() + " is not found");
                } catch (FeignException e) {
                    throw new RuntimeException(e);
                }
            }
            else throw new UsernameNotFoundException("Role " + userCredentials.getUserRole() + " does not exist");
        }
        throw new UsernameNotFoundException("User with email " + username + " is not found");
    }
}
