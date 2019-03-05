import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;



public class Home extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  
    PrintWriter out = response.getWriter();
	response.setContentType("text/html;charset=UTF-8");
	out.println("<html><head>");
	out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
	out.println("<title>Smart Portables</title>");
	out.println("<link rel='shortcut icon' href='cart/icon.jpg'/>");
	out.println("<script type='text/javascript' src='javascript.js'></script>");
	out.println("<link rel='stylesheet' href='styles.css' type='text/css' />");
	out.println("</head>");
	out.println("<body onload='init()'>");
	out.println("<div id='container'>");
	out.println("<header>");
	out.println("<h1><a href='home'>Smart <span> Portables</span></a></h1>");
	out.println("</header>");
	out.println("<nav>");
	out.println("<ul>");
	out.println("<div align='right'>");
	HttpSession session = request.getSession();
	String fname=(String)session.getAttribute("fname");
	
	if (fname == null)
	{
	out.println("<li class=''><a href='register'>Register</a></li>");
	out.println("<li class='' ><a href='login'>Sign in</a></li>");
	}
	else if(fname.equals("SalesMngr"))
	{
		  out.println("<li class=''><a href='register'>Register Customer</a></li>");
		  out.println("<li class=''><a href='#'>Hello  "+fname+"</a></li>");  
		  out.println("<li class='' ><a href='signout'>Sign Out</a></li>");
	}
	else
	{
		out.println("<li class=''><a href='#'>Hello  "+fname+"</a></li>");
		out.println("<li class='' ><a href='signout'>Sign Out</a></li>");
	}
	
	out.println("<li class='' ><a href='vieworders'>View Orders</a></li>");
	out.println("<form action='viewcart'>");
	out.println("<button type='submit' style='background-color:transparent'><img src='images/cart.png' width = '60px' height = '63px'></button>");
	out.println("</form>");
	out.println("</div>");
	out.println("</ul>");
	out.println("</nav>");
	
	out.println("<nav>");
	out.println("<ul>");
	out.println("<li  class=''><a href='home'>Home</a></li>");
	out.println("<li class=''><a href='smartwatches'>Smart Watches</a></li>");
	out.println("<li class=''><a href='speakers'>Speakers</a></li>");
	out.println("<li class=''><a href='headphones'>Headphones</a></li>");
	out.println("<li class=''><a href='phones'>Phones</a></li>");
	out.println("<li class=''><a href='laptops'>Laptops</a></li>");
	out.println("<li class=''><a href='externalstorage'>External Storage</a></li>");
	out.println("</ul>");
	out.println("</nav>");
	
	//search feature
	out.println("  <form name='autofillform' action='autocomplete'>");
      out.println("<table border='0' cellpadding='5'> ");
      out.println("  <tbody> ");
          out.println("<tr>");
            out.println("<td><strong>Search :  </strong>");
                      
                       out.println(" <input type='text' size='40' id='complete-field' onkeyup='doCompletion()' autocomplete='off'></td>");
                     
        out.println("</tr>");
          out.println("<tr>");
             out.println("<td id='auto-row' colspan='2'>");
                out.println("<table id='complete-table' class='popupBox' />");
              out.println("</td>");
          out.println("</tr>");
        out.println("</tbody>");
     out.println(" </table>");
  out.println("  </form>");
	//End of search feature
	
	out.println("<img class='header-image' src='images/img_index1.jpg' width = '100%' height = '100%' alt='Index Page Image' />");
	out.println("<div id='body'>");
	out.println("<section id='content'>");
	out.println("<article>");
	out.println("<h2>Welcome to Smart Portables</h2>");
	out.println("<p>Smart Portables offers variety of Electronic Devices</p>");
	out.println("<p>Shop at the best market rate</p>");
	
	//start of twitter api code
	out.println("<h2>We beat our competitors in all aspects</h2>");
	out.println("<h2>Best price guranteed</h2>");
	//Print twitter lines here of matching products
	MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
	List<Products> productList = dbObj.getProductList("");
	List<String> twitterLines = DealMatches.getLineList();
	
	List<Products> fetchProduct = new ArrayList<Products>();
	
	if(twitterLines == null)
	{
		out.println("<p>No offers found today.</p>");
	}
	else
	{
		int i=0;
		for(Products p:productList)
		{
			for(String line:twitterLines)
			{
				if(i==2)
					break;
				if(i==1)
				{
					if(line.contains(p.getName()) && !(line.contains(fetchProduct.get(0).getName())))
					{
						out.println("<p>" +line+ "</p>");
						fetchProduct.add(p);
						++i;
					}
				}
				else if(line.contains(p.getName()))
				{
						out.println("<p>" +line+ "</p>");
						fetchProduct.add(p);
						++i;
				}
			}
		}
		
		out.println("<h2>Deal Matches</h2>" +
				"<table>");	
	Products p1;
	 for(i = 0;i<fetchProduct.size();i++)
	{
		p1 = fetchProduct.get(i);
		session.setAttribute("addtocart",p1);
		//out.println("p name: "+p.getName());
		out.println("<tr>"+
					"	<td><b>" + p1.getName() + "</b>  </td>" +
					"	<td><img class='product-image' src='" +p1.getImage()+ "'></td>" +
					"	<td>$" + p1.getPrice() + "</td>" +
					"<form class = 'submit-button' method = 'get' action = 'addtocart'>" +
					"<td><input class = 'submit-button' type = 'submit' value='Buy Now'></td>" +
					"<input type='hidden' name='product' value='"+p1+"'>" +
					"</form>" +
					//Write Review form
					"<form class = 'submit-button' method = 'get' action = 'writereview'>" +
					"<td><input class = 'submit-button' type = 'submit' value='Write Review'></td>" +
					"<input type='hidden' name='product' value='"+p1+"'>" +
					"<input type='hidden' name='productCategory' value='" +p1.getCategory()+ "'>" +
					"</form>" +
					
					//View Review form
					"<form class = 'submit-button' method = 'get' action = 'viewreview'>" +
					"<td><input class = 'submit-button' type = 'submit' value='View Review'></td>" +
					"<input type='hidden' name='product' value='"+p1+"'>" +
					"</form>" +
					"</tr>");
	} 
	
	out.println("</table>");
	}
	//end of twitter api code
	
	out.println("</article>");
	out.println("</section>");
	
	out.println("<aside class='sidebar'>");
	out.println("<ul>");	
	out.println("<li>");
	out.println("<h4>Products</h4>");
	out.println("<ul>");
	out.println("<li><a href='smartwatches'>Smart Watches</a></li>");
	out.println("<li><a href='speakers'>Speakers</a></li>");
	out.println("<li><a href='headphones'>Headphones</a></li>");
	out.println("<li><a href='phones'>Phones</a></li>");
	out.println("<li><a href='laptops'>Laptops</a></li>");
	out.println("<li><a href='externalstorage'>External Storage</a></li>");
	out.println("</ul>");
	out.println("</li>");

	//Start of Trending tab
	out.println("<li>");
	out.println("<a href='trendingproducts'><h4>Trending Products</h4></a>");
	out.println("</li>");
	//End of trending tab.
	
	out.println("<li>");
	out.println("<h4>About us</h4>");
	out.println("<ul>");
	out.println("<li class='text'>");
	out.println("<p style='margin: 0;'>This is a  website created to demonstrate a working of online commercial websites.</p>");
	out.println(" </li>");
	out.println("</ul>");
	out.println("</li>");	
	out.println("<li>");
	out.println("<h4>Search Products</h4>");
	out.println("<ul>");
	out.println("<li class='text'>");
	out.println("<form method='get' class='searchform' action='#'>");
	out.println("<p>");
	out.println("<input type='text' size='25' value='' name='s' class='s' />");
	out.println("</p>");	
	out.println("</form></li></ul></li>");	     	
	out.println("<li>");	
	out.println("<h4>Helpful Links</h4>");	
	out.println("<ul>");	
	out.println("<li><a href='mailto:rambani@hawk.iit.edu' title='premium templates'>Customer Care Support Email</a></li>");	
	out.println("<li>Phone: (123)-456-7890</li>");	
	out.println("</ul></li></ul></aside>");	
	out.println("<div class='clear'></div>");
	out.println("</div>");	
	out.println("<footer>");	
	out.println("<div class='footer-content'>");	
	out.println("<div class='clear'></div>");	
	out.println("</div>");	
	out.println("<div class='footer-bottom'>");	
	out.println("<p>Smart Portables - Enterprise Web Application </p>");	
	out.println("</div>");	
	out.println("</footer>");	
	out.println("</div>");	
	out.println("</body>");	
	out.println("</html>");	

}




 public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	
	PrintWriter out = response.getWriter();
	response.setContentType("text/html;charset=UTF-8");
	out.println("<html><head>");
	out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
	out.println("<title>Smart Portables</title>");
	out.println("<link rel='shortcut icon' href='cart/icon.jpg'/>");
	out.println("<script type='text/javascript' src='javascript.js'></script>");
	out.println("<link rel='stylesheet' href='styles.css' type='text/css' />");
	out.println("</head>");
	out.println("<body onload='init()'>");
	out.println("<div id='container'>");
	out.println("<header>");
	out.println("<h1><a href='/'>Smart <span> Portables</span></a></h1>");
	out.println("</header>");
	out.println("<nav>");
	out.println("<ul>");
	out.println("<div align='right'>");
	HttpSession session = request.getSession();
	String fname=(String)session.getAttribute("fname");
	
	if (fname == null)
	{
	out.println("<li class=''><a href='register'>Register</a></li>");
	out.println("<li class='' ><a href='login'>Sign in</a></li>");
	}
	else if(fname.equals("SalesMngr"))
	{
		  out.println("<li class=''><a href='register'>Register Customer</a></li>");
		  out.println("<li class=''><a href='#'>Hello  "+fname+"</a></li>"); 
		  out.println("<li class='' ><a href='signout'>Sign Out</a></li>");
	}
	else
	{
		out.println("<li class=''><a href='#'>Hello  "+fname+"</a></li>");
		out.println("<li class='' ><a href='signout'>Sign Out</a></li>");
	}
	
	out.println("<li class='' ><a href='vieworders'>View Orders</a></li>");
	out.println("<form action='viewcart'>");
	out.println("<button type='submit' style='background-color:transparent'><img src='images/cart.png' width = '60px' height = '63px'></button>");
	out.println("</form>");
	out.println("</div>");
	out.println("</ul>");
	out.println("</nav>");
	
	out.println("<nav>");
	out.println("<ul>");
	out.println("<li  class=''><a href='home'>Home</a></li>");
	out.println("<li class=''><a href='smartwatches'>Smart Watches</a></li>");
	out.println("<li class=''><a href='speakers'>Speakers</a></li>");
	out.println("<li class=''><a href='headphones'>Headphones</a></li>");
	out.println("<li class=''><a href='phones'>Phones</a></li>");
	out.println("<li class=''><a href='laptops'>Laptops</a></li>");
	out.println("<li class=''><a href='externalstorage'>External Storage</a></li>");
	out.println("</ul>");
	out.println("</nav>");
	
	//search feature
	out.println("  <form name='autofillform' action='autocomplete'>");
      out.println("<table border='0' cellpadding='5'> ");
      out.println("  <tbody> ");
          out.println("<tr>");
            out.println("<td><strong>Search :  </strong>");
                      
                       out.println(" <input type='text' size='40' id='complete-field' onkeyup='doCompletion()' autocomplete='off'></td>");
                     
        out.println("</tr>");
          out.println("<tr>");
             out.println("<td id='auto-row' colspan='2'>");
                out.println("<table id='complete-table' class='popupBox' />");
              out.println("</td>");
          out.println("</tr>");
        out.println("</tbody>");
     out.println(" </table>");
  out.println("  </form>");
	//End of search feature
	
	out.println("<img class='header-image' src='images/img_index1.jpg' width = '100%' height = '100%' alt='Index Page Image' />");
	out.println("<div id='body'>");
	out.println("<section id='content'>");
	out.println("<article>");
	out.println("<h2>Welcome to Smart Portables</h2>");
	out.println("<p>Smart Portables offers variety of Electronic Devices</p>");
	out.println("<p>Shop at the best market rate</p>");
	
	//start of twitter api code
	out.println("<h2>We beat our competitors in all aspects</h2>");
	out.println("<h2>Best price guranteed</h2>");
	//Print twitter lines here of matching products
	MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
	List<Products> productList = dbObj.getProductList("");
	List<String> twitterLines = DealMatches.getLineList();
	
	List<Products> fetchProduct = new ArrayList<Products>();
	
	if(twitterLines == null)
	{
		out.println("<p>No offers found today.</p>");
	}
	else
	{
		int i=0;
		for(Products p:productList)
		{
			for(String line:twitterLines)
			{
				if(i==2)
					break;
				if(i==1)
				{
					if(line.contains(p.getName()) && !(line.contains(fetchProduct.get(0).getName())))
					{
						out.println("<p>" +line+ "</p>");
						fetchProduct.add(p);
						++i;
					}
				}
				else if(line.contains(p.getName()))
				{
						out.println("<p>" +line+ "</p>");
						fetchProduct.add(p);
						++i;
				}
			}
		}
		
		out.println("<h2>Deal Matches</h2>" +
				"<table>");	
	Products p1;
	 for(i = 0;i<fetchProduct.size();i++)
	{
		p1 = fetchProduct.get(i);
		session.setAttribute("addtocart",p1);
		//out.println("p name: "+p.getName());
		out.println("<tr>"+
					"	<td><b>" + p1.getName() + "</b>  </td>" +
					"	<td><img class='product-image' src='" +p1.getImage()+ "'></td>" +
					"	<td>$" + p1.getPrice() + "</td>" +
					"<form class = 'submit-button' method = 'get' action = 'addtocart'>" +
					"<td><input class = 'submit-button' type = 'submit' value='Buy Now'></td>" +
					"<input type='hidden' name='product' value='"+p1+"'>" +
					"</form>" +
					//Write Review form
					"<form class = 'submit-button' method = 'get' action = 'writereview'>" +
					"<td><input class = 'submit-button' type = 'submit' value='Write Review'></td>" +
					"<input type='hidden' name='product' value='"+p1+"'>" +
					"<input type='hidden' name='productCategory' value='Speakers'>" +
					"</form>" +
					
					//View Review form
					"<form class = 'submit-button' method = 'get' action = 'viewreview'>" +
					"<td><input class = 'submit-button' type = 'submit' value='View Review'></td>" +
					"<input type='hidden' name='product' value='"+p1+"'>" +
					"</form>" +
					"</tr>");
	} 
	
	out.println("</table>");
	}
	//end of twitter api code
	
	out.println("</article>");
	out.println("</section>");
	out.println("<aside class='sidebar'>");
	out.println("<ul>");	
	out.println("<li>");
	out.println("<h4>Products</h4>");
	out.println("<ul>");
	out.println("<li><a href='smartwatches'>Smart Watches</a></li>");
	out.println("<li><a href='speakers'>Speakers</a></li>");
	out.println("<li><a href='headphones'>Headphones</a></li>");
	out.println("<li><a href='phones'>Phones</a></li>");
	out.println("<li><a href='laptops'>Laptops</a></li>");
	out.println("<li><a href='externalstorage'>External Storage</a></li>");
	out.println("</ul>");
	out.println("</li>");	
	
	//Start of Trending tab
	out.println("<li>");
	out.println("<a href='trendingproducts'><h4>Trending Products</h4></a>");
	out.println("</li>");
	//End of trending tab.
	
	out.println("<li>");
	out.println("<h4>About us</h4>");
	out.println("<ul>");
	out.println("<li class='text'>");
	out.println("<p style='margin: 0;'>This is a  website created to demonstrate a working of online commercial websites.</p>");
	out.println(" </li>");
	out.println("</ul>");
	out.println("</li>");	
	out.println("<li>");
	out.println("<h4>Search Products</h4>");
	out.println("<ul>");
	out.println("<li class='text'>");
	out.println("<form method='get' class='searchform' action='#'>");
	out.println("<p>");
	out.println("<input type='text' size='25' value='' name='s' class='s' />");
	out.println("</p>");	
	out.println("</form></li></ul></li>");	     	
	out.println("<li>");	
	out.println("<h4>Helpful Links</h4>");	
	out.println("<ul>");	
	out.println("<li><a href='mailto:rambani@hawk.iit.edu' title='premium templates'>Customer Care Support Email</a></li>");	
	out.println("<li>Phone: (123)-456-7890</li>");	
	out.println("</ul></li></ul></aside>");	
	out.println("<div class='clear'></div>");
	out.println("</div>");	
	out.println("<footer>");	
	out.println("<div class='footer-content'>");	
	out.println("<div class='clear'></div>");	
	out.println("</div>");	
	out.println("<div class='footer-bottom'>");	
	out.println("<p>Smart Portables - Enterprise Web Application </p>");	
	out.println("</div>");	
	out.println("</footer>");	
	out.println("</div>");	
	out.println("</body>");	
	out.println("</html>");	

} 
}
