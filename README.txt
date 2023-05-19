We have implemented a GUI for the flexible Portfolios in which a user would be able to create a portfolio and buy and sell stocks as desired on any date and evaluate the total value of the portfolio. Our portfolio supports commission fees which is accepted as a user input and would be charged every time the user executes a buy or a sell transaction. We have also incorporated the bar graph feature in the GUI which denotes the Portfolio's performance over time.

We have implemented features for Dollar Cost Averaging (both continuous investment plans and single fixed amount investment) and also for plotting a graphical bar chart of a portfolio's performance.
We have implemented Dollar Cost Averaging in such a way such that if it has to be added to an existing portfolio then it will add directly from start date to end date. If the portfolio doesn't exist even then it will create a new portfolio and assign dollar cost averaging to that portfolio between the given dates.

Our investment strategy has been implemented such that on adding it to the portfolio, It will invest the desired proportion of stocks on the specified date.

The graph has been designed depending on the constraints provided. It has been designed as follows:

1.) If the interval between start and end date is lesser than or equal to 30 days then each timestamp would represent an individual day.

2.) If the interval between start and end date is between 30 days to 60 days then each timestamp would represent an alternate day.

3.) If the interval between start and end date is between 60 days to 150 days then each timestamp would represent once in 5 days.

4.) If the interval between start and end date is between 150 days to 900 days then each timestamp would represent once in a month.

5.) If the interval between start and end date is between 900 days to 2700 days then each timestamp would represent once in a quarter.

6.) If the interval between start and end date is greater than 2700 days then each timestamp would represent once in a year.

Our portfolio validates the transactions that are entered by the user as well as those transactions that were uploaded as a file in our portfolio object. The validation is such that a sell will happen only when sufficient number of shares are bought for that stock before the sale date otherwise it will throw an error if it doesn't contain sufficient number of shares.

We have integrated the Alpha Vantage API in our project such that all the stock values are taken from the API and the total portfolio value can be calculated with the get value function.
We have implemented the get composition function that returns the current composition on a specific date.

We can calculate the cost basis of our portfolio which is the total cost of the portfolio on a specific day. This include all the buy transactions and the commission fees.

Using our GUI the user can perform the following operations -
1.  "Buy a Stock"
2.  "Sell a Stock"
3.  "View a Portfolio"
4.  "Save a Portfolio"
5.  "Load a Portfolio"
6.  "View Cost Basis"
7.  "View Portfolio Value"
8.  "Plot Graph"
9.  "Dollar Cost Averaging"
10. "Invest a fixed amount"
11. "Exit"

The list of flexible portfolio (text based interface) functions that we offer are:

1.)  Buy a stock

2.)  Sell a stock

3.)  View a Portfolio

4.)  Save a Portfolio

5.)  Load a Portfolio

6.)  Determine the cost basis

7.)  Determine the value

8.)  Plot portfolio performance

9.)  Show all portfolios

0.)  Go to previous menu

The user can enter the following options inorder to Implement the functionality that is desired.
There is a flexible view that displays messages related to the flexible portfolio on the output screen. 
Similarly we have a flexible controller that interacts between the parts of model that deals with flexible portfolio and the flexible view.

The project still supports inflexible portfolio which we implemented as part of assignment 4. The functionalities of inflexible portfolio are given below:

Our Project revolves around Portfolio Operations for a Stock Market: Portfolio is nothing but a list of Company Stocks. Some of the features we have implemented as part of our project are:

1.)Identifying Portfolio by unique names.

2.) Adding Stocks to a Portfolio

3.) Saving a Portfolio

4.) Loading an existing Portfolio

5.) Displaying a Portfolio

6.) Getting the value of a Portfolio on a particular Date.

--> We are storing the stocks as csv files locally and extracting data inorder to compute the total Portfolio values. --> Our program fetches the stock prices for the given date from stored CSV files. --> Our model computes values of stocks at any specific date whether it is a weekend or not. --> Our model avoids duplicate stocks while computing and adds to existing if already present.

Our model consists of 6 Interfaces and 6 classes which are: 
1.) Stocks: This Interface represents each individual Stock in which a company's ticker and the number of shares in each stock are stored along with the cost price.

2.)Portfolio: This Interface represents the compositions of a Portfolio and also gets the number of stocks of the Portfolio. Each portfolio has a name and the list of Stocks

3.) User: This represents the User Interface which performs most of the operations such as: Adding a new Portfolio to the user. Getting the Portfolio object with the portfolio name. Getting the value of a Portfolio on a particular date. Saving a Portfolio after it is created. Loading a saved Portfolio and then use it for displaying. Our program also check if the portfolio name entered already exists and shows an appropriate message to the user.

4.) Ticker Validator: We have this Interface to validate if a ticker is valid or not.

5.) Get Stock Value CSV : This functions takes ticker and date as an input and returns there stock value on that particular date.

Our Model even computes stock values on weekends when the value would not be available. We do this by calculating the previous valid date for the stock value to exist.

Our View consists of multiple functions that displays these messages to the user and implements the following messages:

1.) it displays the welcome message.

2.) It displays the main menu to the user that prompts him with the list of possible inputs the user can enter to perform a functionality.

3.) It takes care of every message that is prompted to the user.

4.) It prompts the user to enter date in the format yyyy-mm-dd.
