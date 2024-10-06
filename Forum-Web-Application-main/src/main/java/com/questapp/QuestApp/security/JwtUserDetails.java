package com.questapp.QuestApp.security;

import com.questapp.QuestApp.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class JwtUserDetails  implements UserDetails {

    public Long id;
    public String username;
    public String password;
    private Collection<? extends GrantedAuthority> authorities;

    private JwtUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new JwtUserDetails(user.getId(),user.getUsername(), user.getPassword(), authorities);
    }


}
