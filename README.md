# Tik Tak Toe
## _Play And Enjoy_
A game in which two players alternately put Xs and Os in compartments of a figure formed by two vertical lines crossing two horizontal lines and each tries to get a row of three Xs or three Os before the opponent does.


## Features
- Implement with TDD approach
- Using ServiceLocator (Koin)
- Implement with Compose

## Algorithm

To Find the Result of the game, we need to callculate the Board values.

Create two array with lentgh of 8 to store the win's premutation of X and O.Every element in above arrays should be zero at first. When one the users move, we find __corresponding__ index of array and increase it. At last, after each move we search on array and found 3 on it. The first user's array that reaches 3 is winner or if there is no empty cell.

##### Corresponding:
Assume we have an Array of 9 zero ->
[0,0,0, 0,0,0, 0,0]

- The first three zero is reserved for rows.
- The second three zero is reserved for columns.
- The 7th element is for diameter.
- The 8th element is for inverse diameter.

we increase  corresponding position for choose element position by 1.
for example if user chooses 5 the array becomes : [0,1,0, 0,1,0, 1,1]


## How to install

There are two for installing the Application
 ```
  ./gradlew assembleDebug
 ```
And github Active

