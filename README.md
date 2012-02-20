AtlantBH Custom JMeter Components
=======================
  
What is it?
-----------------------

AtlantBH Custom JMeter Components is a set of JMeter extensions
developed by AtlantBH that currently includes:

+	OAuth Sampler

+	REST Sampler

+	JMS Tools (Java Request Sampler)

+	JSON to XML Converter

+	XML Format Post-processor

+	HDFS Operations Sampler

+	HBase Scan Sampler

+	HBase RowKey Sampler

+	Hadoop Job Tracker Sampler	

For more information about individual components, please visit: http://www.atlantbh.com/jmeter-components/


Requirements
-----------------------

+	Java 1.6 or greater

+ 	JMeter 2.6

+	Maven 3.0 or greater


Build Instructions
-----------------------

-	Go to the top-level directory of the project and run:  
	```
	mvn clean install
	```	 
-	All components will be compiled in the 'target' subdirectory.


Installation Instructions
-----------------------

-	Copy the compiled jar (atlantbh-components-1.0.0-SNAPSHOT.jar) 
	from the 'target' directory to the '/lib/ext' directory in your 
	local JMeter installation.
	
-	Copy all the jars from the 'target/lib' directory to the 'lib' 
	directory in your local JMeter installation.

To test that everything is installed correctly, try adding the OAuth Sampler
in a JMeter test plan (JMeter menu -> Edit -> Add -> Sampler -> OAuth Sampler).


Licensing and legal issues
-----------------------
Copyright 2011 AtlantBH

AtlantBH Custom Jmeter Components is licensed under the Apache License, Version 2.0.

For legal and licensing issues, please look the files:  
LICENSE  
NOTICE
  
  