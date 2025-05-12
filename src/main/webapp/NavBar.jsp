
    <%
        String nome = (String) request.getSession().getAttribute("Nome");
    %>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Navbar.css">
    
    <div class="navbar">
        <div class="logo">
            <a href="<%= request.getContextPath()%>/index.jsp"><img src="<%= request.getContextPath() %>/IMG/Logo.png"  alt="AllKeys"></a>
        </div>
        <div class="item">
            <a href="<%= request.getContextPath()%>/carrello.jsp">Carrello</a>
        </div>

        <% if(nome != null) { %>
            <div class="dropdown"> 
                <button class="dropbtn"><%= nome %></button>
                <div class="dropdown-content">
                    <a href="<%= request.getContextPath() %>/userLogged/profilo.jsp">Profilo</a>
                    <a href="<%= request.getContextPath() %>/VisualizzaOrdiniServlet">I miei ordini</a>
                    <a href="<%= request.getContextPath() %>/userLogged/addNewPaymentMethod.jsp">Aggiungi nuova carta</a>
                    <a href="<%= request.getContextPath() %>/logout">Logout</a>
                </div>
            </div>
        <% } else { %>
            <div class="item">
                <a href="<%= request.getContextPath() %>/register.jsp">Registrati</a>
            </div>
            <div class="item">    
                <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
            </div>    
        <% } %>
    </div>

