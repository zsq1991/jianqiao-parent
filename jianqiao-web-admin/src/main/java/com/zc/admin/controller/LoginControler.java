package com.zc.admin.controller;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.admin.confige.MyShiroRealm;
import com.zc.common.core.encrypt.Md5;
import com.zc.common.core.shiro.PermissionEnum;
import com.zc.common.core.shiro.Result;
import com.zc.common.core.utils.CommonConstants;
import com.zc.common.core.utils.verifycode.VerifycodeUtils;
import com.zc.main.dto.UserDTO;
import com.zc.main.dubbo.service.permission.ILoginService;
import com.zc.main.dubbo.service.permission.IUserService;
import com.zc.main.entity.permission.User;
import com.zc.main.vo.SessionRoleVO;
import com.zc.main.vo.SessionUserVO;
import com.zc.main.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 登录
 * @author system
 * @date 2018-01-25 17:33
 * @version 1.0.0
 */
@Controller
@RequestMapping("/")
public class LoginControler {
	
	
	@DubboConsumer(version="1.0.0")
	private ILoginService loginService;
	
	@DubboConsumer(version="1.0.0")
	private IUserService userService;
	
	@Autowired
    MyShiroRealm myShiroRealm;
	private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

	/**
	 * @描述：跳转登录页面
	 *
	 * @param: null
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:15
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String loginPage(){
		return "redirect:login";
	}
	
	
	/**********************登录页面********************/
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public Object login() {
		return "login";
	}
	
	/***********************首页************************/
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv=new ModelAndView("index");
		//返回index需要的用户名和角色信息
		SessionUserVO su = getLoginUser();
		if (!StringUtils.isEmpty(su)) {
			mv.addObject("userId", su.getId());
			mv.addObject("userName", su.getUserName());
			StringBuffer sb=new StringBuffer();
			if(su.getSessionRoleVo()!=null&&su.getSessionRoleVo().size()>0){
				for (SessionRoleVO sr : su.getSessionRoleVo()) {
					sb.append(sr.getRoleName()).append(",");
				}
				String str=sb.toString();
				String roleNames=str.substring(0, str.length()-1);
				mv.addObject("roleName", roleNames);
            } else {
                mv.setViewName("login");
                mv.addObject("code", PermissionEnum.NO_ROLE.getCode());
                mv.addObject("msg", PermissionEnum.NO_ROLE.getMsg());
            }
        }
		return mv;
	}
	
	//============================================执行登录==============================================/

		/**
		 * @描述：交给shiro管理登录
		 * @param: user
		 * @param: request
		 */
		@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
		@ResponseBody
	    public Object login(@ModelAttribute User user, boolean rememberMe, String msgCode, HttpServletRequest request) {

	        ModelAndView mv = new ModelAndView();

	        //1.图片验证码验证
	     /*   String verifyCode = msgCode;
	        String secode = VerifycodeUtils.getVerifyCodeBySession(request);
	        if (verifyCode == null || !verifyCode.equalsIgnoreCase(secode)) {
	            mv.setViewName("login");
	            mv.addObject("code", 1111);
	            mv.addObject("msg", "验证码错误");
	            return mv;
	        }*/

	        //2.登录认证
	        String username = user.getTelphone();
	        //MD5加密
	        user.setPassword(Md5.getMD5Str(user.getPassword()));
	        UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword(), rememberMe);
	        //获取当前的Subject
	        Subject currentUser = SecurityUtils.getSubject();
	        try {
	            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
	            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
	            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
	            logger.info("对用户[" + username + "]进行登录验证..验证开始");
	            currentUser.login(token);
	            logger.info("对用户[" + username + "]进行登录验证..验证通过");
	        } catch (UnknownAccountException uae) {
	            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
	            mv.setViewName("login");
	            if(uae.getMessage()!=null&&!"".equals(uae.getMessage().trim())){
	                mv.addObject("msg",uae.getMessage());
	            }else {
	                mv.addObject("msg", PermissionEnum.UNKNOWN_ACCOUNT.getMsg());
	            }
	            mv.addObject("code", PermissionEnum.UNKNOWN_ACCOUNT.getCode());
	            return mv;
	        } catch (IncorrectCredentialsException ice) {
	            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
	            mv.setViewName("login");
	            mv.addObject("code", PermissionEnum.INCORRECT_CREDENTIALS.getCode());
	            mv.addObject("msg", PermissionEnum.INCORRECT_CREDENTIALS.getMsg());
	            return mv;
	        } catch (LockedAccountException lae) {
	            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
	            mv.setViewName("login");
	            mv.addObject("code", PermissionEnum.LOCKED_ACCOUNT.getCode());
	            mv.addObject("msg", PermissionEnum.LOCKED_ACCOUNT.getMsg());
	            return mv;
	        } catch (ExcessiveAttemptsException eae) {
	            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
	            mv.setViewName("login");
	            mv.addObject("code", PermissionEnum.EXCESSIVE_ATTEMPTS.getCode());
	            mv.addObject("msg", PermissionEnum.EXCESSIVE_ATTEMPTS.getMsg());
	            return mv;
	        } catch (AuthenticationException ae) {
	            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
	            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
	            ae.printStackTrace();
	            mv.setViewName("login");
	            mv.addObject("code", PermissionEnum.AUTHENTICATION.getCode());
	            mv.addObject("msg", PermissionEnum.AUTHENTICATION.getMsg());
	            return mv;
	        }
	        //验证是否登录成功
	        if (currentUser.isAuthenticated()) {
	            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
	            //清除权限缓存
	            myShiroRealm.getAuthorizationCache().remove(currentUser.getPrincipals());
	            Session session = currentUser.getSession();
	            Result result = loginService.login(user);
	            session.setAttribute(CommonConstants.LOGIN_BACK_USER_SESSION, result.getData());
	            mv.setViewName("redirect:index");
	        } else {
	            token.clear();
	            mv.setViewName("redirect:login");
	        }
	        return mv;
	    }

		
		
		   /**
	     * @描述：加载验证码
	     * @param: null
	     * @return:
	     * @作者： Mr.Shu
	     * @创建时间：2017/6/1 10:20
	     */
	    @RequestMapping(value = "/loadverify", method = RequestMethod.GET)
	    public void loadVerifyCode(HttpServletRequest request, HttpServletResponse response) {
	        VerifycodeUtils.makeVerifyImageByNum(request, response, 4);
	    }


	    
	    public SessionUserVO getLoginUser() {

			//获取当前的Subject
			Subject currentUser = SecurityUtils.getSubject();
			SessionUserVO info = (SessionUserVO) currentUser.getSession().getAttribute(
					CommonConstants.LOGIN_BACK_USER_SESSION);

			//如果是记住我，处理Session失效
			if (info == null) {
	            Object telPhone = currentUser.getPrincipal();
	            if (telPhone != null) {
	                boolean isRemembered = currentUser.isRemembered();
	                if (isRemembered) {
	                    //清除权限缓存
	                    myShiroRealm.getAuthorizationCache().remove(currentUser.getPrincipals());
	                    UserDTO userDTO = new UserDTO();
	                    userDTO.setTelphone(telPhone.toString());
						UserVO userVO = userService.getUserByCondition(userDTO);
						//对密码进行加密后验证
						UsernamePasswordToken token = new UsernamePasswordToken(userVO.getTelphone(), userVO.getPassword(), currentUser.isRemembered());
						//把当前用户放入session
	                    currentUser.login(token);
	                    Session session = currentUser.getSession();
						User user = new User();
						user.setTelphone(userVO.getTelphone());
						user.setPassword(userVO.getPassword());
						Result result = loginService.login(user);
	                    session.setAttribute(CommonConstants.LOGIN_BACK_USER_SESSION, result.getData());
	                    info = (SessionUserVO) result.getData();
	                }
	            }

	        }
			return info;

		}
	    
		/**
		 * @描述：注销登录
		 */
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String logout() {
			try {
				SecurityUtils.getSubject().logout();
			} catch (Exception e) {
				logger.error("注销登录处理错误", e);
			}
	        return "redirect:login";
	    }

		/**
	     * @描述：重定向到没有权限返回页面
		 */
		@RequestMapping("/403")
		public String unauthorizedRole() {
			logger.info("------没有权限-------");
	        return "redirect:noPermission";
	    }

	    /**
	     * @描述：没有权限返回页面
	     */
	    @RequestMapping("/noPermission")
	    public String noPermission() {
	        return "noPermission";
	    }

		/**
		 * @描述：欢迎页
		 */
		@RequestMapping("/welcome")
	    public String toWelcome() {
	        return "welcome";
	    }

}
