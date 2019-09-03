package top.ok.fine.pomegranate.system.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.ok.fine.pomegranate.system.domain.Role;
import top.ok.fine.pomegranate.system.domain.User;
import top.ok.fine.pomegranate.system.repository.UserRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/26
 */


@Service
public class WebUserDetailService implements UserDetailsService {


    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails  loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = user.getRoles();

        for (Role role : roles) {
            String name = role.getName();
            GrantedAuthority authority = new SimpleGrantedAuthority(name);
            authorities.add(authority);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
