package com.jhuniversity.crm.web.filter;
import com.jhuniversity.crm.settings.domian.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //springmvc自带的拦截器，怎么配置都不行。要不就是不经过拦截器。要不就是经过拦截器，但是啥也不拦截
        //要不就是，登陆页面经过拦截器。我自己输入网址时，直接访问又不经过拦截器了。
        //在xml文件中把 *.do  变成 /   怎么设置也都访问不到静态资源了，拦截器还是没用
        //最后只能自己定义一个好了
        System.out.println("进入拦截器");
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String path=request.getServletPath();
        //不应该被拦截的资源，直接放行
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            filterChain.doFilter(servletRequest,servletResponse);

            //其他资源进行验证
        }else {

            User user=(User)request.getSession().getAttribute("user");
            if (user!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                /**
                 *  实际项目中，对于绝对路径，不论操作是前端还是后端，应该一律使用绝对路径
                 *    关于转发和重定向的路径的写法如下
                 *       转发：使用的是一种特殊的绝对路径的使用方式，这种绝对路径前面不加/项目名，
                 *             这种路径也称之为内部路径/login.jsp
                 *       重定向：
                 *             使用的是传统绝对路径的写法，前面必须以/项目名开头，后面跟具体的资源路径
                 *             /crm/login.jsp
                 *    为什么使用重定向，不使用转发？
                 *        转发之后，路径会停留在老路径之上，而不是跳转之后最新资源路径
                 *        我们应该在为用户跳转到登录页的同时 ，将浏览器地址栏上自动设置为当前的登录页路径
                 */
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }

    }

    @Override
    public void destroy() {

    }
}
