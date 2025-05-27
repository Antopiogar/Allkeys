	<%! boolean isAdmin = false; %>
    <%
        String nome = (String) request.getSession().getAttribute("Nome");
    	if(request.getSession().getAttribute("isAdmin")!=null) 
    		isAdmin = (boolean)request.getSession().getAttribute("isAdmin");
    %>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar2.css">
        <script type="text/javascript" src ="<%= request.getContextPath() %>/js/Search2.js" defer></script>
    
    <div class="navbar">
        <div class="logo">
            <a href="<%= request.getContextPath()%>/index.jsp"><img src="<%= request.getContextPath() %>/IMG/Logo.png"  alt="AllKeys"></a>
        </div>
        <div class="searchBar">
        	<input type="text" id="fastSearchBar" placeholder="cerca" >
        </div>
        <div class="item">
            <a href="<%= request.getContextPath()%>/carrello.jsp">Carrello</a>
        </div>
        
		<%if(nome!= null && isAdmin == true) {%>
			<div class="dropdown"> 
                <button class="dropbtn"><%= nome %></button>
                <div class="dropdown-content">
                    <a href="<%= request.getContextPath() %>/adminLogged/profiloAdmin.jsp">Profilo</a>
                    <a href="<%= request.getContextPath() %>/VisualizzaOrdiniServlet">I miei ordini</a>
                    <a href="<%= request.getContextPath() %>/userLogged/addNewPaymentMethod.jsp">Aggiungi nuova carta</a>
                    <a href="<%= request.getContextPath() %>/logout">Logout</a>
                </div>
            </div>
        <%}else if(nome != null) { %>
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

