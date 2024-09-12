package src; //package in.automationtesting.oopd.oopd; // package in.automationtesting.oopd.oopd; //package src;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.mysql.cj.xdevapi.Statement;
class OnlineFoodOrderingSystem
{   
	public Connection sqlconnect()throws SQLException{
		Connection con = null;
	    String url = "jdbc:mysql://localhost:3306/oopd";
	    String UserName = "root";
	    String Password = "shrey@123"; //Tewatia@20  shrey@123 3011mitali
	    try {
	        con = DriverManager.getConnection(url,UserName,Password);
	        return con;

	    } catch (SQLException e) {
	        System.out.println("Connection Failed! Check output console");
	        e.printStackTrace();
	        System.exit(0);
	}
		return con;
	}
	
	void choice1(Connection con) throws SQLException, InterruptedException
	{
		Scanner sc = new Scanner(System.in);
        int cust_id=0;
        while(true)
        {
            int choice1;
            System.out.println("\n\n__Welcome to our ONLINE FOOD ORDERING SYSTEM__");
            System.out.println("\nPlease select an option:");
            System.out.println("1. New user");
            System.out.println("2. Existing user");
            System.out.println("3. Exit");
            choice1 = sc.nextInt();
            
            while(!(choice1 == 1 || choice1 == 2 || choice1 == 3))
            {
                System.out.println("\nInvalid input!");
                System.out.println("Please select an option:");
                System.out.println("1. New User");
                System.out.println("2. Existing User");
                System.out.println("3. Exit");
                choice1 = sc.nextInt();
            } 
            if(choice1 == 1)
            {
                SignUp sgn_up = new SignUp();
                sgn_up.sign_up_attempt(con);
            }
            if(choice1 == 2)            {
          	    System.out.println("Enter your email");
          	    String customer_email = sc.next();
             	System.out.println("Enter your password");
             	String customer_password = sc.next();
                LogIn lg_in = new LogIn();
                cust_id = lg_in.log_in_attempt(con,customer_email,customer_password);
                if(cust_id==-1) 
                	choice1(con);
                else
                	choice2(con,cust_id);
                		
            }
            if(choice1 == 3) 
            {
                System.out.println("Thank you for using our system!");
                System.out.println("Have a nice day!");
                System.exit(0);
                break;
            }
        }
	}
	
	void choice2(Connection con, int cust_id) throws SQLException, InterruptedException
	{
		Scanner sc = new Scanner(System.in);
        while(true) {
            int choice2;
            Restaurant r = new Restaurant();
            Cart c = new Cart();
            Wishlist w = new Wishlist();
            Order o = new Order();
           
            System.out.println("\nPlease select an option:");
            System.out.println("1. View restaurants and their menu");
            System.out.println("2. View cart");
            System.out.println("3. View wish list");
            System.out.println("4. Move items from wishlist to cart");
            System.out.println("5. Place order");
            System.out.println("6. Track order");
            System.out.println("7. View previous orders"); 
            System.out.println("8. Log out");
            
            choice2 = sc.nextInt();
            while(!(choice2 == 1 || choice2 == 2 || choice2 == 3 || choice2 == 4 || choice2 == 5 || choice2 == 6 || choice2 == 7|| choice2 == 8))
            {
                System.out.println("Invalid input!");
                System.out.println("Please select an option:");
                System.out.println("1. View restaurants and their offerings");
                System.out.println("2. View cart");
                System.out.println("3. View wish list");
                System.out.println("4. Move items from wishlist to cart");
                System.out.println("5. Place order");
                System.out.println("6. Track order");
                System.out.println("7. View previous orders");
                System.out.println("8. Log out");
                choice2 = sc.nextInt();     
            }
            
            if(choice2 == 1)
            {
            	r.view_restaurants(con,cust_id);
            }
            else if(choice2 == 2)
            {    
            	int rest_id=c.know_cart_rest_id(con, cust_id);
            	if(rest_id>0)
            		c.view_cart(con,cust_id,rest_id);
            	else
            		System.out.println("You have no items in cart");
            }
            else if(choice2 == 3)
            {   
            	
                int choice3; 
                int rest_id=w.know_wishlist_rest_id(con, cust_id);
            	if(rest_id>0)
            		w.view_wishlist(con , cust_id, rest_id); 
            	else
            		System.out.println("No item in wishlist");
            }
            
       
            else if(choice2 ==4 ) {
            	
            	int rest_id=w.know_wishlist_rest_id(con, cust_id);
            	if(rest_id>0) {
            		               		
            		PreparedStatement t4=con.prepareStatement("select * from oopd.wishlist where customer_id = ?");	
             		t4.setInt(1, cust_id);
            		ResultSet r4 = t4.executeQuery();
            	    
            		while(r4.next()) {
                	    PreparedStatement stmt=con.prepareStatement("insert into oopd.cart values(?,?,?,?)");
                		stmt.setInt(1,r4.getInt("customer_id"));
   					 	stmt.setInt(2,r4.getInt("restaurant_id")); 
   					 	stmt.setInt(3,r4.getInt("food_id"));
   					 	stmt.setInt(4,r4.getInt("food_quantity"));
   					 	stmt.executeUpdate(); 
            		}
					 	System.out.println("Record Inserted");
					   
             		PreparedStatement t5 = con.prepareStatement("DELETE FROM oopd.wishlist where customer_id=?");
           	    	t5.setInt(1, cust_id);
        	    	t5.executeUpdate();                 		
            	}
            	
            	else
            		System.out.println("You have no items in wishlist");       	
            }
            
            else if(choice2 == 5)
            {    
            	Order oo= new Order();
  	    		int rest_id=c.know_cart_rest_id(con, cust_id);
            	if(rest_id>0)
            		oo.place_order(con, cust_id, rest_id);
            	else
            		System.out.println("You have no items in your cart to place order.");
                
            }
            else if(choice2 == 6)
            {
                System.out.println("\nEnter order number to track: ");
                PreparedStatement t=con.prepareStatement("select * from oopd.order where customer_id = ? and order_status = ?");
        		t.setInt(1, cust_id);
        		t.setInt(2, 1);
        		ResultSet ro = t.executeQuery();
        		int order_id=0;
        		while(ro.next())
        		{
        			order_id=ro.getInt("order_id");
        		}
        		Tracking ob2=new Tracking();
        		ob2.track_order(con,order_id);
        
            }
            else if(choice2 == 7) {
            	Order oo= new Order();
            	oo.view_orders(con, cust_id);
            }
            
            else if(choice2 == 8)
            { 
                System.out.println("Log out successful!");
                break;
            } 
        }
        choice1(con);
	}
	
    final void master_function(Connection con) throws SQLException, InterruptedException
    {
    	choice1(con);
    }
}

class SignUp extends OnlineFoodOrderingSystem 
{
    private static final Connection con = null;

	void sign_up_attempt(Connection con) throws SQLException     
    {  
    	
  	    System.out.println("\nGlad to know you want to order food");
  	    Scanner sc=new Scanner(System.in);
  	    System.out.println("Enter your name");
  	    String customer_name = sc.next();
  	    System.out.println("Enter your email");
  	    String customer_email = sc.next();
     	System.out.println("Enter your password");
     	String customer_password = sc.next();
     	System.out.println("Enter your address");
     	String customer_address = sc.next();
     	System.out.println("Enter your city");
     	String customer_city = sc.next();
     	System.out.println("Enter your area");
     	String customer_area = sc.next();
 
 	   
     	PreparedStatement stmt=con.prepareStatement("insert into oopd.customer values(?,?,?,?,?,?,?,?,?)"); 
     	
  	    stmt.setInt(1,0);
	    stmt.setString(2,customer_name); 
	    stmt.setString(3,customer_email); 
	    stmt.setString(4,customer_password);
	    stmt.setString(5,customer_address); 
	    stmt.setString(6,customer_city);  
	    stmt.setString(7,customer_area); 
	    stmt.setInt(8,1); 
	    stmt.setInt(9,1); 
  	    
	    int i=stmt.executeUpdate();  
	    System.out.println("record inserted");
    }
}
class LogIn extends OnlineFoodOrderingSystem 
{
    int log_in_attempt(Connection con,String email, String password) throws SQLException
    {
    	  int cust_id;
		  PreparedStatement st = con.prepareStatement("select * from oopd.customer where customer_email=? and customer_password= ? ");
   	      st.setString(1, email);
   	      st.setString(2, password);
 	      ResultSet r1=st.executeQuery();
          String nme = null;
          String ps = null;
        
 	      if(r1.next()) {
 	          nme =  r1.getString("customer_email");
 	          ps=r1.getString("customer_password");
 	          if(nme.equalsIgnoreCase(email) && ps.equals(password) )
 	          {
 	        	  System.out.println( "\nUser found");
 	        	  cust_id=r1.getInt("customer_id");
 	        	  return cust_id;  
 	          }
 	      }
 	      System.out.println( "\nUser not found. Select option below");      
 	      return -1;
    }
}

class Order extends OnlineFoodOrderingSystem
{
    private boolean order_successful = false;
    boolean get_order_successful() {return order_successful;}
    void set_order_successful(boolean flag) {order_successful = flag;}
    int order_amount=0;
  	double delivery_amount=0;
  	double final_amount=0;
  	
    final void view_orders(Connection con, int cust_id) throws SQLException
    {   
    	PreparedStatement ot;
    	ResultSet ro;
    	ot = con.prepareStatement("select * from oopd.order where customer_id = ?");
	    ot.setInt(1, cust_id);
	    ro = ot.executeQuery();
	    System.out.println("All your orders are:-");
	    System.out.println("order id\torder amount\tdelivery amount\t\ttotal amount");
	    while(ro.next()) {
	    	 System.out.print(ro.getInt("order_id")+"\t\t");
	    	 System.out.print(ro.getInt("order_amount")+"\t\t");
	    	 System.out.print(ro.getInt("delivery_amount")+"\t\t\t");
	    	 System.out.println(ro.getInt("total_amount"));
	    }
    }      
    final void place_order(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException
    {    
    	calc_final_amount(con, cust_id, rest_id);	
        System.out.println("Your order total is :-  "+ final_amount);
        PreparedStatement stmt=con.prepareStatement("insert into oopd.order values(?,?,?,?,?,?,?)");
        stmt.setInt(1,0);
        stmt.setInt(2,cust_id); 
		stmt.setDouble(3,new java.util.Date().getTime());
		stmt.setDouble(4,order_amount);
		stmt.setDouble(5,delivery_amount);
		stmt.setDouble(6,final_amount);
		stmt.setInt(7,1);
		stmt.executeUpdate();
		PaymentMode ob=new PaymentMode();
		ob.pay_mode();
		rating rate=new rating();
		rate.rating_res_app(con,rest_id);
		
    }     
   
	void calc_final_amount(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException{
	    	calc_order_amount(con, cust_id, rest_id);
	    	double d = calc_delivery_amount(con, cust_id, rest_id);
	    	delivery_amount=d;
	    	System.out.println("your delivery amount is "+ delivery_amount);
	    	final_amount = order_amount + delivery_amount;
	    	final_amount = check_promo_code(con, cust_id, final_amount); 	
	 }
	
    void calc_order_amount(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException{
    	
    	PreparedStatement st4 = con.prepareStatement("select * from oopd.cart where customer_id=? and restaurant_id=? ");
    	st4.setInt(1, cust_id);
     	st4.setInt(2, rest_id);
   	    ResultSet r4=st4.executeQuery();
        int curr_food_id=0, curr_rest_id = 0,curr_food_quantity=0;
      	int f_price=0;
      	System.out.println("\n__ WELCOME TO ORDER PAGE __");
      	System.out.println("\nYou have following items in your cart .");
      	System.out.println("Name"+"		"+"Quantity"	);
      	while(r4.next()) {
      		
      		curr_rest_id = r4.getInt("restaurant_id");
      		curr_food_id = r4.getInt("food_id");
      		curr_food_quantity = r4.getInt("food_quantity");
      		PreparedStatement st5 = con.prepareStatement("select * from oopd.menu where food_id=?");
	      	st5.setInt(1, curr_food_id);
	      	ResultSet r5=st5.executeQuery();
	      	if(r5.next()) {
	      		System.out.println(r5.getString("food_name")+"		"+curr_food_quantity);
	      	}
      	}
      	
      	String change_food_name="";
      	int new_quantity=0;
      	int change_food_id=0;
      	System.out.println("\nDo you wish to modify your cart before placing order? ");
      	System.out.println("If yes then enter food name else enter null");
      	Scanner sc = new Scanner(System.in);
 
        do{
        	
        	change_food_name=sc.next();	
        	if(change_food_name.equalsIgnoreCase("null")) {
        		 System.out.println("\nYou have chosen not to change any item . So your order will be placed now");
        		 ResultSet rrt=st4.executeQuery();
        	     System.out.println("\n Final order will be placed for these items :- ");
        	     System.out.println("Name"+"		"+"Quantity"	);
        	     while(rrt.next()) {
        	      		
        	      		curr_food_id = rrt.getInt("food_id");
        	      		new_quantity = rrt.getInt("food_quantity");
        	      		PreparedStatement st5 = con.prepareStatement("select * from oopd.menu where food_id=?");
        		      	st5.setInt(1, curr_food_id);
        		      	ResultSet r5=st5.executeQuery();
        		      
        		      	if(r5.next()) {
        		      		System.out.println(r5.getString("food_name")+"		"+new_quantity);
        		      	}
        	    }
        		break;
        	}
        	else {
        		System.out.println("\nEnter new quantity");
        		new_quantity=sc.nextInt();
        		PreparedStatement s = con.prepareStatement("select * from oopd.menu where restaurant_id=? and food_name=?");
      	  	    s.setInt(1, curr_rest_id);
      	  	    s.setString(2, change_food_name);
      	  	    ResultSet r3=s.executeQuery();
      	    	if(r3.next())
      	    	{
      	    		int res = r3.getInt("restaurant_id"); 
      	    		String t=r3.getString("food_name");
      	    		if(t.equals(change_food_name) && res==rest_id)
      	  	    	{
      	    			change_food_id=r3.getInt("food_id");
      	  	    	}
      	    	} 
      	    	
        		PreparedStatement stt = con.prepareStatement("UPDATE oopd.cart SET food_quantity=? where customer_id=? and food_id=?");
        	    stt.setInt(1, new_quantity);
        	    stt.setInt(2,cust_id );
        	    stt.setInt(3, change_food_id);
        	    stt.executeUpdate();
        	}
        	
        	System.out.println("Record updated");
        }
        while(true);

   	    ResultSet rnew=st4.executeQuery();
   	    		
   	    while(rnew.next()) {
   	    	curr_food_id = rnew.getInt("food_id");
   	    	curr_food_quantity = rnew.getInt("food_quantity");
   	    	PreparedStatement st5 = con.prepareStatement("select * from oopd.menu where food_id=?");
	      	st5.setInt(1, curr_food_id);
	      	ResultSet r5=st5.executeQuery();
	      	if(r5.next()) {
	      		f_price = r5.getInt("food_price");
	      	}
   	    	order_amount = order_amount + f_price*curr_food_quantity;
   	    	PreparedStatement ds = con.prepareStatement("DELETE FROM oopd.cart where customer_id=? and food_id=?");
   	    	ds.setInt(1, cust_id);
	  	    ds.setInt(2, curr_food_id);
	    	ds.executeUpdate();
   	    }
   	    
   	    	System.out.println("\n\nYour Food total is - "+ order_amount);
   	    
   	    if(order_amount<101) {
   	    	System.out.println("Please select more items (Order value should be above 100");
   	    	 order_amount=0;
   	    	Restaurant r1 = new Restaurant();
   	    	r1.view_restaurants(con,cust_id);	
   	    	calc_order_amount(con, cust_id, rest_id);  	    	
   	    }  
    }
 
    double check_promo_code(Connection con, int cust_id, double final_amount) throws SQLException{
    	   
        int status50=0;
        int status20=0;
        Scanner sc = new Scanner(System.in);
        PreparedStatement st6 = con.prepareStatement("select * from oopd.customer where customer_id=?");
        st6.setInt(1, cust_id);
        ResultSet r6=st6.executeQuery();
        
        if(final_amount<100) {
        	System.out.println("Promo codes are available for order amounts more than 100 only ");
        }
        else {
	        if(r6.next()) {
			        status50 = r6.getInt("promo_code_1");
			        status20 = r6.getInt("promo_code_2");
			       
			        if(status50>0)
			        {
			        	System.out.println("Woho! You can save upto 50% . Would you like to avail this offer right now? Enter 1 for YES");
				        int response1 = sc.nextInt();
				        if(response1==1) {
				        	
					        final_amount = final_amount*0.5;
					        System.out.println("Your new Food total is - "+ final_amount);
					        int promo_code_1=0;
					        PreparedStatement st8 = con.prepareStatement("UPDATE oopd.customer SET promo_code_1=? where customer_id=?");
					        st8.setInt(1, promo_code_1);
					        st8.setInt(2, cust_id);
				            st8.executeUpdate();
			            
				        }
				        
				        else {
				        	System.out.println("Sadly you didn't used our offer!");
				        }
	           }
	       
		        else if(status20>0)
		        {
			        System.out.println("Woho! You can save upto 20% . Would you like to avail this offer right now? Enter 1 for YES");
			        int response2 = sc.nextInt();
			        if(response2==1) {
			        	
				        final_amount = final_amount*(0.8);
				        System.out.println("Your new Food total is - "+ final_amount);
				        int promo_code_2=0;
				        PreparedStatement st7 = con.prepareStatement("UPDATE oopd.customer SET promo_code_2=? where customer_id=?");
				        st7.setInt(1, promo_code_2);
			            st7.setInt(2, cust_id);
			            st7.executeUpdate();
		           
			        }
			        else {
			        	System.out.println("Sadly you didn't used our offer!");
			        }
		        }
		    
		        else
		        System.out.println("\nSorry you have availed all your offers already !");
		        }
        }
        return final_amount;
        }
    
    double calc_delivery_amount(Connection con, int cust_id, int rest_id) throws SQLException
	{    
	
		String cust_city="", cust_area="", res_city="", res_area="";
		double cust_lat=0.0, cust_lon=0.0, res_lat=0.0, res_lon=0.0, dist=0.0;
		PreparedStatement st;
		st = con.prepareStatement("select * from oopd.customer where customer_id = ?");
	    st.setInt(1, cust_id);
	    ResultSet r = st.executeQuery();
	    if(r.next()) {
	    	cust_city = r.getString("customer_city");
	    	cust_area = r.getString("customer_area");
	    }
		st = con.prepareStatement("select * from oopd.restaurant where restaurant_id = ?");
	    st.setInt(1, rest_id);
	    r = st.executeQuery();
	    if(r.next()) {
	    	res_city = r.getString("restaurant_city");
			res_area = r.getString("restaurant_area");
	    }
		st = con.prepareStatement("select * from oopd.area_coordinates where city = ? and area = ?");
	    st.setString(1, cust_city);
		st.setString(2, cust_area);
	    r = st.executeQuery();
	    if(r.next()) {
	    	cust_lat = r.getDouble("latitude");
	    	cust_lon = r.getDouble("longitude");
	    }
		st = con.prepareStatement("select * from oopd.area_coordinates where city = ? and area = ?");
	    st.setString(1, res_city);
		st.setString(2, res_area);
	    r = st.executeQuery();
	    if(r.next()) {
	    	res_lat = r.getDouble("latitude");
	    	res_lon = r.getDouble("longitude");
	    }
		dist = Math.ceil(distance(cust_lat, cust_lon, res_lat, res_lon));
		delivery_amount = dist * 5;
		return delivery_amount;
		
	}
	static double distance(double lat1, double lon1, double lat2,double lon2)
	{
	    double dlat, dlon,c, r,a;
		r = 6371;
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
	    lon2 = Math.toRadians(lon2);
	    dlat = lat2 - lat1;
		dlon = lon2 - lon1;
	    a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);     
	    c = 2 * Math.asin(Math.sqrt(a));
	    return(c * r);
	}
 
}


class Restaurant extends Order
{
    void view_restaurants(Connection con,int cust_id) throws SQLException, InterruptedException
    {
    	if(cust_id==0)
    		return;
    	Scanner sc=new Scanner(System.in);
    	PreparedStatement sm = con.prepareStatement("select * from oopd.customer where customer_id=?");
    	sm.setInt(1, cust_id);
  	    ResultSet r0=sm.executeQuery();
  	    String city=null;
    	if(r0.next())
    	{
    		int t=r0.getInt("customer_id");
    		if(t==cust_id)
  	    	{
    			city=r0.getString("customer_city");
  	    	}
    	}
    	PreparedStatement st = con.prepareStatement("select * from oopd.restaurant where restaurant_city=?");
    	st.setString(1, city);
  	    ResultSet r1=st.executeQuery();
        String cty = null;
        int rest_id=0;
        System.out.println("These are the Restaurants in " + city);
  	    while(r1.next()) {
  	    	cty =  r1.getString("restaurant_city");
  	    	if(cty.equalsIgnoreCase(city))
  	    	{
  	    		String res_c;
  	    		res_c = r1.getString("restaurant_name");
  	    		System.out.println("\nRestaurant Name:-"+res_c);
  	    		System.out.println("Menu of "+res_c);
  	    		rest_id = r1.getInt("restaurant_id");
  	    		Menu res_menu=new Menu();
  	    		res_menu.view_menu(con,rest_id);
  	    	}
  	    }
  	 
		  int order_choice=0;
		  System.out.println("\nEnter your choice where you want to add food items:\n1.Cart\n2.Wishlist");
		  order_choice=sc.nextInt();
  	    System.out.println("Enter the Restauarnt name from which you want to order");
  	    
  	    String ord_res=sc.next();
  	    PreparedStatement stm = con.prepareStatement("select * from oopd.restaurant where restaurant_name=?");
    	stm.setString(1, ord_res);
  	    ResultSet r2=stm.executeQuery();
    	if(r2.next())
    	{
    		String t=r2.getString("restaurant_name");
    		if(t.equalsIgnoreCase(ord_res))
  	    	{
    			rest_id=r2.getInt("restaurant_id");
  	    	}
    	}
  	    System.out.println("The estimated time for delivering the item is 30 minutes");
    	System.out.println("Enter the Food items and their quantity which you want to order from "+ord_res+" Restaurant and enter 'null' to stop");
    	String food_name;
  	    do 
  	    {
  	    	food_name=sc.next();
  	    	if(food_name.equalsIgnoreCase("null") )
  	    		break;
  	    	int quant = sc.nextInt();
  	    	int fod_id=0;
  	  	    PreparedStatement s = con.prepareStatement("select * from oopd.menu where restaurant_id=? and food_name=?");
  	  	    s.setInt(1, rest_id);
  	  	    s.setString(2, food_name);
  	  	    ResultSet r3=s.executeQuery();
  	    	if(r3.next())
  	    	{
  	    		int res = r3.getInt("restaurant_id"); 
  	    		String t=r3.getString("food_name");
  	    		if(t.equalsIgnoreCase(food_name)&&res==rest_id)
  	  	    	{
  	    			fod_id=r3.getInt("food_id");
  	  	    	}
  	    	} 
  	
			if (order_choice==1)
			{
				PreparedStatement ss = con.prepareStatement("select * from oopd.cart where customer_id=? and food_id=?");
				ss.setInt(1, cust_id);
				ss.setInt(2, fod_id);
				ResultSet rr=ss.executeQuery();
				if(rr.next()) {
					quant = quant+rr.getInt("food_quantity");
					PreparedStatement ds = con.prepareStatement("DELETE FROM oopd.cart where customer_id=? and food_id=?");
					ds.setInt(1, cust_id);
					ds.setInt(2, fod_id);
					ds.executeUpdate();
  	            }
				 PreparedStatement stmt=con.prepareStatement("insert into oopd.cart values(?,?,?,?)");
				 stmt.setInt(1,cust_id);
				 stmt.setInt(2,rest_id); 
				 stmt.setInt(3,fod_id); 
				 stmt.setInt(4,quant);
				 stmt.executeUpdate();  
				 System.out.println("Record inserted");
			 }
				
			 if (order_choice==2)
			{
				PreparedStatement ss = con.prepareStatement("select * from oopd.wishlist where customer_id=? and food_id=?");
				ss.setInt(1, cust_id);
				ss.setInt(2, fod_id);
				ResultSet rr=ss.executeQuery();
				if(rr.next()) {
					quant = quant+rr.getInt("food_quantity");
					PreparedStatement ds = con.prepareStatement("DELETE FROM oopd.wishlist where customer_id=? and food_id=?");
					ds.setInt(1, cust_id);
					ds.setInt(2, fod_id);
					ds.executeUpdate();
  	            }
				 PreparedStatement stmt=con.prepareStatement("insert into oopd.wishlist values(?,?,?,?)");
				 stmt.setInt(1,cust_id);
				 stmt.setInt(2,rest_id); 
				 stmt.setInt(3,fod_id); 
				 stmt.setInt(4,quant);
				 stmt.executeUpdate();  
				 System.out.println("Record inserted");
			 }
			 
  	    }while(true);
  	    
		if(order_choice==1) 
		{
			System.out.println("\n __ WELCOME TO CART __");
			Cart crt=new Cart();
			crt.view_cart(con,cust_id, rest_id);
		}
		if(order_choice==2) 
		{
			System.out.println("\n __ WELCOME TO WISHLIST __");
			Wishlist wst=new Wishlist();
			wst.view_wishlist(con,cust_id, rest_id);
		}
    }
}

class Menu extends Restaurant
{
	void view_menu(Connection con, int rest_id) throws SQLException
	{
		PreparedStatement st = con.prepareStatement("select * from oopd.menu where restaurant_id=?");
    	st.setInt(1, rest_id);
  	    ResultSet r1=st.executeQuery();
        int res = 0;
        String fod_nm = null;
        int fod_prc = 0;
        System.out.println("Food name\t\tFood price");
  	    while(r1.next()) {
  	    	res =  r1.getInt("restaurant_id");
  	    	if(res==rest_id)
  	    	{
  	    		fod_nm = r1.getString("food_name");
  	    		fod_prc = r1.getInt("food_price");
  	    		System.out.println(fod_nm+"\t\t"+fod_prc);
  	    	}
  	    }
	}
}

class Cart extends Order
{  
	int know_cart_rest_id(Connection con, int cust_id) throws SQLException {
		 PreparedStatement stt = con.prepareStatement("select * from oopd.cart where customer_id=?");
	 	 stt.setInt(1, cust_id);
		 ResultSet rtt=stt.executeQuery();
		 if(rtt.next())
			 return rtt.getInt("restaurant_id");	 
		 else
			 return -1;	 
	}
	
    void view_cart(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException    
    {
    	
    	System.out.println("You have added following things in your cart");
 	    PreparedStatement st = con.prepareStatement("select * from oopd.restaurant where restaurant_id=?");
 	    st.setInt(1, rest_id);
 	    ResultSet r1=st.executeQuery();
 	    int res = 0;
 	    String res_nm = null;
 	   
	    if(r1.next()) {

	    	res =  r1.getInt("restaurant_id");

	    	if(res==rest_id)
	    	{
	    		res_nm = r1.getString("restaurant_name");
	    		System.out.println("From "+res_nm +" restaurant");
	    		System.out.println("Food item\tQuantity");
	    		PreparedStatement st3 = con.prepareStatement("select * from oopd.cart where restaurant_id=?");
	      	    st3.setInt(1, rest_id);
	    	    ResultSet r3=st3.executeQuery();
	    	    int fod_id=0;
	    	    
	    	    while(r3.next()) {

	    	    	int res3 =  r3.getInt("restaurant_id");
	    	    	if(res3==rest_id)
	    	    		fod_id=r3.getInt("food_id");
	    	    
	    	    	PreparedStatement st2 = con.prepareStatement("select * from oopd.menu where restaurant_id=? and food_id=?");
	    	    	st2.setInt(1, rest_id);
	    	    	st2.setInt(2, fod_id);
	    	    	ResultSet r2=st2.executeQuery();
	    	    	
	    	    	int res2 = 0;
	    	    	int tmp;
	    	    	String food_item = null;
	    	    	
	    	    	while(r2.next()) {
	    	    		res2 =  r2.getInt("restaurant_id");
	    	    		tmp= r2.getInt("food_id");
	    	    		if(res2==rest_id && tmp==fod_id)
	    	    			food_item=r2.getString("food_name");
	    	    	}  	    	
	    	    	System.out.println(food_item+"\t\t"+r3.getInt("food_quantity"));

	    	      }
	    	}
	    } 
	 
  	    	Order o= new Order();
 	    	o.place_order(con, cust_id, rest_id);
  	 }
    void update_cart(String auth_mn)
    {
        
    }         
}

class Wishlist extends Order
{    
	int know_wishlist_rest_id(Connection con, int cust_id) throws SQLException {
		 PreparedStatement stt = con.prepareStatement("select * from oopd.wishlist where customer_id=?");
	 	 stt.setInt(1, cust_id);
		 ResultSet rtt=stt.executeQuery();
		 if(rtt.next())
			 return rtt.getInt("restaurant_id");	 
		 else
			 return -1;	 
	}
	
    void view_wishlist(Connection con, int cust_id, int rest_id) throws SQLException    
    {
    
          System.out.println("You have following items in your wish-list");
          PreparedStatement st = con.prepareStatement("select * from oopd.restaurant where restaurant_id=?");
          st.setInt(1, rest_id);
          ResultSet r1=st.executeQuery();
          int res = 0;
          String res_nm = null;
         
          if(r1.next()) {
            res =  r1.getInt("restaurant_id");
            if(res==rest_id)
            {
            	res_nm = r1.getString("restaurant_name");
                System.out.println("Food item\tQuantity");
                PreparedStatement st3 = con.prepareStatement("select * from oopd.wishlist where restaurant_id=?");
                st3.setInt(1, rest_id);
                ResultSet r3=st3.executeQuery();
                int fod_id=0;
                while(r3.next()) {
                    int res3 =  r3.getInt("restaurant_id");
                    if(res3==rest_id)
                    {
                        fod_id=r3.getInt("food_id");                    
                    }
                PreparedStatement st2 = con.prepareStatement("select * from oopd.menu where restaurant_id=? and food_id=?");
                st2.setInt(1, rest_id);
                st2.setInt(2, fod_id);
                ResultSet r2=st2.executeQuery();
                int res2 = 0;
                int tmp;
                String food_item = null;

                while(r2.next()) {
                    res2 =  r2.getInt("restaurant_id");
                    tmp= r2.getInt("food_id");
                    if(res2==rest_id && tmp==fod_id)
                    	food_item=r2.getString("food_name");
                }
                System.out.println(food_item+"\t\t"+r3.getInt("food_quantity"));
              }
            }
        }        
     }
}

class PaymentMode extends OnlineFoodOrderingSystem
{
	void pay_mode() throws InterruptedException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Following are the available payment options:-");
		System.out.println("1. Credit Card");
		System.out.println("2. Debit Card");
		System.out.println("3. Net Banking");
		System.out.println("4. UPI");
		System.out.println("5. Mobile Wallets");
		System.out.println("6. Cash on Delivery");
		System.out.println("Enter the mode of the payment which you want to opt");	
		int mode=sc.nextInt();
		if(!(mode==1||mode==2||mode==3||mode==4||mode==5||mode==6))
		{
			System.out.println("Please enter a valid payment mode");
			pay_mode();
		}
		System.out.println("---Directing to the payment gateway----");
		TimeUnit.SECONDS.sleep(1);
		if(mode==1||mode==2)
		{
			String name,date;
			long card_no;
			int cvv_no;
			System.out.println("\n__Enter the card details__");
			System.out.println("\nEnter the name of cardholder");
			name=sc.next();
			System.out.println("Enter the card number");
			card_no=sc.nextLong();
			System.out.println("Enter the expiration date of the card in dd/mm/yyyy format");
			date=sc.next();
			System.out.println("Enter the cvv number");
			cvv_no=sc.nextInt();
			System.out.println("Verifying your card details");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else if(mode==3)
		{
			int opt,pin;
			String email,password;
			System.out.println("\nChoose your Bank from which you want to pay");
			System.out.println("1. SBI");
			System.out.println("2. HDFC");
			System.out.println("3. Punjab National Bank");
			System.out.println("4. Axis Bank");
			System.out.println("5. ICICI Bank.");
			System.out.println("6. Kotak Mahindra Bank");
			opt=sc.nextInt();
			System.out.println("Connecting...");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Enter your email");
			email=sc.next();
			System.out.println("Enter our password");
			password=sc.next();
			System.out.println("Enter your pin");
			pin=sc.nextInt();
			System.out.println("Verifying your credentials");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else if(mode==4)
		{

			int opt,pin;
			System.out.println("\nChoose the UPI app from which you want to pay");
			System.out.println("1. Patym");
			System.out.println("2. Mobiwik");
			System.out.println("3. PhonePe");
			System.out.println("4. Google Pay");
			System.out.println("5. Freecharge");
			System.out.println("6. Amazon Pay");
			opt=sc.nextInt();
			System.out.println("Enter the pin");
			pin=sc.nextInt();
			System.out.println("Connecting...");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else if(mode==5)
		{
			int wallet;
			System.out.println("\nChoose the wallet from which you want to order");
			System.out.println("1. Patym");
			System.out.println("2. Mobiwik");
			System.out.println("3. Freecharge");
			wallet=sc.nextInt();
			System.out.println("Connecting... to your wallet");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else 
		{
			System.out.println("\nOrder is succesfuly placed, thanks for ordering");
			System.out.println("Delivery agent will keep the order at your door and step back by 2 meters.");
			System.out.println("Place money on top of the package & step back by 2 meters while the agent collects the cash.");
		}
	}
}

class Tracking extends OnlineFoodOrderingSystem
{
    void track_order(Connection con, int order_id) throws SQLException
	{   

        long curr_time = new java.util.Date().getTime();
        long estimated_time=0;
		PreparedStatement st = con.prepareStatement("select * from oopd.order where order_id = ?");
	    st.setInt(1, order_id);
	   
		ResultSet r = st.executeQuery();
		
	    if(r.next()) { 
	    	estimated_time = (long)r.getDouble("order_time") + 1800000l;  
	    } // Estimated time is always 30 minutes ahead of order time.

	    if(curr_time <= estimated_time)
			System.out.println("Your order will reach you in " + String.format("%.2f", (estimated_time - curr_time) / 60000.0) + " mins.");
		else if(curr_time > estimated_time)
		{
			if(curr_time - estimated_time <= 180000l)    // Current time is within 33 minutes of order time.
				System.out.println("We are sorry for the slight delay!\nKindly wait for another " 
						+ String.format("%.2f", (180000l - (curr_time - estimated_time)) / 60000.0) + " mins.");
		    else
			{	
				Scanner sc = new Scanner(System.in);
				int i;
				System.out.println("We are sorry for the delay!\nYour order is delayed by " 
						+ String.format("%.2f", (180000l - (curr_time - estimated_time)) / 60000.0) + " mins.");
			    System.out.println("Since we could not deliver your item within time, your order is now eligible for cancellation.");
				System.out.println("Please select an option below:\n1. Cancel order\n2. Wait for delivery");
				i = sc.nextInt();
				while(!(i == 1 || i == 2))
				{
					System.out.println("Invalid input!\nPlease select an option below:\n1. Cancel order\n2. Wait for delivery");
					i = sc.nextInt();
				}	
				if(i == 1)
				{	
					System.out.println("Your order has been cancelled!");
				    System.out.println("If payment has been done, the refund will be reflected in your account within the next two business days.");
					// Appropriate order cancellation action.
				}
				else
					System.out.println("Thank you for your patience!\nWe are trying our best to deliver your order at the earliest.");	
		    }
		}
    }
}

//when order is received then we have to call this function
class rating extends OnlineFoodOrderingSystem
{
	void rating_res_app(Connection con,int res_id) throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		int f=0,res_rate=0,app_rate=0;
		while(true) {
		System.out.println("Enter the rating for the restaurant in the range 1 to 5");
		res_rate=sc.nextInt();
		System.out.println("Enter the rating for this App in the range 1 to 5");
		app_rate=sc.nextInt();
		if(res_rate>=0&&res_rate<=5&&app_rate>=0&&app_rate<=5)
			f=1;
		if(f==1)
			break;
		else
			System.out.println("Please enter a valid rating in the range 1 to 5");
		}
		System.out.println("Thanks for rating our App and the restaurant");
		PreparedStatement stmt=con.prepareStatement("insert into oopd.rating values(?,?,?)");
        stmt.setInt(1,res_id);
        stmt.setInt(2,res_rate);
        stmt.setInt(3, app_rate);
		stmt.executeUpdate();
	}
}

public class master
{
    public static void main(String args[]) throws SQLException, InterruptedException 
    {    
	    Scanner sc=new Scanner(System.in);
        OnlineFoodOrderingSystem ofos = new OnlineFoodOrderingSystem();
        Connection con = ofos.sqlconnect();
        ofos.master_function(con);
    }
}