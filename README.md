
**<h1 align="center"> --Movie Theater Seating Challenge--</h1>**


## Programming Language
Java

## Problem
"is to design and write a seat assignment program to maximize both customer satisfaction and customer safety. 
For the purpose of public safety, assume that a buffer of three and/or one row is required."


**Approach**

* Using a Greedy Algorithm Implemention :
* Starting from the upper left corner and filling in seats from 0 to 20 and rows from 9 to 0.
* Trying to keep reservation parties together would first make sure that there is no space where they can all sit together prior to splitting them up
* For safety, buffer seats in rows aboveand below the reservation as well as three on each side were blocked 
* used global variables so row, column, seat buffers, and row buffers could be changed very easye


**Assumptions**

1. The buffer row of one is the same seat in rows above and below the current reseravtions
2. The File reservation numbers were in order
3. If the reservation could nto be filled due to lack of seats or was a a number below 1, then Printed to the console was the reservation details along with the response of why it was nto booked, this reservation was also not added to the output file


**Goals**

1.Customer satisfaction - first priority as a group would rather sit together than seperately in better viewing seats
2.Customer safety 


**Optimizations**
1. Incorportating a better view with sitting together as the first priority
2. Run time, for checking for consecutive seats for the party was a slow process in my algorithm this could be speed up by using a bit more space and putting pointers to the first open seat and how many consecutive seats are available
3. Fitting in as many customers as possible to maximize profit may include splitting up more parties or taking reservations out of order
4. If there is unsold seats could reconfiguring so that better viewing seat ( the middle) and larger safety buffers could be applied
5. Adding a cost scale for tickets - matinee, child, adult, senior discount or for the seats ( like an airline does) 
6. Better testing of full scale project
7. Possibly one of my assumptions was incorrect





