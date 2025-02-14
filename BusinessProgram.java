/* CIS-18A Course Project
Chris Shey Enriquez
Option #1 Business Order and Delivery Program

The purpose is to allow customer to place orders for various services offered by a business and schedule an appropriate time that satisfies both parties

Write a Java program to place order and set appointment for delivery of goods or services from a business of your choice (restaurant, grocery, mobile pet spa, mobile car detailer, home cleaning, home repair/improvement, mobile car repair, etc....).


The program should prompt the user to select products or services and appointment or
delivery date, and time based on business operation time.
The program should display the user selection on screen.
The program should output the order summary and appointment in a text file

*/

import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


//Interface represents Service
interface Service {
String getServiceName();
}
//Class represents business service order
class Order {
	private String customerName;
	private Service service;
	private String appointmentDate;
	private String appointmentTime;

//Constructor
public Order(String customerName, Service service, String appointmentDate,
String appointmentTime) {
	this.customerName = customerName;
	this.service = service;
	this.appointmentDate = appointmentDate;
	this.appointmentTime = appointmentTime;
}
//Method return order summary as string
public String getOrderSummary() {
	return "Customer: " + customerName + "\nService: " + service.getServiceName() + "\nDate: " + appointmentDate + "\nTime: " + appointmentTime;
}
//Method save order summary to file
public void saveToFile() {
	try (PrintWriter writer = new PrintWriter(new FileWriter("order_summary.txt", true))) {
	writer.println(getOrderSummary());
	writer.println("-----------------------------");
	} catch (IOException e) {
	System.out.println("Error saving order: " + e.getMessage());
	}
 }
}

//Concrete clasess
class MobileCarWash implements Service {
public String getServiceName(){
	return "Mobile Car Wash";
}
}
class GroceryDelivery implements Service {
public String getServiceName() {
	return "Grocery Delivery";
}
}
class HomeCleaning implements Service {
 public String getServiceName() {
	return "Home Cleaning";
}
}
class PetGrooming implements Service {
public String getServiceName() {
	return "Pet Grooming";
}
}
public class BusinessProgram {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
			System.out.println("Welcome to the Business Order and Delivery Program!");

//Array to store orders
Order[] orders = new Order[5];
int orderCount = 0;

//Loop for multiple orders
while (orderCount < orders.length) {
System.out.print("Enter your name (or type 'exit' to quit):");
String name = scanner.nextLine();
if (name.equalsIgnoreCase("exit")) {
System.out.println("Exiting the program.");
break;
} 

//Service selction with input validation
Service selectedService = null;
while (selectedService == null) {
	System.out.println("Select a service:");
	System.out.println("1. Mobile Car Wash");
	System.out.println("2. Grocery Delivery");
	System.out.println("3. Home Cleaning");
	System.out.println("4. Pet Grooming");
	System.out.println("Enter choice (1-4)or 0 to exit:");
	
int serviceChoice;
try {
serviceChoice = scanner.nextInt();
} catch (InputMismatchException e) {
System.out.println("Invalid input. Please enter a number.");
scanner.next();
continue;
}
scanner.nextLine();

//Validiate 
switch (serviceChoice) {
case 1:
selectedService = new MobileCarWash();
	break;
case 2:
selectedService = new GroceryDelivery();
break;
case 3:
selectedService = new HomeCleaning();
break;
case 4:
selectedService = new PetGrooming();
break;
case 0:
System.out.println("Exiting program.");
scanner.close();
return; 
default:
System.out.println("Error: Invalid choice. Please enter a number between 1 and 4.");
break;
}
}
//Get appointment date with validation
System.out.println("Enter appointment date (YYYY-MM-DD): ");
String date = scanner.nextLine();
String time;
while (true) {
System.out.print("Enter appointment time (HH:MM AM/PM): ");
time = scanner.nextLine();
if (isValidTime(time)){
break;
} else {
System.out.println("Invalid time. Please enter a time between 8:00 AM and 8:00 PM.");
  }
 }

//Create order object then save array
Order order = new Order(name, selectedService, date, time);
orders[orderCount] = order;
orderCount++;

System.out.println("\nOrder Summary:\n" + order.getOrderSummary());
order.saveToFile();

	System.out.println("\nYour order has been confirmed! Thank you for using our service.");
	}
	scanner.close();
  }

//Validate if time is between business hours
public static boolean isValidTime(String time) {
	try {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
	LocalTime enteredTime = LocalTime.parse(time, formatter);
	LocalTime openingTime = LocalTime.of(8, 0);
	LocalTime closingTime = LocalTime.of(20, 0);
	return !enteredTime.isBefore(openingTime) && ! enteredTime.isAfter(closingTime);
} catch (DateTimeParseException e) {
	return false;
	
    }
  }
 }

