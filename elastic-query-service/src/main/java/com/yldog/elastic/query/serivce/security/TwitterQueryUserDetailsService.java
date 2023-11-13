package com.yldog.elastic.query.serivce.security;

import com.yldog.elastic.query.serivce.business.QueryUserService;
import com.yldog.elastic.query.serivce.transformer.UserPermissionsToUserDetailTransformer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TwitterQueryUserDetailsService implements UserDetailsService {

    private final QueryUserService queryUserService;
    private final UserPermissionsToUserDetailTransformer userPermissionsToUserDetailTransformer;

    public TwitterQueryUserDetailsService(QueryUserService queryUserService,
                                          UserPermissionsToUserDetailTransformer userPermissionsToUserDetailTransformer) {
        this.queryUserService = queryUserService;
        this.userPermissionsToUserDetailTransformer = userPermissionsToUserDetailTransformer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return queryUserService
                .findAllPermissionsByUsername(username)
                .map(userPermissionsToUserDetailTransformer::getUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with name " + username));
    }
}
