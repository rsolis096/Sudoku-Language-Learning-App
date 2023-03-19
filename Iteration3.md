ITERATION 3 - SUDOKU Language Learning App

Submitted By: 
Luciano Tyszkow-Castellani, lmt12 (Scrum Master)
Ricardo Solis, rds11 (Product Owner)
Sijia Wang, swa287 (Team Member)
Asmita Srivastava, asa313 (Repo Manager)

IMPLEMENTATIONS FOR ITERATION 3:

Implementation #1 :
User story: As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3).
Tdd: This opportunity is presented in the Game Menu options where the user can select the dimensions of the Sudoku board for generating the puzzle. Traditionally, the 9x9 grid option will be selected as default to generate a basic sudoku puzzle, but additionally, users may select 4x4 grid or a 6x6 rectangle grid, for easy play. On choosing the options and starting the menu, the sudoku puzzle will be generated in accordance with user choices and more words were added to the word bank to generate the word bank appropriately.

![Screenshot_2023-03-18_at_12.52.59_PM](/uploads/4662ac0ec13350edb9d0fc1249945ba7/Screenshot_2023-03-18_at_12.52.59_PM.png)
![Screenshot_2023-03-18_at_12.53.16_PM](/uploads/8f120744a679d2ea368f764a5ce6c4bc/Screenshot_2023-03-18_at_12.53.16_PM.png)![Screenshot_2023-03-18_at_7.20.59_PM](/uploads/c7007a20f5864a46f45e9c5cbcbb222e/Screenshot_2023-03-18_at_7.20.59_PM.png)
      

Implementation #2 :
User story: As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4).
Tdd: This opportunity is presented in the Game Menu options where the user can select the dimensions of the Sudoku board for generating the puzzle. Traditionally, the 9x9 grid option will be selected as default to generate a basic sudoku puzzle, but additionally, users may select a 12x12 tablet grid, for a challenging version of play. On choosing the options and starting the menu, the sudoku puzzle will be generated in accordance with user choices.  The 12x12 is a rectangular board which has a difficult mode, due to more boxes to fill and more words are added with the 12 cells to deepen language comprehension. 

On a tablet.
  

 On a phone.

Implementation #3 : 
User story: To make the vocabulary learning easy and accessible, I wish to have access to the sudoku puzzle app on my different devices like tablet, so that the words will be conveniently displayed in larger, easier to read fonts.
Tdd: The app is made and configured to fit to screen for different devices. The layout will be relative with respect to the device the app is opened on. For example, on a tablet the menu page will be enlarged with buttons and texts with bigger and enlarged fonts. The sudoku board, as well, will fit the screen relatively. While on a phone view, users may need to scroll from side to side in order to view the whole puzzle at once, the tablet mode shall make it easy to play the game with the whole board visible. 

  ⇒   
On a phone,                              On a tablet.



Implementation #4 : 
User story: As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font than standard mode. 
Tdd: When the user turns the screen, the game settings adapt according to the landscape/portrait settings on the device the game is being played on, this is done by using the built-in android feature of rotating the screen. A rotator icon is shown on the bottom right of the screen, enabling the user to flip the screen layout. This could be an automatic enhancement as soon as the user switches phone layout, the sudoku app settings adapt to the external device settings. All resulting in an enlarged, fit-to-screen and clear sudoku board for better game view and play. When the user rotates the screen, there is an option to turn the game layout resulting in 2 different views, namely Vertical and Horizontal. 
                                        
Portrait shift to Horizontal




Implementation #5 : 
As an expert language learner, I want a timed mode so that I can improve my translation speed and compete against myself. I should be able to see the time during and after I’ve completed solving my puzzle to analyze my performance in the game and while learning the language.
Tdd: While the user is playing the game, there will be a timer in the corner that begins when the game begins. This timer will have digits for hours, minutes, and seconds for flexibility. After the game is solved and the user chooses to finish the game page, the player will be redirected to the results screen which contains the time taken to solve the game displayed beneath a “Congratulations” message to celebrate the user and mark the end of play. 

    





ITERATION 4: User Stories and TDD Examples

User story: As a language teacher, I want the ability to custom add my own words to the puzzles, so that I can make new puzzles to help with each new lesson I teach.
Tdd: Accessible through the options menu, the word bank is customizable. The user can create new categories and add new word pairs to categories (both preset and user created) prompted in a text file. Any user added categories or words can also be deleted if needed, but the given categories and words cannot be deleted. To add a category, only a name needs to be provided. To add a word pair, two fields need to be filled with the English word and the translation. After adding new words, the user will have the option to play from the custom word bank by selecting “Custom” from the word categories. This will generate a custom sudoku puzzle with the teacher-fed words. 
For instance, if a teacher were to feed in a collection of  English-Spanish word pairs relating to color, the system would take in the list of word pairs and make a customized sudoku board out of entered words.
Such a board is presented                      with the following list of words entered by the teacher                         
              


User story: As a student, to practice my spoken understanding of the words learned in the game, I want a listening comprehension mode. In this mode, numbers will appear in the prefilled cells. When I hover over the prefilled cells, the corresponding word in the language will be read out to me. I can further test my listening comprehension by selecting the correct English translation of the word from the menu.
Tdd: When the user goes through the routine choices to generate a sudoku puzzle for language learning, a board is generated. Further enhancement to the game will be added where if the user clicks on a cell containing a word from the numbers already pre-filled in the Sudoku board, the cell shall have the option to read out/ play the pronunciation of the word cell selected by the user. After hearing the word by the game app, the user may choose to play the game based on their comprehension of the word listened to match the corresponding word in English from a list of options. This will aid in listening-comprehension of languages learned by the user. 
Clicking the voice/volume icon will prompt the word in the cell to be read out to the user for comprehension.




User story: As a learner I want there to be visibility themes for the game mode play, to make the game appear aesthetically pleasing in gradient themes, light and dark mode to encourage me while playing the puzzle by having colorful settings to keep me motivated.
Tdd: In the options menu of the game, there will be possibilities for the user to select the game appearance by choosing one out of light/dark/gradient colorful board settings, after selecting the choice the game will be displayed in the corresponding colorful layout selected by the user. By default, there will be the light mode enabled for ease of playing and no distractions. 

     
 
Apart from this, many more enhancements to the existing game logic and UI settings shall be reviewed and improved upon. 
