package com.wsm.admin.shiro;

import com.wsm.admin.model.Resource;
import com.wsm.admin.model.Role;
import com.wsm.admin.model.User;
import com.wsm.admin.service.IUserService;
import com.wsm.common.util.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ajay peng 
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;
	
    public MyShiroRealm() {
        super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		try {
			User dbUser = userService.findByUserName(user.getUserName());
			Set<String> shiroPermissions = new HashSet<>();
			Set<String> roleSet = new HashSet<String>();
			Set<Role> roles = dbUser.getRoles();
			for (Role role : roles) {
				Set<Resource> resources = role.getResources();
				for (Resource resource : resources) {
					shiroPermissions.add(resource.getResourceKey());
				}
				roleSet.add(role.getRoleKey());
			}
			authorizationInfo.setRoles(roleSet);
			authorizationInfo.setStringPermissions(shiroPermissions);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

		
		User user = userService.findByUserName(username);
		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		Object credentials = token.getCredentials();
		if (credentials == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		String password = new String((char[]) credentials);
		String passwordCode = PasswordUtil.encrypt(password, user.getUserName(), PasswordUtil.getStaticSalt());
		// 密码错误
		if (!passwordCode.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		// 账号锁定
		if (user.getStatus() == 1) {
			throw new LockedAccountException("账号已被禁用,请联系管理员");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
    }

}
