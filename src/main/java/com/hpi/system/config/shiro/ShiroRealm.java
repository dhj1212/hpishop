package com.hpi.system.config.shiro;

import com.hpi.common.util.CommUtils;
import com.hpi.common.util.JwtUtil;
import com.hpi.modules.users.entity.SysUser;
import com.hpi.util.Constants;
import com.hpi.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.*;


@Slf4j
public class ShiroRealm extends AuthorizingRealm
{
	//@Resource
	//UserService userService;
	
	//@Autowired
	//private SysUserService sysUserService;
	
	//@Autowired
	//private ISysBaseAPI sysBaseAPI;
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		//String username = (String) jwtUtil.decode(jwt).get("username");
	    Map<String, SysUser> principal = (Map) principals.getPrimaryPrincipal();
		SysUser users =  principal.get("user");
	    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	    if (users!=null){
	    	
	    	System.out.println("--------------------------------user hello------------------"+users.getUserid());
	    	//authorizationInfo.addRole("用户管理");
	    	//authorizationInfo.addRoles(roles);
	    	//authorizationInfo.addStringPermission("/system/syslog/show1");
	    	//authorizationInfo.addStringPermission("/system/syslog/findLog2");
	    	List<String> permissions=users.getPermissionList();
	    	if (permissions!=null){
	    		Set<String> permissionset = new HashSet<>(permissions);
	    		authorizationInfo.setStringPermissions(permissionset);
	    	}
	    	
	    	if (permissions!=null && permissions.size()>0){
	    		for (String permission:permissions ){
		    		if (CommUtils.isNotEmpty(permission)){
		    			//authorizationInfo.addStringPermission(permission.trim());
		    			System.out.println("---permission="+permission.trim());
		    		}
		    		
		    	}
	    	}
	    	
	    	/**Set<Sysroleuser> sysroleusers=user.getSysroleusers();
	    	if (sysroleusers!=null && sysroleusers.size()>0){
	    		for (Sysroleuser roleuser :sysroleusers ){
	    			if (roleuser!=null ){
	    				Sysrole role=roleuser.getSysrole();
	    				if (role!=null){
	    					authorizationInfo.addRole(role.getRolename());
	    					System.out.println("role.getRolename()===="+role.getRolename());
	    					Set<Sysrolepermission> rolepermissions=role.getSysrolepermissions();
	    					if (rolepermissions!=null){
	    						for( Sysrolepermission rolepermission : rolepermissions ){
	    							if (rolepermission!=null && rolepermission.getSyspermissions()!=null ){
	    								Syspermissions permission=rolepermission.getSyspermissions();
	    								if (permission!=null && permission.getPermission()!=null && permission.getPermission().trim().length()>0){
	    									System.out.println("permission.getPermission().trim()===="+permission.getPermission().trim());
	    									authorizationInfo.addStringPermission(permission.getPermission().trim());
	    								}
	    								
	    							}
	    						}
	    					}
	    				}
	    				
	    				
	    			}
	    		}
	    		
	    	}**/
	    	/*System.out.println("userid=="+user.getUserid()+user.getUsername());
	    	Set<String> roleNames = new HashSet<String>();
		    Set<String> permissions = new HashSet<String>();
		    roleNames.add("admin1");
		    roleNames.add("zhangsan");
		    permissions.add("user1.do?method=showUserlist");
		    permissions.add("user:add");
		    permissions.add("/user.do?method=showOrglist");
		    permissions.add("login.do?logout");*/
	    }else{
	    	System.out.println("user is null......................");
	    }
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException
	{
		
		//System.out.println("==========  AuthenticationInfo  denglu  ========");
		//final String loginId1 = (String) authenticationToken.getPrincipal();
		//System.out.println("登陆loginId1================"+loginId1);
		//System.out.println("=================="+authenticationToken.getCredentials());
		//String password = null;
		SysUser user=null;
		//final Object credentials = authenticationToken.getCredentials();
		//if (credentials instanceof char[]) {
		//   password = new String((char[]) credentials);
		//}
		String token = (String) authenticationToken.getCredentials();
		System.out.println("登陆token================"+token);
		String loginId = JwtUtil.getString(token, "username");
		//String enpassword = JwtUtil.getString(token, "enpassword");
		//System.out.println("登陆password================"+password);
		
		System.out.println("登陆loginId================"+loginId);
		//System.out.println("登陆enpassword================"+enpassword);
		try {
			//Sysuser user = this.userService.getUserLogin(loginId);
			//SysUser user = new SysUser();
			
			user=UserUtil.getUser();
			if (user==null || user.getLoginid()==null){
				//user=sysBaseAPI.getUserByName(loginId);
				user=new SysUser();
				user.setUserid("1");
				user.setUsername("系统管理员");
				user.setStatus("1");
				user.setLoginid("admin");
				if (user == null) {
					log.error("账号或密码错误");
			      throw new UnknownAccountException("账号或密码错误");
			    }
				//List<String> permissions=sysBaseAPI.findUserPermission(loginId);
				List<String> permissions=new ArrayList<>();
				permissions.add("/sys.dba");
				user.setPermissionList(permissions);
			}else{
				/*System.out.println("---permission=");
				for (String permission:user.getPermissionList() ){
		    		if (CommUtils.isNotEmpty(permission)){
		    			System.out.println("---permission="+permission.trim());
		    		}
		    		
		    	}*/
				if (CommUtils.isNotEmpty(loginId) && !loginId.equals(user.getLoginid())){
					throw new UnknownAccountException("用户异常，请重新登录");
				}
				List<String> permissions=user.getPermissionList();
		    	if (permissions!=null && permissions.size()>0){
		    		System.out.println("---permission_size---="+permissions.size());
		    	}else{
		    		System.out.println("---permission_size---==0");
		    	}
			}
			
			
			//user.setUserid("1");
			//user.setLoginid(loginId);
			//user.setPassword(MD5Utils.encrype("123"));
			
			if(Constants.CODE_USER_STATUS_YTY.equals(user.getStatus())){
	            throw new DisabledAccountException("账号已经禁止登录");
	        }else{
	           // user.setUpdated(DateUtils.getNowTimestamp());
	           // user.setUpdatedAt(DateUtils.getNowFormatDate(null));
	           // System.out.println("效验更新前ROLE："+user.getRole().getRId());
	           // userService.update(user,true,user.getId());
	        }
			// 从数据库查询到密码
		    //配合shiro配置的mc5加密(应该可以配置为不加密)
		   // if (password != null) {
		     // password = DigestUtils.md5Hex(password);
		      
		   // }
		    //加密的盐
		   // String salt = user.getSalt()==null || user.getSalt().trim().length()<=0 ? MD5Utils.SALT: user.getSalt().trim();
		    
		    final HashMap<String, SysUser> principal = new HashMap<String, SysUser>();
		    
		    principal.put("user", user);
		  //  principal.put("permissions", permissions);
		    //System.out.println("user.getPassword()==="+user.getPassword());
		    //String content="用户："+user.getUsername()+"【"+loginId+"】登录成功";
			//sysBaseAPI.addLog(content, Constants.CODE_LOG_TYPE_LOGIN, "");
		   // return new SimpleAuthenticationInfo(principal, user.getPassword(),ByteSource.Util.bytes(salt), this.getName());
		    return new SimpleAuthenticationInfo(principal,token,this.getName());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return new SimpleAuthenticationInfo();
		}
		
		
	}

}
