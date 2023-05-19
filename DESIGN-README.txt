Our model, controller and view are well separated from each other and only one of the model classes which is the User class interacts with the 
controller which in turn has only one class to interact with the View module.
We have used the Build pattern for implementing our Stock class to avoid errors during creating objects.

Based on feedback from assigment 5:

We have added commission fee as a user entered input for all buy and sell transactions. This change has been implemented for the text based interface as well.

Our program supports both GUI and text based interfaces. The GUI of our program is handled by the SwingController. Our program also has a Features interface and a FeaturesImpl class that implements the Features interface. The controller has a composition "has-a" relationship with Features, and it passes an instance of the Features object to the view. Features acts as an adapter for our controller. Our GUI view and controller use the same models that were user by the text based interface in the previous assignment.
We have added interfaces and classes to represent the Dollar Cost Average Plans (both continuing and single fixed amount transaction) that may be created by the user.
We have added utility classes for plotting the bar chart which will be displayed by the view.
We also have utility classes for file handling operations.
Our GUI has individual exclusive screens for each user functionality. It is very user-friendly

Model
The lowermost object in our model is the Stock object and the flexible stock object in which each Stock is represented with its corresponding number of shares. The flexible stock class extends the stock class and both the classes have their own interfaces.
Coming to the next level higher on the Hierarchy we see the Portfolio which represents a list of Stocks. We have defined a new flexible Portfolio class that extends the old Portfolio class. The Portfolio can be flexible or inflexible 
Another level higher we see the User object in which multiple portfolios are created and can be saved in XML format.
Similarly, all the operations would be performed by the User class in coordination with other model classes. In order to operate the methods of a flexible portfolio class, there will be a flexible user who in turn will be extending the user class.
Initially we had created classes to get a stock value from a csv file and also to check if a ticker symbol is valid or not.
Now that we have integrated the Alpha Vantage API in our project such that all the stock values are taken from the API and the total portfolio value can be calculated with the get value function.
Our flexible portfolio has a cost basis function that includes all the purchases made in that portfolio till that date. It also includes the commission fee paid by the user for each transaction until that date.

Controller
Our controller is the one that interacts between the model and view for both flexible as well as inflexible portfolios. Our controller is involved in taking inputs from the user and passes it to the view and model as and when required.
The FlexiController  is the main controller that decides which portfolio to be created and delegates the user to the desired controller. If the user wants to operate an inflexible portfolio he will bne delegated to the odl controller.
It starts with displaying the view main message and takes the user prompt if he wants to go ahead with a flexible or an inflexible portfolio.
Depending on the values user entered, it goes ahead to the desired model inorder to perform the desired functionality. 

View 
Our view is solely responsible for printing the messages on the screen and does no other operations. 
The controller directly interacts with the view inorder to display the messages as desired. The flexible controller would interact with the flexible view inorder to display the messages related to the flexible view.

All our three modules : model, controller and view are independent of each other. Thereby our user interface can be replaced without affecting the core functionality.
Our model can handle fractional shares but doesn't allow the user to create portfolios with fractional shares.

We also have a utility package that consists of the following classes:
File Handling: This is the class that deals with file operations such as loading a file and saving a file. 
Draw a graph: We have a class that deals with drawing a graph in our utilities package. This class helps in plotting a graph inorder to check the portoflio's performance over a period of time. 

