package cn.exrick.xboot.support.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author chenchao
 */
public class SpringSecurityUtils {
	
	public static String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String name = authentication.getName();
			return name;
		}else {
			return "Anonymous";
		}
	}
	
	public static Object getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			Object principal = authentication.getPrincipal();
			return principal;
		}else {
			return null;
		}
	}

}
