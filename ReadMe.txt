Readme file:

Steps to be followed for server startup: 
	- Paste the complete csj3 folder under your tomcat's webapps directory.
		Example for me: C:\apache-tomcat-7.0.34\webapps\csj5
	- Set the required classpath for MySQL, MongoDB, Tomcat and java.
	- Create the schemas as given in the sql file for MySQL and mongoDB.
	- Start MySQL, MongoDB and tomcat servers respectively.
	- Once the server is up and running, hit the localhost url: http://localhost/csj5/home and this will take you to the home page of the website.

Login details:
	- To login as Store Manager, username:admin and password:admin
	- To login as Sales Manager, username:admin and password:admin
	- To login as customer, please register for the first time as there will be no records in your local database copy.

- Store Manager can view Inventory and Daily Sales Report - Numerical as well as  Graphical Representation.	
- Sales Manager can manage user registration using Add Users tab and also manage user's orders.
- Data Analytics tab is only accessible to Store Manager.