package com.wsm.sso.server.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author ajay peng 
 */
//@Component
public class SsoShiroRealm extends AuthorizingRealm {

	/*@Autowired
	private IUserService userService;*/
	
    public SsoShiroRealm() {
        super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		/*User user = (User) principals.getPrimaryPrincipal();
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
		}*/
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /*String username = (String) token.getPrincipal();

		
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
		return info;*/
        return null;
    }

}
