# Java Project: Shop Management System

The Shop Management System is a Java-based application designed to manage the operations of a retail shop. It incorporates features such as inventory management, sales tracking, employee authentication, and graphical interfaces, along with robust XML file handling capabilities for data export/import. The project has been incrementally developed from a basic implementation to a feature-rich solution.

## Project Overview

This project was initially based on the following repository: [www.Stucom-Pelai DAM1_M03_UF2_POO_Shop](https://github.com/Stucom-Pelai/MP0485_RA4_POO_Shop.git)

The objective of this project is to develop a system capable of managing various aspects of a shop, including product inventory, sales, and employee access. The project gradually evolved from a simple setup into a robust solution, integrating file handling, integration xml handling libraries (DOM, SAX, JAXB) and database connectivity (SQL).

## Features
1) **Unlimited Inventory, Sales, and Products**: Removed any limits on the number of items in inventory, sales transactions, and products available for sale. The system now handles an unrestricted number of entries.
   
2) **Product Removal**: Added functionality to remove specific products from the inventory, updating stock levels accordingly.

3) **Purchase Date and Time**: Implemented a feature to save the date and time of every purchase made.

4) **Load Inventory from File**: Developed a method to import the shop's inventory from a file.

5) **Export Sales Data**: Created functionality to export sales data to a file.
  
6) **Login System**: Developed a secure login system for employees to authenticate before accessing the system.

7) **Graphical User Interface (GUI)**
   - LoginView: A secure login interface for employee authentication.
   - ShopView: A dashboard displaying options to manage products, inventory, and sales.
   - CashView: A window showing the total cash available in the store.
   - ProductView: An intuitive interface for adding, updating, and removing products.
   - InventoryView: A table-based interface (using JTable) to display detailed inventory information in an organized manner.

8) **XML Data Handling**
   - DOM Writer:
      - Generates structured XML files containing inventory details (name, price, stock).
      - Attributes include total product count and currency information.
   - SAX Reader:
      - Parses XML files to update the inventory dynamically.
      - Reads stock, prices, and availability from the file.
   - JAXB Marshaller/Unmarshaller:
      - Exports inventory to timestamped XML files (e.g., inventory_YYYY-MM-DD.xml).
      - Imports inventory from XML files, integrating the data into the system seamlessly.
9) **Database Integration**
   - **Initial Inventory from Database:** Load the shop's initial inventory directly from a connected SQL database.
   - **Dynamic Inventory Updates:** Automatically sync inventory changes made in the application with the database.
   - **Export Inventory to Database:** Save the current state of the shop's inventory back to the database for record-keeping.
## Getting Started
### Prerequisites
- **Java Development Kit (JDK):** Ensure that you have JDK 17 or higher installed.
- **SQL Database:** Set up a local or remote SQL database for employee session management.
- **Git:** Clone the project from the repository.
```
git clone https://github.com/nsaladie/MP03_POO_Shop.git
```
