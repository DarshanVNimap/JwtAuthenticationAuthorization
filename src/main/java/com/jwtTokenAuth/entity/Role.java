package com.jwtTokenAuth.entity;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	
	ROLE_USER(
			Set.of(
					Permissions.ADMIN_CREATE,
					Permissions.ADMIN_DELETE,
					Permissions.ADMIN_READ,
					Permissions.ADMIN_UPDATE,
					
					Permissions.USER_CREATE,
//					Permissions.USER_DELETE,
					Permissions.USER_READ,
					Permissions.USER_UPDATE
					)
			) , 
	
	ROLE_ADMIN(Set.of(
			Permissions.USER_CREATE,
			Permissions.USER_DELETE,
			Permissions.USER_READ,
			Permissions.USER_UPDATE
			)) ;
	
	@Getter
	private final Set<Permissions> permissions;
	
	
	public List<SimpleGrantedAuthority> getAuthority(){
		var authorities =  getPermissions()
				.stream()
				.map( permission -> 
						new SimpleGrantedAuthority(permission.getPermissions())
						).toList();
		
		authorities.add(new SimpleGrantedAuthority(this.name()));	
		authorities.stream().forEach(auth -> System.out.println(auth.getAuthority()));
		return authorities;
	}

}
 