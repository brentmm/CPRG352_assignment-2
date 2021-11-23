package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        //code during request
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String user = (String) session.getAttribute("sessionUser");
        
        if (user == null){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("login");
            return; //Important for security
        }
        

        //calls upon next filter or loads page
        chain.doFilter(request, response);
        
        //code during response
            //not used
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    //not used
    }

    @Override
    public void destroy() {
    //not used
    }
 
}
