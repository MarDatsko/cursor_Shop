# **Hello**
## **It is my First JavaFX project!**

**_Project name_**: Cursor_Shop\
**_Project architecture_**: [link to Architecture!](https://docs.google.com/document/d/1pER0GMJi0UiTHflIvRDQ2-xpEoXuct0njte7eMCm5LY/edit?fbclid=IwAR1v4ZZe2O3GODokTU35zDzNjn8dQipPJMeC6pPySoqynLhI5xYA5FW5YvU)\
**_Project author_**: Marian Datsko\
**_Database_**: MySQL

### Before running the Application, you must do this action.
1) Create SQL database.
2) In the package resources is file:`CursorShop.sql` with a script what you must run, for creating tables.
3) Check if your configs data are equels with data from package dao `class Configs`.
4) Try to run the Application.

### If an application is doesn't run write to me <marian.datsko@gmail.com>. If all a good and application works, I tell you what you can do in this App.
* You see a login page where you can log in or click on reference registration and sign up. (You already have two created users <MarDatsko,1111> and <admin, admin>).
* If you choose `registration` you must see a new page, where you should fill all fields your data. After that click on the button `save`. You must return on the login page, where you may fill your data and switch to a new page `MainPage`.
  * On first listView print all Product list what have our Shop. If you double click on any product, this product switch on the second listView where you see your order.  If you double click on a product in table MyOrder this product remove with orders.
  * ChoiceBox `Sort By` you can choose like you want to see products list.
  * Field `search` you can find a product by brand name like HP, Apple...
  * Button `buy` if you choose products what you want to buy click on a button `buy` and first your order will send admin, and he must check the order and confirm it, if admin confirms your order you again press button and successful buying products.
  * Under table MyOrder you see a table where shows messages what you write or admin write to you.
  * On the right side table MyOrder you see total cost your order and how much money you have.
  * Pagination isn't working, because I didn't understand how work with him.
* If you log in like admin, you switch on `AdminPage`.
  * In ChoiceBox you may choose user and see his order list and his status, he is block or unblock, his order confirm or unconfirm, you can change user status click on buttons.
  * From below is the same message field and table where you can see messages and write them.
  * On the right side window you have three field, where you may edit product and click button `save`, or add product click button with the same name. But you can't edit product with UserOrder, only after press button `AllProducts`.
