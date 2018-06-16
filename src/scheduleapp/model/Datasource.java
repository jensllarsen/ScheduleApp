/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jens Larsen
 */
public class Datasource {

    // data base info
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_NAME = "U04H9n";
    public static final String DB_URL = "jdbc:mysql://52.206.157.109/" + DB_NAME;
    public static final String DB_USERNAME = "U04H9n";
    public static final String DB_PASSWORD = "53688238693";

    // table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_CUSTOMER = "customer";
    private static final String TABLE_ADDRESS = "address";
    private static final String TABLE_CITY = "city";
    private static final String TABLE_COUNTRY = "country";

    // table columns
    private static final String COLUMN_USER_USERNAME = "userName";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_CUSTOMER_CUSTOMERID = "customerId";
    private static final String COLUMN_CUSTOMER_CUSTOMERNAME = "customerName";
    private static final String COLUMN_CUSTOMER_ADDRESSID = "addressId";
    private static final String COLUMN_CUSTOMER_ACTIVE = "active";
    private static final String COLUMN_CUSTOMER_CREATEDATE = "createDate";
    private static final String COLUMN_CUSTOMER_CREATEDBY = "createdBy";
    private static final String COLUMN_CUSTOMER_LASTUPDATE = "lastUpdate";
    private static final String COLUMN_CUSTOMER_LASTUPDATEBY = "lastUpdateBy";
    private static final String COLUMN_ADDRESS_ADDRESSID = "addressId";
    private static final String COLUMN_ADDRESS_ADDRESS = "address";
    private static final String COLUMN_ADDRESS_ADDRESS2 = "address2";
    private static final String COLUMN_ADDRESS_CITYID = "cityId";
    private static final String COLUMN_ADDRESS_POSTALCODE = "postalCode";
    private static final String COLUMN_ADDRESS_PHONE = "phone";
    private static final String COLUMN_ADDRESS_CREATEDATE = "createDate";
    private static final String COLUMN_ADDRESS_CREATEDBY = "createdBy";
    private static final String COLUMN_ADDRESS_LASTUPDATE = "lastUpdate";
    private static final String COLUMN_ADDRESS_LASTUPDATEBY = "lastUpdateBy";
    private static final String COLUMN_CITY_CITYID = "cityId";
    private static final String COLUMN_CITY_CITY = "city";
    private static final String COLUMN_CITY_COUNTRYID = "countryId";
    private static final String COLUMN_CITY_CREATEDATE = "createDate";
    private static final String COLUMN_CITY_CREATEDBY = "createdBy";
    private static final String COLUMN_CITY_LASTUPDATE = "lastUpdate";
    private static final String COLUMN_CITY_LASTUPDATEDBY = "lastUpdatedBy";
    private static final String COLUMN_COUNTRY_COUNTRYID = "countryId";
    private static final String COLUMN_COUNTRY_COUNTRY = "country";
    private static final String COLUMN_COUNTRY_CREATEDATE = "createDate";
    private static final String COLUMN_COUNTRY_CREATEDBY = "createdBy";
    private static final String COLUMN_COUNTRY_LASTUPDATE = "lastUpdate";
    private static final String COLUMN_COUNTRY_LASTUPDATEBY = "lastUpdateBy";

    // static queries
    /* Query Customers with Addresses
    SELECT customer.customerName,customer.active,customer.customerId,
	address.address,address.addressId,address.address2,
    city.city,address.postalCode,address.phone, country.country
    FROM (((customer INNER JOIN address on customer.addressId = address.addressId)
    INNER JOIN city on address.cityId = city.cityId)
    INNER JOIN country on city.countryId = country.countryId)
    WHERE customer.active = 1;
     */
    private static final String QUERY_CUSTOMERS_WITH_ADDRESSES
            = "SELECT " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_CUSTOMERNAME
            + "," + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ACTIVE + ","
            + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_CUSTOMERID + ","
            + TABLE_ADDRESS + "." + COLUMN_ADDRESS_ADDRESS + ","
            + TABLE_ADDRESS + "." + COLUMN_ADDRESS_ADDRESSID + ","
            + TABLE_ADDRESS + "."
            + COLUMN_ADDRESS_ADDRESS2 + ","
            + TABLE_CITY + "." + COLUMN_CITY_CITY + "," + TABLE_ADDRESS + "."
            + COLUMN_ADDRESS_POSTALCODE + "," + TABLE_ADDRESS + "."
            + COLUMN_ADDRESS_PHONE + "," + TABLE_COUNTRY + "." + TABLE_COUNTRY + " "
            + "FROM (((" + TABLE_CUSTOMER + " INNER JOIN " + TABLE_ADDRESS
            + " on " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ADDRESSID + " = "
            + TABLE_ADDRESS + "." + COLUMN_ADDRESS_ADDRESSID + ") INNER JOIN "
            + TABLE_CITY + " on " + TABLE_ADDRESS + "." + COLUMN_ADDRESS_CITYID
            + " = " + TABLE_CITY + "." + COLUMN_CITY_CITYID + ") INNER JOIN "
            + TABLE_COUNTRY + " on " + TABLE_CITY + "." + COLUMN_CITY_COUNTRYID
            + " = " + TABLE_COUNTRY + "." + COLUMN_COUNTRY_COUNTRYID
            + ") WHERE " + TABLE_CUSTOMER + "." + "active = 1;";

    // Add customer
    /*
    INSERT INTO customer (customerName, addressId, active,
    createDate, createdBy, lastUpdate, lastUpdateBy)
     */
    private static final String ADD_CUSTOMER_START
            = "INSERT INTO " + TABLE_CUSTOMER
            + " (" + COLUMN_CUSTOMER_CUSTOMERNAME + "," + COLUMN_CUSTOMER_ADDRESSID
            + "," + COLUMN_CUSTOMER_ACTIVE + "," + COLUMN_CUSTOMER_CREATEDATE
            + "," + COLUMN_CUSTOMER_CREATEDBY + "," + COLUMN_CUSTOMER_LASTUPDATE
            + "," + COLUMN_CUSTOMER_LASTUPDATEBY + ") ";

    // Add Address
    /*
    INSERT INTO address (address, address2, cityId, postalCode, phone, createDate,
    createdBy, lastUpdate, lastUpdateBy)
     */
    private static final String ADD_ADDRESS_START
            = "INSERT INTO " + TABLE_ADDRESS
            + "(" + COLUMN_ADDRESS_ADDRESS + "," + COLUMN_ADDRESS_ADDRESS2 + ","
            + COLUMN_ADDRESS_CITYID + "," + COLUMN_ADDRESS_POSTALCODE + ","
            + COLUMN_ADDRESS_PHONE + "," + COLUMN_ADDRESS_CREATEDATE + ","
            + COLUMN_ADDRESS_CREATEDBY + "," + COLUMN_ADDRESS_LASTUPDATE + ","
            + COLUMN_ADDRESS_LASTUPDATEBY + ") ";

    // Add City
    /*
    INSERT INTO city (city,countryId,createDate,createdBy, lastUpdate, lastUpdateBy)
     */
    private static final String ADD_CITY_START
            = "INSERT INTO " + TABLE_CITY
            + "(" + COLUMN_CITY_CITY + "," + COLUMN_CITY_COUNTRYID + ","
            + COLUMN_CITY_CREATEDATE + ","
            + COLUMN_CITY_CREATEDBY + "," + COLUMN_CITY_LASTUPDATE + ","
            + COLUMN_CITY_LASTUPDATEDBY + ") ";

    // Add Country
    /*
    INSERT INTO country (country,createDate,createdBy, lastUpdate, lastUpdateBy)
     */
    private static final String ADD_COUNTRY_START
            = "INSERT INTO " + TABLE_COUNTRY
            + "(" + COLUMN_COUNTRY_COUNTRY + "," + COLUMN_COUNTRY_CREATEDATE + ","
            + COLUMN_COUNTRY_CREATEDBY + "," + COLUMN_COUNTRY_LASTUPDATE + ","
            + COLUMN_COUNTRY_LASTUPDATEBY + ") ";

    // globals
    private static Connection connection = null;
    public static String loggedInUser = null;

    // public methods
    public static boolean open() throws ClassNotFoundException {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connected to database : " + DB_NAME);
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find class " + e.getMessage());
            return false;
        }
    }

    public static void close() throws SQLException {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkLogin(String username, String password)
            throws ClassNotFoundException, SQLException {
        boolean open = Datasource.open();

        if (!open) {
            System.out.println("Unable to open database connection when trying login!");
            return false;
        }

        Statement statement = connection.createStatement();

        String passwordQuery = "SELECT * "
                + "FROM " + TABLE_USER + " "
                + "WHERE " + COLUMN_USER_USERNAME + "='" + username + "' AND "
                + COLUMN_USER_PASSWORD + "='" + password + "'";

        ResultSet result = statement.executeQuery(passwordQuery);

        // if username and password is found in the database, ie a result was returned
        if (result.next()) {
            loggedInUser = username;
            // close connection
            statement.close();
            Datasource.close();
            return true;
        } else {
            // close connection
            statement.close();
            Datasource.close();
            return false;
        }
    }

    public static List<CustomerWithAddress> getCustomersWithAddresses() throws ClassNotFoundException, SQLException {

        List<CustomerWithAddress> customers = new ArrayList<>();

        boolean open = Datasource.open();

        if (!open) {
            System.out.println("Unable to open database connection when trying get customers with addresses!");
            return null;
        }

        try (Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(QUERY_CUSTOMERS_WITH_ADDRESSES)) {

            while (result.next()) {

                CustomerWithAddress tempCustomer = new CustomerWithAddress();

                tempCustomer.setCustomerID(result.getInt(COLUMN_CUSTOMER_CUSTOMERID));
                tempCustomer.setCustomerName(result.getString(COLUMN_CUSTOMER_CUSTOMERNAME));
                tempCustomer.setAddressId(result.getInt(COLUMN_CUSTOMER_ADDRESSID));
                tempCustomer.setAddress(result.getString(COLUMN_ADDRESS_ADDRESS));
                tempCustomer.setAddress2(result.getString(COLUMN_ADDRESS_ADDRESS2));
                tempCustomer.setCity(result.getString(COLUMN_CITY_CITY));
                tempCustomer.setPostalCode(result.getString(COLUMN_ADDRESS_POSTALCODE));
                tempCustomer.setPhone(result.getString(COLUMN_ADDRESS_PHONE));
                tempCustomer.setCountry(result.getString(COLUMN_COUNTRY_COUNTRY));
                tempCustomer.setActive(true);

                customers.add(tempCustomer);
            }
        }

        return customers;
    }

    public static boolean addCustomer(Customer customer) throws ClassNotFoundException, SQLException {

        String name = customer.getCustomerName();
        int addressId = customer.getAddressID();
        int active = 1;
        String createDate = LocalDateTime.now().toString();
        String createdBy = loggedInUser;
        String lastUpdate = createDate;
        String lastUpdateBy = loggedInUser;

        String customerInsert = ADD_CUSTOMER_START
                + "VALUES (" + "'" + name + "'" + "," + addressId + ","
                + active + "," + "'" + createDate + "'" + "," + "'"
                + createdBy + "'" + "," + "'"
                + lastUpdate + "'" + "," + "'" + lastUpdateBy + "'" + ");";

        boolean open = Datasource.open();

        if (!open) {
            System.out.println("Error opening datasource!");
            return false;
        }

        boolean result = false;

        try (Statement statement = connection.createStatement()) {

            result = statement.execute(customerInsert);

        } catch (SQLException e) {
            System.out.println("SQL Error adding customer: " + e.getMessage());
        }

        Datasource.close();
        return result;
    }

    public static boolean addAddress(Address address) throws ClassNotFoundException, SQLException {

        String address1 = address.getAddress();
        String address2 = address.getAddress2();
        int cityId = address.getCityId();
        String postalCode = address.getPostalCode();
        String phone = address.getPhone();
        String createDate = LocalDateTime.now().toString();
        String createdBy = loggedInUser;
        String lastUpdate = createDate;
        String lastUpdateBy = loggedInUser;

        String addressInsert = ADD_ADDRESS_START
                + "VALUES ("
                + "'" + address1 + "'" + ","
                + "'" + address2 + "'" + ","
                + cityId + ","
                + "'" + postalCode + "'" + ","
                + "'" + phone + "'" + ","
                + "'" + createDate + "'" + ","
                + "'" + createdBy + "'" + ","
                + "'" + lastUpdate + "'" + ","
                + "'" + lastUpdateBy + "'" + ","
                + ");";

        boolean open = Datasource.open();

        if (!open) {
            System.out.println("Error opening datasource!");
            return false;
        }

        boolean result = false;

        try (Statement statement = connection.createStatement()) {

            result = statement.execute(addressInsert);

        } catch (SQLException e) {
            System.out.println("SQL Error adding address: " + e.getMessage());
        }

        Datasource.close();
        return result;
    }

    public static boolean addCity(City city) throws ClassNotFoundException, SQLException {

        String cityName = city.getCity();
        int countryId = city.getCountryid();
        String createDate = LocalDateTime.now().toString();
        String createdBy = loggedInUser;
        String lastUpdate = createDate;
        String lastUpdateBy = loggedInUser;

        String addressInsert = ADD_CITY_START
                + "VALUES ("
                + "'" + cityName + "'" + ","
                + countryId + ","
                + "'" + createDate + "'" + ","
                + "'" + createdBy + "'" + ","
                + "'" + lastUpdate + "'" + ","
                + "'" + lastUpdateBy + "'" + ","
                + ");";

        boolean open = Datasource.open();

        if (!open) {
            System.out.println("Error opening datasource!");
            return false;
        }

        boolean result = false;

        try (Statement statement = connection.createStatement()) {

            result = statement.execute(addressInsert);

        } catch (SQLException e) {
            System.out.println("SQL Error adding city: " + e.getMessage());
        }

        Datasource.close();
        return result;
    }

    public static boolean addCountry(Country country) throws ClassNotFoundException, SQLException {

        String countryName = country.getCountry();

        String createDate = LocalDateTime.now().toString();
        String createdBy = loggedInUser;
        String lastUpdate = createDate;
        String lastUpdateBy = loggedInUser;

        String countryInsert = ADD_COUNTRY_START
                + "VALUES ("
                + "'" + countryName + "'" + ","
                + "'" + createDate + "'" + ","
                + "'" + createdBy + "'" + ","
                + "'" + lastUpdate + "'" + ","
                + "'" + lastUpdateBy + "'" + ","
                + ");";

        boolean open = Datasource.open();

        if (!open) {
            System.out.println("Error opening datasource!");
            return false;
        }

        boolean result = false;

        try (Statement statement = connection.createStatement()) {

            result = statement.execute(countryInsert);

        } catch (SQLException e) {
            System.out.println("SQL Error adding country: " + e.getMessage());
        }

        Datasource.close();
        return result;
    }
}
