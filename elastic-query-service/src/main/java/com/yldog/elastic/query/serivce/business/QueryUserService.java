package com.yldog.elastic.query.serivce.business;

import com.yldog.elastic.query.serivce.dataaccess.entity.UserPermission;

import java.util.List;
import java.util.Optional;

public interface QueryUserService {
    Optional<List<UserPermission>> findAllPermissionsByUsername(String username);
}
