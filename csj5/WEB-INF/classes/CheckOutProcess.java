import java.io.*;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.servlet.http.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class CheckOutProcess extends HttpServlet 
{
	static int orderCount=0;
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  
    PrintWriter out = response.getWriter();
	response.setContentType("text/html;charset=UTF-8");
	out.println("<html><head>");
	out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
	out.println("<title>Smart Portables</title>");
	out.println("<link rel='shortcut icon' href='cart/icon.jpg'/>");
	out.println("<link rel='stylesheet' href='styles.css' type='text/css' />");
	out.println("</head>");
	out.println("<body>");
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
	
	
	String s="";
	s=request.getParameter("addr") + "   ";
	s=s+request.getParameter("city") + "   ";
	s=s+request.getParameter("state") + "   ";
	s=s+request.getParameter("zip") + "   ";
	s=s+request.getParameter("country") + "   ";
		
	OrderDetails order = null;
	FileOutputStream fos;
	ObjectOutputStream oos;

	if(orderCount==0)
	{
		fos = new FileOutputStream(new File("C:\\apache-tomcat-7.0.34\\webapps\\csj1\\OrderDetails.txt"));
		oos = new ObjectOutputStream(fos);
		++orderCount;
	}
	else
	{		
		fos = new FileOutputStream(new File("C:\\apache-tomcat-7.0.34\\webapps\\csj1\\OrderDetails.txt"), true);
		oos = new AppendableObjectOutputStream(fos);
	}
	
	
	
	
	
	Random r = new Random();
	int Low = 1;
	int High = 622653;
	int orderNo = r.nextInt(High-Low) + Low;
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DAY_OF_MONTH, 14);
	Date date = cal.getTime();
	String DATE_FORMAT = "MM/dd/yyyy"; 
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);				
	String deliverydate = sdf.format(date);
	String fullname=request.getParameter("fullname");
        Cart ekart;
        ekart = (Cart) session.getAttribute("cart");
		Map<String, Float> items = ekart.getCartItems();
		String userid=(String)session.getAttribute("userid");
		
		//SQL use
		MySQLDataStoreUtilities dbObject = new MySQLDataStoreUtilities();

	for(Map.Entry<String, Float> entry : items.entrySet())
	{
				String key = entry.getKey();
				Float values = entry.getValue();	
				
				order = new OrderDetails();
				order.setOrderNo(orderNo+"");
				order.setUserId(fname);
				order.setModelName(key);
				order.setShippingAddress(s);
				order.setZipCode(request.getParameter("zip"));
				order.setPrice(values);
				order.setDeliveryDate(deliverydate);
				dbObject.insertOrderDetails(order);
				//oos.writeObject(order);
	}
				ekart.setProductList(new ArrayList<Products>());
				session.removeAttribute("cart");
				out.println("<h3><br><br>Your Order No "+order.getOrderNo()+" has been Placed Succesfully. The order will be delivered by " + deliverydate + " </h3><br><br>");	
		
	}
	
	private static class AppendableObjectOutputStream extends ObjectOutputStream 
	{

        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // do not write a header
        }
    }	
	
}
