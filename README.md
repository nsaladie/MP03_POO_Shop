# Java Project: Shop Management System

The Shop Management System is a Java-based application designed to manage the operations of a retail shop. It incorporates features such as inventory management, sales tracking, employee authentication, and graphical interfaces, along with robust XML file handling capabilities for data export/import. The project has been incrementally developed from a basic implementation to a feature-rich solution with multiple database options.

## Project Overview

This project was initially based on the following repository: [www.Stucom-Pelai DAM1_M03_UF2_POO_Shop](https://github.com/Stucom-Pelai/MP0485_RA4_POO_Shop.git)

The objective of this project is to develop a system capable of managing various aspects of a shop, including product inventory, sales, and employee access. The project gradually evolved from a simple setup into a robust solution, integrating file handling, integration xml handling libraries (DOM, SAX, JAXB), and multiple database connectivity options (SQL and MongoDB).

## Getting Started
### Prerequisites
- **Java Development Kit (JDK):** Ensure that you have JDK 17 or higher installed.
- **SQL Database:** Set up a local or remote SQL database for employee session management.
- **MongoDB:** Set up a local MongoDB instance running on port 27017.
- **Maven:** For dependency management
- **Git:** Clone the project from the repository.

```
git clone https://github.com/nsaladie/MP03_POO_Shop.git
```

## Features
### Core Functionality
1) **Unlimited Inventory, Sales, and Products**: Removed any limits on the number of items in inventory, sales transactions, and products available for sale. The system now handles an unrestricted number of entries.
   
2) **Product Removal**: Added functionality to remove specific products from the inventory, updating stock levels accordingly.

3) **Purchase Date and Time**: Implemented a feature to save the date and time of every purchase made.

4) **Load Inventory from File**: Developed methods to import the shop's inventory from multiple sources including files and databases.

5) **Export Sales Data**: Created functionality to export sales data to files and databases.
  
6) **Login System**: Developed a secure login system for employees to authenticate before accessing the system.

### Graphical User Interface (GUI)
   - LoginView: A secure login interface for employee authentication.
   - ShopView: A dashboard displaying options to manage products, inventory, and sales.
   - CashView: A window showing the total cash available in the store.
   - ProductView: An intuitive interface for adding, updating, and removing products.
   - InventoryView: A table-based interface (using JTable) to display detailed inventory information in an organized manner.
   - SaleView: An interface for making sales transactions.
   - SalesListView: A detailed view of all sales records.

### Data Management
#### XML Data Handling
   - DOM Writer:
      - Generates structured XML files containing inventory details (name, price, stock).
      - Attributes include total product count and currency information.
   - SAX Reader:
      - Parses XML files to update the inventory dynamically.
      - Reads stock, prices, and availability from the file.
   - JAXB Marshaller/Unmarshaller:
      - Exports inventory to timestamped XML files (e.g., inventory_YYYY-MM-DD.xml).
      - Imports inventory from XML files, integrating the data into the system seamlessly.

#### Database Integration
   - **SQL Database Support:**
      - JDBC implementation for SQL databases
      - Hibernate ORM integration for more robust database operations
   - **MongoDB Integration:**
      - Connect to MongoDB for NoSQL database support
      - Store and retrieve inventory data in MongoDB collections
      - Maintain historical inventory data
      - Track sales data in a dedicated collection
      - User authentication via MongoDB

### Architecture
- **DAO Pattern:** Data Access Object pattern implementation for database operations
- **MVC Architecture:** Clear separation between model, view, and controller components
