# Interactive-Simulated-Annealing
---
### Instructions
- This repo requires Java 1.8 to run
- The entry point into this program is the Dispatch.java class, which is responsible for parsing files from the input folder, calling the algorithm, and deleting a file from the input folder after it has been solved successfully.
- The Dispatch.java class can run the 2 provided solvers. By modifying lines 32 to 34, you can choose which solver is used.
- One solver (ChocoSolve) is the open source [Choco Solver](http://www.choco-solver.org) library. It is a general-purpose search algorithm that serves as a benchmark. The other one (AnnealingSolver) is an implementation of [Simulated Annealing](https://en.wikipedia.org/wiki/Simulated_annealing).
- These are the keys for controlling the Simulated Annealing algorithm:
    - 't' to show the current temperature
    - 'q' to increase temperature
    - 'a' to decrease temperature
    - 'w' to perform a randomization of all variables involved in a violated constraint
    - 's' to attempt to fix one violated constraint
    - 'b' to return to the best state encountered so far
    - 'r' to restart the problem by randomizing the current state
    - 'p' to print the partial solution of a problem to a file
    
### The Problem
Suppose there exists an ordering of ages for a set of people. You are given a list of constraints of the form [a, b, c] which means that the age of person c is not in between the ages of people a and b. Suppose there exists at least one ordering of people that satisfies all of the constraints. Your goal is to find an ordering of people that satisfies every single constraint. This problem is canonically known as ["betweenness"](wikipedia.org/wiki/Betweenness) and has significance in bioinformatics and modeling theories of probability, causality, and time.

### What is Simulated Annealing?
Simulated Annealing(SA) is a type of local search, meaning that it tries to improve on its current state by considering its neighboring states one at a time. In this problem, the desirability of a given state is given by how many constraints it satisfies. 

The algorithm is encouraged to move into inferior neighbor states in the early stages of execution, and less likely to accept them as time passes: we want to be adventurous in the beginning, and more conservative as we gain a better understanding of the search space. The "adventureousness" of the algorithm is denoted by the temperature. Given a state, the Simulated Annealing algorithm will **always** move into it if it is better, and will **sometimes** move into it if it is inferior, depending on the value of the temperature parameter. Higher temperatures increase the probability that the algorithm will choose to move into an inferior state.

In this repo, we calculate the probability that we will move into a neighboring state using the following function:

**e^((neighbor_goodness - current_goodness)/T)**

Notice how the value of this function is always greater than 1 when (neighbor_goodness > current_goodness), and how the value of this function is always less than 1 when (neighbor_goodness < current_goodness). This is reflects the fact that a Simulated Annealing algorithm will **always** move into a better neighboring state, and will **sometimes** move into an into a worse neighboring state, depending on the value of Temperature.

Finally we decay our temperature by multiplying it by some number less than 1 at the end of each iteration. We chose 0.99.
