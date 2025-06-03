package filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter({"/userLogged/*"})
public class AuthUtenteFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String ruolo = (session != null) ? (String) session.getAttribute("Nome") : null;

        if (ruolo != null) {
            chain.doFilter(request, response); //Accesso consentito.
        } else {
            if (session != null)
                session.setAttribute("redirect", true);
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
