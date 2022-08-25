package com.weilai.server.interceptor;

import com.weilai.server.annotation.AuthorityAnnotation;
import com.weilai.server.exception.CustomException;
import com.weilai.server.pojo.Authority;
import com.weilai.server.service.AuthorityService;
import com.weilai.server.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorityInterceptor implements HandlerInterceptor {

    private AuthorityService authorityService;
    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    /**
     *
     * @param request request
     * @param response response
     * @param handler handler
     * @return 是否通过
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//      不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }
        Method method = handlerMethod.getMethod();
        AuthorityAnnotation annotation = method.getAnnotation(AuthorityAnnotation.class);
//      为空则无需限制，直接放行
        if (annotation == null) {
            return true;
        }
        if (annotation.sign() > 64) {
            throw new CustomException("401","无权访问！");
        }
//      由于JwtInterceptor已经对Token进行过校验了，所以这里不进行重复校验,直接获取id！
        Long id = TokenUtils.getId();
//      按道理讲能执行到这id就不可能为空，但是为了避免出现了未知问题让某些缓解出现了异常，我们还是检验一下为好
        if (id == null) {
            throw new CustomException("Error","获取id异常！");
        }
//      获取用户的权限信息
        Authority authority = authorityService.getById(id);

        //判断是否有权限访问
        if (((1L << (annotation.sign() - 1)) & authority.getAuthority()) == 0) {
            throw new CustomException("401","无权访问！");
        }
        return true;
    }
}
