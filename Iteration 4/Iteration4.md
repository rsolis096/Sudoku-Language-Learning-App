
**ITERATION 4 - SUDOKU App for Language Learning** 

**Submitted By:** 

Luciano Tyszkow-Castellani, lmt12 (Scrum Master)

Ricardo Solis, rds11 (Product Owner)

Sijia Wang, swa287 (Repo Manager)

Asmita Srivastava, asa313 (Team Member)


**FINAL REQUIREMENTS: User Stories and TDD Examples**


**IMPLEMENTATION #1**

**User story:** As a language teacher, I want the ability to custom add my own words to the puzzles, so that I can make new puzzles to help with each new lesson I teach.

**Tdd:** Accessible through the options menu, the word bank is customizable. The user can create new categories and add new word pairs to categories (both preset and user created) when prompted by entering English-Spanish word pairs in text boxes. Any user defined categories or words can also be deleted if needed, but the given pre-set categories and words cannot be deleted. To add a word pair, two fields need to be filled with the English word and the corresponding Spanish translation. After adding new words, the user will have the option to play from the custom word bank by selecting “Custom Words” from the word categories. This will generate a custom sudoku puzzle with the user-fed words. Based on the no.of word pairs entered by the users, different grid sizes for the sudoku puzzle shall be made available.   

![](/Iteration%204/Iteration%204%20Images/1.png) ![](/Iteration%204/Iteration%204%20Images/2.png) ![](/Iteration%204/Iteration%204%20Images/3.png) 

For instance, if a teacher were to enter in a collection of English-Spanish word pairs relating to color, the system would take in the list of word pairs and make a customized sudoku board out of entered words.

Collection of English-Spanish word pairs based on color created by teacher in class module : 

**Red - roja**

**Pink - rosada**

**Gray - gris** 

**White - blanco** 

**Blue - azul**

**Green - verde**

![](/Iteration%204/Iteration%204%20Images/4.png)   ![](/Iteration%204/Iteration%204%20Images/5.png) ![](/Iteration%204/Iteration%204%20Images/6.png) 

Clicking the cross icon next to the word pairs will delete the word pair from the custom bank. Furthermore, by selecting ‘clear word bank’ the user can delete all words in the custom word bank. Playable grid sizes based on the no. of word pairs entered by user (using above example, word pairs entered by teacher are 6) :

![](/Iteration%204/Iteration%204%20Images/7.png) 

**IMPLEMENTATION #2**

**User story:** As a student, to practice my spoken understanding of the words learned in the game, I want a listening comprehension mode. In this mode, numbers will appear in the prefilled cells. When I click on the prefilled cells, the corresponding word in the language will be read out to me. I can further test my listening comprehension by selecting the correct English translation of the word from the menu.

**Tdd:** When the users go through the routine choices to generate a sudoku puzzle for language learning, they will be presented with an additional option of audio mode. This is accessible on the game settings page along with other puzzle settings, by selecting the toggle button for audio mode ON will generate the board with the desired settings with an added functionality for listening comprehension by the users. In this mode, when the user clicks on a pre-filled cell in the Sudoku puzzle, the cell will automatically play the pronunciation of the word cell in the corresponding language selected by the user. After hearing the word by the game app, the user may choose to play the game based on their comprehension of the word listened to match and solve the puzzle. This will aid in listening-comprehension of languages learned by the user. Clicking the voice/volume icon will prompt the word in the cell to be read out to the user for comprehension.

![](/Iteration%204/Iteration%204%20Images/8.png) 

**IMPLEMENTATION #3**

**User story:** As a learner I want there to be visibility themes for the game mode play, to make the game appear aesthetically pleasing in gradient themes, light and dark mode to accommodate my mood and surroundings while playing. 

**Tdd:** In the options menu of the game, there will be an option for the user to select the game appearance for opting manual dark mode for the app appearance, after selecting the choice the game will be displayed in the corresponding colorful layout selected by the user. By default, there will be the light mode enabled for ease of playing and no distractions.  

![](/Iteration%204/Iteration%204%20Images/9.png) ![](/Iteration%204/Iteration%204%20Images/10.png) ![](/Iteration%204/Iteration%204%20Images/12.png) 

Moreover, the grid UI was improved upon highlighting all cells affected by the cell the user is attempting to fill in the Sudoku puzzle. This feature will help users eliminate any conflicts between cell entries to effectively achieve the correct Sudoku puzzle result (Remember, adhering to Sudoku logic a word can only appear once in a row, a column, as well as a box within the grid.)

![](/Iteration%204/Iteration%204%20Images/11.png) 

**IMPLEMENTATION #4**

**User story:** As a beginner user, I wish to have access to the game manual while I’m playing the Sudoku puzzle to help me gain understanding about the game logic as well as teach me about different modes to play the game.

**Tdd:** When the user starts the game, the grid will be displayed to the user along with a timer and the word options needed to solve the Sudoku puzzle. In this added feature, next to the timer the user will view supplementary aid including a help button which redirects the user to the tutorial page, as well as a ‘clear cell’ option to undo one’s errors. The ‘help’ button will provide the user the ability to catch up with the game logic while they are in play mode. 

![](/Iteration%204/Iteration%204%20Images/13.png)   ![](/Iteration%204/Iteration%204%20Images/14.png)   ![](/Iteration%204/Iteration%204%20Images/15.png) 

![](/Iteration%204/Iteration%204%20Images/16.png)   
