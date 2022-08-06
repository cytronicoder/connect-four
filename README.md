### 📂 This repository is now archived as a finished project.

# Solving Connect Four

## Premise

Connect Four is a two-player game where the players take turns dropping colored discs into a 7x6 grid. The first player to get four of their discs in a row (either vertically, horizontally, or diagonally) is the winner. The game ends when the board is full or the players run out of discs, at which point the game is a draw [(1)](https://en.wikipedia.org/wiki/Connect_Four#Gameplay). It is also a strongly solved perfect information strategy game: first player has a winning strategy whatever his opponent plays [(2)](https://en.wikipedia.org/wiki/Connect_Four#Mathematical_solution).

## Project details

This projects aims to implement a Connect Four solver in Java. Then, it will be tested against some common test cases and eventually be used to play against a real opponent.

I have previously done this project using a simple Minimax algorithm, but I have decided to explore a more complex algorithm in order to create a more robust solver.

## Success criteria

The solver should be able to solve a given board in a reasonable amount of time: with every move, the solver should be able to determine whether it is winning or losing.

- If it is winning, it should return a winning move for itself.
- If it is losing, it should block the opponent from winning if there is a winning move available for the opponent.

## Acknowledgements

I would like to thank [@PascalPons](https://github.com/PascalPons) for his tutorial on [the perfect Connect Four Solver](https://blog.gamesolver.org/): it was a great help to understand how the algorithm should be implemented. Additionally, many thanks to [Nick Drohan](https://www.linkedin.com/in/nick-drohan-b8a75014/) and [Rishab Nayak](https://github.com/rishabnayak) for guiding me through this and providing assistance - you guys are amazing.
