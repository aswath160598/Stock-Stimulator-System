Our JAR file can be run using the command "java -jar assignment6.jar text" to run the text based interface and "java -jar assignment6.jar gui" to run the GUI. The "stock_master_file" csv must be present in the same directory as the jar file.

On running the JAR file with the GUI, the user will be shown a menu (main menu) which will have buttons for various operations the user can perform. The operations are -
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

On clicking on a button on the main menu the user will be presented with a screen which will accept various input such as portfolio name, ticker, etc, from the user. The user can hover over an input field to see more information about what input is expected. The user will also be notified if any invalid inputs are entered. Using the "Back" button the user can go back to the main menu.

The "Exit" button will save all existing portfolios and close the program.

To plot the bar graph we use an opensource library called StdDraw. It can be found at https://introcs.cs.princeton.edu/java/stdlib/StdDraw.java

On running the JAR file with the text interface, the user will be shown a menu (main menu) which will have 2 different options listed below.
Please select an option:
1.) Operate on an inflexible portfolio
2.) Operate on a flexible portfolio

If the user enters 2, he will be prompted with the flexible portfolio operations which are shown below:
Please Enter select option
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


The user will enter the number depending on his desirability. The option 8 would be used to plot a portfolio as a graph. And 9 to show all portfolios. He would enter 0 inorder to go the previous menu.
Buy, Sell, Cost Basis and Graph Plotting are specific to the flexible portfolio only.


If the user enters 1, he will be prompted with the inflexible portfolio operations which are shown below:
Please Enter your option
1.) Add a new Portfolio
2.) View a Portfolio
3.) Show the total value of a Portfolio
4.) Save a Portfolio
5.) Load a Portfolio
6.) Exit the program
0.) Go to previous menu

The user can enter 1 and press the "Enter" key to "Add a new Portfolio". The user will then be asked to enter a portfolio name. The portfolio name should not contain spaces (it can be something like "portfolio_one"), after typing the portfolio name the user can press enter. The user will then be asked to enter the stock ticker symbol and the number of shares for that stock. The stock ticker symbol will be validated against the list of supported stocks (please see the list below) and the number of shares has to be a positive integer value. The user will then be asked, "Do you want to continue adding Stocks?". The user can respond with "n" if they do not wish to add another stock to the portfolio or "y" if they need to add another stock to the current portfolio. On entering "y", the user will be asked to enter another stock ticker symbol and the number of shares. The user can enter any number of stocks by entering "y" repeatedly. On entering "n" the user will be shown the main menu with the 6 options. The user can add another portfolio by entering 1 and following the process described above.

The created portfolios can be viewed by selecting option 2 on the main menu and entering the portfolio name.

To query the value of a portfolio the user can enter 3 for "Show the total value of a Portfolio". The user will be asked for the portfolio name and the date on which the value is to be computed. The date should be entered in the yyyy-mm-dd format. The program will compute the value of the portfolio on the given date and show the result to the user in the format "Ticker [unit_Stock_Price, no_Of_Shares, stock_Value]" followed by the total value of the portfolio on the given date. Our program can be used to query the portfolio value from 2014-01-01 to 2022-10-31.

The user can save a portfolio by selecting option 4 on the main menu and entering the portfolio name. The portfolio will be saved in XML format in the same directory as the JAR file and the file name will be set to the portfolio name.

The user can load a saved portfolio by selecting 5 on the main menu and entering the portfolio name. The portfolio should be saved in valid XML format in the same directory as the JAR file and the file name should the same as the portfolio name.

The user can exit the program by selecting 6 on the main menu. All created portfolios will be saved in XML format to the same directory as the JAR and the file name will be set to the portfolio name.

Our program supports all stocks that are present in the Alpha Vantage API that we have integrated.
These values are returned with respect to the specific ticker.
