package filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/adminLogged/*")
public class AuthAdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Boolean isAdmin = (session != null) ? (Boolean) session.getAttribute("isAdmin") : null;
        String nome = (session != null) ? (String) session.getAttribute("Nome") : null;

        if (Boolean.TRUE.equals(isAdmin) && nome != null) {
            chain.doFilter(request, response); // Admin autenticato
        } else {
            if (session != null) session.setAttribute("redirect", true);
            res.sendRedirect(req.getContextPath() + "/errorPages/error403.jsp");
        }
    }
}
