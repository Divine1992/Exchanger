package gov.divine.Service;

import gov.divine.Model.Role;
import gov.divine.Model.User;
import gov.divine.Repository.RoleRepository;
import gov.divine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
    @Autowired
	@Qualifier("roleRepository")
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByRole("USER");
		if (userRole == null){
			userRole = new Role();
			userRole.setRole("USER");
			roleRepository.save(userRole);
		}
		user.setActive(true);
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(login);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}

}
