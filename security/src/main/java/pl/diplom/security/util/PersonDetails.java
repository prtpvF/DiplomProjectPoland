package pl.diplom.security.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.diplom.common.model.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class PersonDetails implements UserDetails {

        private final Person person;

        public PersonDetails(Person person) {
            this.person = person;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority(person.getRole().toString()));
        }

        @Override
        public String getPassword() {
            return this.person.getPassword();
        }

        @Override
        public String getUsername() {
            return this.person.getUsername();
        }
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        @Override
        public boolean isEnabled() {
            return true;
        }
}
