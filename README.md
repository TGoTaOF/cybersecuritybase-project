# cybersecuritybase-project
This is for the first project for the cybersecuritybase MOOC course.

This project contains program that has 5 of OWASP's top 10 securify flaws, reports for them and way to fix them.

TODO:  

###**Issue: A1:2017 Injection**  
**Steps to reproduce:**  
**1.**  
**2.**  
**3.**  
**4.**  

**How to fix:**  

###**Issue: A2:2017 Broken Authentication**  
**Steps to reproduce:**  
**1.** Start/restart the web application.  
**2.** go to address "localhost:8080" in browser.  
(here 8080 may need to replaced with port where the application is running).  
**3.A.** I have included a text file named "216passwords" to this projects github root folder for use.  
**3.B.** Or make your own text file(.txt) that has words on it's lines. More lines, the better, but for this demonstration only requirment is that word "qwerty" is past the 10th line somewhere in the file.  
**4.** Start OWASP ZAP (make sure browser uses proxy in port that ZAP is listening).  
**5.** Ty to log in to our application with username "User2" and password "asd123", it wont succeed, but ZAP should have listened that.  
**6.** In ZAP find the POST:login and choose the "asd123" in the Request window/tab, rigth click and choose "Fuzz".  
**7.** In the new window select Payloads, select file as type and then select the included file or the text file that we made with words on the lines. (Select ok/add until back to fuzzing window)  
**8.** Now press "Start Fuzzer". We can see that the web application allows unlimited tries for the login.  
**9.** Also in the results of the fuzz, at the try with "qwerty", we see that we did not get error(localhost:8080/login?error) as location in response window/tab, meaning we found the password.  

**How to fix:** This needs to be fixed by limiting how many times one user can try to login to the system. This can be made by implementing a limit to tries or enabling automated credential stuffing protection.  

###**Issue: A5:2017 Broken Access Control**  
**Steps to reproduce:**  
**1.** Start/restart the web application.  
**2.** go to address "localhost:8080/admin" in browser.  
(here 8080 may need to replaced with port where the application is running)  
**3.** We see that we got to the applications admin page without any kind of authentication.  

**How to fix:** Admin page should make sure only admin users can access it. This can be done for example by checking admin rights in "AdminController.java", where mapping for that url is done, and redirect user to different page if user doesn't have admin rights. Other possible implementation would be in "SecurityConfiguration.java" with the HttpSecurity object.  

###**Issue: A7:2017 Cross-Site Scripting (XSS)**  
**Steps to reproduce:**  
**1.** Start/restart the web application.  
**2.** go to address "localhost:8080" in browser.  
(here 8080 may need to replaced with port where the application is running)  
**3.** Enter "User1" as username and "letmein" as password. Log in.  
**4.** Enter "<script language="javascript" type="text/javascript">alert("Ha Ha Ha");</script>" to the message field and press "Add".  
**5.** Pop-up message will be shown. This means site can be used to execute scripts, and is vulnerable to XSS.  

**How to fix:** All texts should be escaped in the html. In this project using th:text in the user.html when ever we are printing text would do the trick.  

###**Issue: A10:2017 Insufficient Logging**  
**Steps to reproduce:**  
**1.**  
**2.**  
**3.**  
**4.**  

**How to fix:**  
