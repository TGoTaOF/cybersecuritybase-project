# cybersecuritybase-project
This is for the first project of the cybersecuritybase MOOC course.  

This project contains program that has 5 of OWASP's top 10 securify flaws, reports for them and way to fix them.  

## 

# Project description:
This project is a web application that lets users send messages and also read messages send by other users.  

Application is very simple and it only allows users to send messages to general chat where everyone can read send messages.  

Users can only see last 5 send messages, while admin is able to see last 20 messages.  

Admin can also delete users.  

Application does not have a way to register as user, as this application is just for this project, thus it only has already existing accounts to point out the 5 security flaws.  

##

# Project's webside structure:  
**"/login"**  :Login page.  


**"/user/{username}"**  :Currently logged in user's page for reading last 5 messages and sending new messages.  


**"/admin"**  :admin page to delete users and see 20 last messages.  


**"/production"**  :This page simulates a note to devs on a production test server for my report of flaw "A6:2017 Security Misconfiguration"  

##

# Project's source code structure:

**Source Packages:**

	sec.project:
	CyberSecurityBaseProjectApplication.java  :Main class, starts application.  


	sec.project.config:
	CustomUserDetailsService.java  :Contains init for already existing accounts and messages.  
	SecurityConfiguration.java  :Some security.  
	  

	sec.project.controller:
	AccountController.java  : Http address controller for pages that users can access.  
	AdminController.java  :Http address controller for admin pages.  
	ProductionController.java  :Http address controller for simulated production server.  


	sec.project.domain:
	Account.java  :Users account class for database.  
	Message.java  :Class for messages for database.  


	sec.project.repository:
	AccountRepository.java  :Class "skeleton" for database with accounts in it.  
	MessageRepository.java  :Class "skeleton" for database with messages in it.  


**Other (Resource) Packages:**

	
	**templates**
	admin.html  :page for admins  
	production.html  :page for simulated production server note.  
	user.html  :page for users.  
	

##

# Flaws (A2, A5, A6, A7 ,A10 from [Top 10 flaws](https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf)):

## **Issue: A2:2017 Broken Authentication**  
	**Steps to reproduce:**  
	**1.** Start/restart the web application.  
	**2.** go to address "localhost:8080" in browser.  
	(here 8080 may need to be replaced with port where the application is running).  
	**3.A.** I have included a text file named "216passwords" to this projects github root folder for use.  
	**3.B.** Or make your own text file(.txt) that has words on it's lines. More lines, the better, but for this demonstration only requirment is that word "qwerty" is past the 10th line somewhere in the file.  
	**4.** Start OWASP ZAP (make sure browser uses proxy in port that ZAP is listening).  
	**5.** Ty to log in to our application with username "User2" and password "asd123", it wont succeed, but ZAP should have listened that.  
	**6.** In ZAP find the POST:login and choose the "asd123" in the Request window/tab, rigth click and choose "Fuzz".  
	**7.** In the new window select Payloads, select file as type and then select the included file or the text file that we made with words on the lines. (Select ok/add until back to fuzzing window)  
	**8.** Now press "Start Fuzzer". We can see that the web application allows unlimited tries for the login.  
	**9.** Also in the results of the fuzz, at the try with "qwerty", we see that we did not get error(localhost:8080/login?error) as location in response window/tab, meaning we found the password.  

**How to fix:** This needs to be fixed by limiting how many times one user can try to login to the system as well as how often login attemps can be made(for example one try every second). This can be made by implementing a limit to tries or enabling some third party automated credential stuffing protection(from java, spring etc.). Other thing that could be done is password control for allowed passwords. System should not allow user to use weak and well-known passwords such as "qwerty". Good way would be to require the password to have small and big alphabets as well as atleast one number and one special letter.  

## **Issue: A5:2017 Broken Access Control**  
	**Steps to reproduce:**  
	**1.** Start/restart the web application.  
	**2.** go to address "localhost:8080" in browser.  
	(here 8080 may need to be replaced with port where the application is running)  
	**3.** Login with username "User3" and password "123321".  
	**4.** When logged in change url of the site in browser to "localhost:8080/admin". Press enter.
	**5.** We see that we got to the applications admin page without any kind of authentication.  

**How to fix:** Admin page should make sure only admin users can access it. This can be done for example by checking admin rights in "AdminController.java", where mapping for that url is done, and redirect user to different page if user doesn't have admin rights. Other possible implementation would be in "SecurityConfiguration.java" with the HttpSecurity object.  

## **Issue: A6:2017 Security Misconfiguration**  
	**Steps to reproduce:**  
	**1.** Start/restart the web application.  
	**2.** go to address "localhost:8080/production" in browser.  
	(here 8080 may need to replaced with port where the application is running).  
	**3.** We got in to applications old test server that contains information of what usernames and passwords were used during testing. For administerator username seems to be "admin" and password "admin".
	**4.** Now to go back to the live application, go to address "localhost:8080/login" in browser.  
	(here 8080 may again need to be replaced with port where the application is running).  
	**3.** Let's try using the admin accounts username and password that we found in the production server. Enter "admin" as username and "admin" as password. Press login.  
	**4.** We are now logged in as administerator.   

**How to fix:** This was caused by live applications security not configured at all as far as admin account goes, and so the password and username for the admin were default ones. This is to be fixed by changing the admin username and password in the live version of the application. Also outsiders should not have access to developer teams notes, so should take better care of any notes with usernames and accounts too.   

## **Issue: A7:2017 Cross-Site Scripting (XSS)**  
	**Steps to reproduce:**  
	**1.** Start/restart the web application.  
	**2.** go to address "localhost:8080" in browser.  
	(here 8080 may need to be replaced with port where the application is running)  
	**3.** Enter "User1" as username and "letmein" as password. Login.  
	**4.** Enter "<script language="javascript" type="text/javascript">alert("Ha Ha Ha");</script>" to the message field and press "Add".  
	**5.** Pop-up message will be shown. This means site can be used to execute scripts, and is vulnerable to XSS.  

**How to fix:** All texts should be escaped, in daatbases and in the html. In this project using th:text in the user.html whenever we are printing text would do the trick.  

## **Issue: A10:2017 Insufficient Logging**  
	**Steps to reproduce:**  
	**1.** Start/restart the web application.  
	**2.** go to address "localhost:8080" in browser.  
	(here 8080 may need to be replaced with port where the application is running)  
	**3.** Login with username "User3" and password "123321".  
	**4.** When logged in change url of the site in browser to "localhost:8080/admin". Press enter.  
	**5.** Now we are in admin page without proper rights. Next Press "delete" under "User1". Now account User1 is deleted from the application database.  
	**6.A.** If using netbeans go read the output log of the execution, it has nothing about deleting an account.  
	**6.B.** Or if running jar on commandline/terminal, read the output log, it has nothing about deleting an account.  
	**7.** No log files were also made, so: We successfully deleted some other person's account without leaving any evidence.

**How to fix:** Application needs better logging and monitoring. Everytime something modifies databases, it should be logged in someway so that recovery is possible.  
