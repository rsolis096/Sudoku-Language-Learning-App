
**ITERATION 2 - SUDOKU Language Learning App**

**Submitted By:** 

Luciano Tyszkow-Castellani, lmt12 (Repo Manager)

Ricardo Solis, rds11 (Team Member)

Sijia Wang, swa287 (Scrum Master)

Asmita Srivastava, asa313 (Product Owner)

**IMPLEMENTATIONS FOR ITERATION 2:**

**IMPLEMENTATION #1**

**User story:** As a novice user of the software, I wish to become familiar with the game logic using informative display and guided access to the game, to better understand the app demands in order to play a round of sudoku puzzles. 

**Tdd:**  When the app is opened, a menu page is displayed to users with choices in accordance to the game guided by descriptive headings. For example, users may choose a difficulty for the game. By default, the easy level of difficulty is selected hence the novice user may use the first game to become familiar with the game logic. When they start the game, the grid is displayed to them with few empty entries so that they can choose to fill the word out of the displayed options. Additionally, the introductory game will be traditional sudoku values of digits from 1-9, for easy understandability of game and software mechanics. 

***Future scope:*** Further in the app development process, the options page on the home screen would include tutorials and instructions as to the game requirements and how to play the puzzle as well. 



**IMPLEMENTATION #2**

**User story :** As a beginner language learner, I want the app to verify if my answer is correct instantly as I input them so that I don’t have wrong answers on the board that will affect my performance throughout the game later. 

**Tdd:** On starting the game, users may use the mouse to scroll through the sudoku grid, as they’re attempting to fill in each entry of the box, the box will turn Green to highlight correct answers and Red to indicate any mistakes. The user then can reattempt the wrong answers at any point in the game, to result in an all green sudoku board. 

***Scope for future*:** Users may drag their cursor to the red incorrect tiles to reveal hints to help them solve the puzzle. Once the hint is displayed, the user may re-attempt to solve for the entry to yield a green correct answer. 

`  `**=>**   



**IMPLEMENTATION #3**

**User Story:** As an expert language learner, I want the option to type in my answer using my keyboard instead of picking from the drop-down options to challenge my memory and spelling accuracy for the words learned.

**Tdd:** When a user chooses to start a game, a menu will pop up, one of the options will be whether or not the user wants to type their answers manually. When this mode is selected, the game played will look different as opposed to the standard mode, where the user will be displayed an on-screen keyboard to type their responses.




**IMPLEMENTATION #4**

**User Story:** As a novice user of the software, I wish for the app menu to be self-explanatory along with instructions for the game logic and requirements to play a game. 

**Tdd:** When the app is opened for the first time, the user is displayed with three buttons conveying the game settings, START will begin a Sudoku puzzle game with a 9x9 grid, and TUTORIAL leads to a page containing game instructions and basic rules to begin solving an easy level puzzle. These instructions will help the user get the understanding of the board logic and how to solve the puzzle using words from a bank/ user input. 



**IMPLEMENTATION #5**

**User Story:** As a language learner, I wish to broaden my language vocabulary by having an option to play the Sudoku puzzle with different categories of words for instance, greetings, food and drink words, etc., to increase more natural translation of words from my language to the second language I’m learning. 

**Tdd:** When a user chooses to start a game, a choice of selection is offered to the user to choose from 5 different categories of words namely, Numbers (this is the traditional choice for the Sudoku game at beginner level), Family, Greetings, Food&Drinks and Directions. All the categories are word banks containings English-Spanish word pairs for each of the three difficulty levels - easy, medium and hard. If no choice is made for the word bank selection, it will default to a bank of numbers 1 to 9 (similar to normal sudoku). The difficulty mode and word bank options combined will decide the possible words to appear in the game.



***Scope for future:*** Language teachers have an option to make their custom word bank using user input, to incorporate a glossary of words as an exercise for students to solve the Sudoku puzzle using. This will help design new puzzles to help with each new lesson taught.

**ITERATION 3: User Stories and TDD Examples**

**User story:** To make the vocabulary learning easy and accessible, I wish to have access to the sudoku puzzle app on my different devices like tablet, so that the words will be conveniently displayed in larger, easier to read fonts.  

**Tdd:** The app should be made to fit to screen for different devices. The layout will be relative with respect to the device the app is opened on. For example, on a tablet the menu page will be enlarged with buttons and texts with bigger and enlarged fonts. The sudoku board, as well, will fit the screen relatively. 



**User story:** As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font than standard mode. 

**Tdd:** When the user turns the screen, the game settings adapt according to the landscape/portrait settings on the device the game is being played on. This could be an automatic enhancement as soon as the user switches phone layout, the sudoku app settings adapt to the external device settings. All resulting in an enlarged, fit-to-screen and clear sudoku board for better game view and play.



**User story:** As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3).

**Tdd:** This opportunity will be presented in the Game Menu options where the user can select the dimensions of the Sudoku board for generating the puzzle. Traditionally, the 9x9 grid option will be selected as default to generate a basic sudoku puzzle, but additionally, user may select 4x4 grid or a 6x6 rectangle grid, for easy play. On choosing the options and starting the menu, the sudoku puzzle will be generated in accordance with user choices.  



**User story:** As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4).

**Tdd:** This opportunity will be presented in the Game Menu options where the user can select the dimensions of the Sudoku board for generating the puzzle. Traditionally, the 9x9 grid option will be selected as default to generate a basic sudoku puzzle, but additionally, user may select a 12x12 tablet grid, for a challenging version of play. On choosing the options and starting the menu, the sudoku puzzle will be generated in accordance with user choices.  The 12x12 is a rectangular board which has a difficult mode, due to more boxes to fill. 

**User story:** As an expert language learner, I want a timed mode so that I can improve my translation speed and compete against myself.

**Tdd:** While the user is playing the game, there will be a timer in the corner that begins when the game begins. This timer will have digits for hours, minutes, and seconds for flexibility. After the game is solved, the player will be displayed with a timer under the results screen. 


**User story:** As a language teacher, I want the ability to add my own words to the puzzles, so that I can make new puzzles to help with each new lesson I teach.

**Tdd:** Accessible through the menu, the word bank is customizable. The user can create new categories and add new word pairs to categories (both preset and user created). Any user added categories or words can also be deleted if needed, but the given categories and words cannot be deleted. To add a category, only a name needs to be provided. To add a word pair, two fields need to be filled with the English word and the translation, and a difficulty level needs to be selected for the word. 
