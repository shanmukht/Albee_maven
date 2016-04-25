package com.albee.DataBeans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Arrays;


public class DbconInMemory {

	//	private LoginData loginData;
	//	private ShippingData shippingData;
	static int totalRows;

	public DbconInMemory() throws SQLException{
		//	this.loginData = getLoginData();
		//	this.shippingData = getUserDetails();
	}


	public LoginData getLoginData() throws SQLException {

		//	 openServerModeInBrowser();
		return insertWithPreparedStatementLogin();
	}

	public ShippingData getShippingData() throws SQLException {

		return insertUserDetails();
	}

	public WebserviceData getWebserviceData() throws SQLException {

		return insertApiDetails();
	}

	// H2 Database Viewer Example


	private static final String DB_DRIVER = "org.h2.Driver";

	private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	//	    public static void main(String[] args) throws Exception {
	//	        try {
	//	            openServerModeInBrowser();
	//	            insertWithPreparedStatement();
	//
	//	        } catch (SQLException e) {
	//	            e.printStackTrace();
	//	        }
	//	    }

	private static LoginData insertWithPreparedStatementLogin() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		PreparedStatement insertPreparedStatement = null;
		PreparedStatement selectPreparedStatement = null;
		
		System.out.println("Values from table 1 Albeeuser***************");


		String CreateQuery = "CREATE TABLE Albeelogin(email varchar(255), password varchar(255), logintype varchar(50))";
		String InsertQuery = "INSERT INTO Albeelogin" + "(email, password, logintype) values" + "(?,?,?)";
		String SelectQuery = "select * from Albeelogin";
		LoginData loginData = new LoginData();

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, "abcxyz@gmail.com");
			insertPreparedStatement.setString(2, "abc123xyz");
			insertPreparedStatement.setString(3, "RightUser_RightPwd");
			insertPreparedStatement.addBatch();

			insertPreparedStatement.setString(1, "wrongUsername");
			insertPreparedStatement.setString(2, "abc123xyz");
			insertPreparedStatement.setString(3, "RightUser_WrongPwd");
			insertPreparedStatement.addBatch();

			insertPreparedStatement.setString(1, "abcxyz@gmail.com");
			insertPreparedStatement.setString(2, "wrongPassword");
			insertPreparedStatement.setString(3, "WrongUser_RightPwd");
			insertPreparedStatement.addBatch();

			insertPreparedStatement.setString(1, "");
			insertPreparedStatement.setString(2, "");
			insertPreparedStatement.setString(3, "EmptyUser_EmptyPwd");
			insertPreparedStatement.addBatch();
			insertPreparedStatement.executeBatch();
			

			insertPreparedStatement.close();

			selectPreparedStatement = connection.prepareStatement(SelectQuery,
					              ResultSet.TYPE_SCROLL_INSENSITIVE, 
					              ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = selectPreparedStatement.executeQuery();

			System.out.println("H2 In-Memory Database inserted through PreparedStatement");
			
			try {
				rs.last();
				totalRows = rs.getRow();
				rs.beforeFirst();
			} 
			catch(Exception ex)  {
				System.out.println("*********** ERROR *******: ");
			}
			System.out.println("*********** Total User login rows in table***********: "+totalRows);
			
			String[] email = new String[totalRows];
			String[] password = new String[totalRows];
			String[] logintype = new String[totalRows];
			int i=0;
			
			while (rs.next()) {
				System.out.println("Email " + rs.getString("email") + " Password " + rs.getString("password") + "Userdetails are ->"+rs.getString("logintype"));
				
				 email[i]=rs.getString(1);
				 password[i] = rs.getString(2);
				 logintype[i]=rs.getString(3);
				loginData.setEmail(email);
				loginData.setPassword(password);
				loginData.setLogintype(logintype);
				i++;

			}
			//System.out.println("******** : endpointUrl : " + Arrays.deepToString(email));
			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
			System.out.println("Connection is closed");
		}
		return loginData;  
	}


	private static ShippingData insertUserDetails() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		PreparedStatement insertPreparedStatement = null;
		PreparedStatement selectPreparedStatement = null;


		String CreateQuery = "CREATE TABLE AlbeeUser(fname varchar(25), lname varchar(25), company varchar(25), stadd1 varchar(50), stadd2 varchar(25), city varchar(25),zipcode varchar(10), state varchar(3), phone varchar(11))";
		String InsertQuery = "INSERT INTO AlbeeUser" + "(fname, lname, company, stadd1, stadd2, city, zipcode, state, phone) values" + "(?,?,?,?,?,?,?,?,?)";
		String SelectQuery = "select * from AlbeeUser";
		ShippingData shippingData = new ShippingData();

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, "abc");
			insertPreparedStatement.setString(2, "xyz");
			insertPreparedStatement.setString(3, "abc corp");
			insertPreparedStatement.setString(4, "100 north point st");
			insertPreparedStatement.setString(5, "suite 200");
			insertPreparedStatement.setString(6, "san francisco");
			insertPreparedStatement.setString(7, "94133");
			insertPreparedStatement.setString(8, "CA");
			insertPreparedStatement.setString(9, "1111117890");

			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();

			System.out.println("H2 In-Memory Database inserted through PreparedStatement for Albee user details");
			while (rs.next()) {
				System.out.println("fname " + rs.getString("fname") + " lname " + rs.getString("lname"));
				String fname=rs.getString(1);
				String lname = rs.getString(2);
				String company = rs.getString(3);
				String stadd1 = rs.getString(4);
				String stadd2 = rs.getString(5);
				String city = rs.getString(6);
				String zipcode = rs.getString(7);
				String state = rs.getString(8);
				String phone = rs.getString(9);

				shippingData.setFname(fname);
				shippingData.setLname(lname);
				shippingData.setCompany(company);
				shippingData.setStAdd1(stadd1);
				shippingData.setStAdd2(stadd2);
				shippingData.setCity(city);
				shippingData.setZipCode(zipcode);
				shippingData.setState(state);
				shippingData.setPhone(phone);

			}

			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
			System.out.println("Connection is closed");
		}
		return shippingData;  
	}

	private static WebserviceData insertApiDetails() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		PreparedStatement insertPreparedStatement = null;
		PreparedStatement selectPreparedStatement = null;


		String CreateQuery = "CREATE TABLE ApiDetails(endpointUrl varchar(255), authUsername varchar(50), authPassword varchar(50), authkey varchar(100), content_type varchar(25), apiService varchar(25), apiType varchar(35))";
		String InsertQuery = "INSERT INTO ApiDetails" + "(endpointUrl, authUsername, authPassword, authkey, content_type, apiService, apiType) values" + "(?,?,?,?,?,?,?)";
		String SelectQuery = "select * from ApiDetails";
		WebserviceData webserviceData = new WebserviceData();

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
		
			insertPreparedStatement.setString(1, "http://parabank.parasoft.com/parabank/services/bank/customers/12212/");
			insertPreparedStatement.setString(2, "");
			insertPreparedStatement.setString(3, "");
			insertPreparedStatement.setString(4, "");
			insertPreparedStatement.setString(5, "application/xml");
			insertPreparedStatement.setString(6, "get");
			insertPreparedStatement.setString(7, "No_Authentication");
			insertPreparedStatement.addBatch();
			
			insertPreparedStatement.setString(1, "http://localhost:8082/service/test/Ashish");
			insertPreparedStatement.setString(2, "nisum1");
			insertPreparedStatement.setString(3, "nisum1");
			insertPreparedStatement.setString(4, "");
			insertPreparedStatement.setString(5, "application/text");
			insertPreparedStatement.setString(6, "get");
			insertPreparedStatement.setString(7, "Basic_Authentication");
			insertPreparedStatement.addBatch();
		

			insertPreparedStatement.setString(1, "http://jsonplaceholder.typicode.com/posts/1");
			insertPreparedStatement.setString(2, "");
			insertPreparedStatement.setString(3, "");
			insertPreparedStatement.setString(4, "");
			insertPreparedStatement.setString(5, "application/json");
			insertPreparedStatement.setString(6, "get");
			insertPreparedStatement.setString(7, "No_Authentication");
			insertPreparedStatement.addBatch();
			insertPreparedStatement.executeBatch();
			


			insertPreparedStatement.close();

			selectPreparedStatement = connection.prepareStatement(SelectQuery, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = selectPreparedStatement.executeQuery();

//			ResultSetMetaData rsmd = rs.getMetaData();
//
//			int columnsNumber = rsmd.getColumnCount();

			System.out.println("H2 In-Memory Database inserted through PreparedStatement for API details");

			try {
				rs.last();
				totalRows = rs.getRow();
				rs.beforeFirst();
			} 
			catch(Exception ex)  {
				System.out.println("*********** ERROR *******: ");
			}
			System.out.println("*********** Total API rows in table***********: "+totalRows);


			String[] endpointUrl = new String [totalRows];
			String[] authusername = new String [totalRows];
			String[] authpassword = new String [totalRows];
			String[] authkey = new String [totalRows];
			String[] content_type = new String [totalRows];
			String[] apiService = new String [totalRows];
			String[] apiType = new String [totalRows];

			int i = 0;

			while (rs.next()) {

				System.out.println("endPointURL= " + rs.getString("endpointUrl") + " APIType= " + rs.getString("apiType"));
			//	String endpointUrl=rs.getString(1);
//				authusername = rs.getString(2);
//				 authpassword = rs.getString(3);
//				 authkey = rs.getString(4);
//				 content_type = rs.getString(5);
//				 apiService = rs.getString(6);
//				 apiType=rs.getString(7);

				endpointUrl[i] = rs.getString(1);
				authusername[i] = rs.getString(2);
				 authpassword[i] = rs.getString(3);
				 authkey[i] = rs.getString(4);
				 content_type[i] = rs.getString(5);
				 apiService[i] = rs.getString(6);
				 apiType[i]=rs.getString(7);
				 webserviceData.setEndpointUrl(endpointUrl);
					webserviceData.setAuthUsername(authusername);
					webserviceData.setAuthPassword(authpassword);
					webserviceData.setAuthkey(authkey);
					webserviceData.setContent_type(content_type);
					webserviceData.setApiService(apiService);
					webserviceData.setApiType(apiType);

				
				i++;
			}
			
			System.out.println("******** : endpointUrl : " + Arrays.deepToString(endpointUrl));
//			 webserviceData.setEndpointUrl(endpointUrl);
//				webserviceData.setAuthUsername(authusername);
//				webserviceData.setAuthPassword(authpassword);
//				webserviceData.setAuthkey(authkey);
//				webserviceData.setContent_type(content_type);
//				webserviceData.setApiService(apiService);
//				webserviceData.setApiType(apiType);

			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
			System.out.println("Connection is closed");
		}
		return webserviceData;  
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	//	    private static void openServerModeInBrowser() throws SQLException {
	//	        Server server = Server.createTcpServer().start();
	//	        System.out.println("Server started and connection is open.");
	//	        System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:test");
	//	    }
}


