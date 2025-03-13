
# Swarm Simulator

This project is a simulation of a swarm of robots navigating in an environment, developed for the "Advanced Programming" course at UNICAM (Università degli Studi di Camerino).

## Project Overview

The simulation involves a swarm of robots executing a program consisting of commands and moving within an environment. 
This environment includes areas, such as circles and rectangles, which represent specific conditions. 
The robots execute various commands that enable them to:

* Follow a condition.

* Signal a condition.

* Stop signaling a condition.
   
* Change a condition signaled (if they pass over specific areas).
   
* Follow signals from other robots within a defined range.
   
* Move at a specified speed and direction.

* Repeat a series of commands forever.

* Repeat a series of commands until a specific condition is met.

* Continue to move for the specified amount of time.
   
* Stop.

##  Usage

To run a simulation:

1.  In the terminal, navigate to the "swarm-simulator" directory and execute `./gradlew build` to build the application.
   
2.  Run the application with `./gradlew run`. This will execute a simulation using default data (dt = 1.0, simulation time = 20.0, number of robots = 5) with the following program:
   
    ```
    MOVE 10.0 10.0 30.0
    CONTINUE 5
    REPEAT 3
    CONTINUE 3
    DONE
    ```
   
    and the following environment:
   
    ```
    a CIRCLE -20.0 40.0 10.0
    f RECTANGLE 10.0 10.0 20.0 15.0
    ```
   
3.  To use a different program or environment, modify the `robot_program.txt` and `robot_environment.txt` files located in the `src/main/resources` directory.
    To change the number of robots, dt, or simulation time, use the command:
   
    ```bash
    ./gradlew run --args='arg1 arg2 arg3'
    ```
   
    where `arg1` is dt, `arg2` is simulation time, and `arg3` is the number of robots.

## Technologies Used

* Java 21
   
* IntelliJ IDEA
   
* Gradle

## Author

* Antonio Addesa
