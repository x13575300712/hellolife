package com.hellolife.shiro;

import javax.annotation.Resource;

import com.hellolife.sys.service.PermissionService;
import com.hellolife.sys.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.hellolife.sys.dao.Permission;
import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
	@Resource
    private UserService userService;
	@Autowired
    private RoleService roleService;
	@Autowired
    private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User userInfo  = (User)principals.getPrimaryPrincipal();
		long userId = userInfo.getId();
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		Set<String> roles=new HashSet<String>();
		List<Role> rolesList = roleService.selectRole(userId);
		for(Role role:rolesList) {
			roles.add(role.getRole());
		}
		List<Permission> permissions = permissionService.selectPermission(userId);
		for(Permission permission:permissions) {
			info.addStringPermission(permission.getPermission());
		}
		info.setRoles(roles);
		return info;
	}
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	    //获取用户的输入的账号.
	    String usercode = (String)token.getPrincipal();
	    //通过username从数据库中查找 User对象，如果找到，没找到.
	    //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
	    User userInfo = userService.loginUser(usercode);
	    if(userInfo == null){
	        return null;
	    }
	    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
	            userInfo, //用户名
	            userInfo.getPassWord(), //密码
	            ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
	            getName()  //realm name
	    );
	    return authenticationInfo;
	}

}
