****************************************
*  BSC Java Test Canidate Application  *
*  Payment Tracker					   *
****************************************

INTRODUCTION

The application is Java, Maven-based application built using Spring framework,
in particular Spring Boot components, allowing easy assembly and usage.


APPLICATION ASSEMBLY INSTRUCTIONS

Pre-requisites:

1. Java JDK version 8 is installed
2. Maven version 3 is installed
3. The computer has internet connection to allow 3rd party library downloads
   from Maven repositories.

How to build:

1. Navigate to payment-tracker directory.
2. Execute the following in the command line:
	mvn clean package
3. You should see a "BUILD SUCCESS" message after the assembly is completed.
   

   
APPLICATION START

1. Navigate to payment-tracker/target directory.
2. Run command
	java -jar payment-tracker-1.0.jar
	
Optional argument - full path to the payment records file. Example:
	java -jar payment-tracker-1.0.jar C:\payments\payment_records.txt

Any other provided arguments are ignored.


ASSUMPTIONS AND MODIFICATIONS

- Periodicity of printing net amounts was changed from one minute to 10 seconds,
  to allow quickly seeing the changes made.
  
- Input file parsing - Invalid lines are ignored, but parsing continues until the
  end of file is reached. If the specified file does not exist or a read lock cannot
  be obtained, and error message is displayed, but the program continues.
  
- Console input parsing - Invalid user inputs result in displaying an error message
  with error description

- Payment records persistence - The records (both loaded from the file or entered
  manually) are stored just in memory, no database (even memory-based) is utilized.
  
- Exchange rates configuration:

	* Exchange rates file 'exchange.rates' is a text file packed inside the jar 
	  file. It is assumed that this approach is sufficient for the demo application,
	  in the real world the file would be a part of external configuration. It's
	  loaded once during application startup and cached in memory.
	  
	* The file contains the following records:
	  USD/CZK=25.26017
	  USD/EUR=0.93218364
	  USD/CAD=1.33
	  USD/RUB=56.12