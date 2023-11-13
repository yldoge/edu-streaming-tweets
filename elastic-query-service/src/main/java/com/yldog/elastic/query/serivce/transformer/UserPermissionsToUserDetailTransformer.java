package com.yldog.elastic.query.serivce.transformer;

import com.yldog.elastic.query.serivce.dataaccess.entity.UserPermission;
import com.yldog.elastic.query.serivce.security.PermissionType;
import com.yldog.elastic.query.serivce.security.TwitterQueryUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPermissionsToUserDetailTransformer {

    public TwitterQueryUser getUserDetails(List<UserPermission> userPermissions) {
        return TwitterQueryUser.builder()
                .username(userPermissions.get(0).getUsername())
                .permissions(userPermissions.stream()
                        .collect(Collectors.toMap(
                                UserPermission::getDocumentId,
                                permission -> PermissionType.valueOf(permission.getPermissionType()))))
                .build();
    }
}
