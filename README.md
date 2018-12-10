# COCOMA-RoboRescue

## Overview
In this project, we are testing reinforcement learning, QLearning in fact, in the RoboCupRescue Simulation.


We will assume no communication between the agents & no centralized learning.

We will concentrate on clearing the blockades in the environment. So only three simulators will be mendatory : 

* The clear simulator
* The collapse simulator
* The traffic simulator


## What is implemented
We implemented PoliceForce agents, members of the QLPD (QLearning Police Departement). These agents use a decentralized QLearning to choose between two simple actions : 
* Clear a blockade
* Make a random move

Basically, the agents should clear the maximum amount of blockades globally. Since the number of agents on the same blockade doesn't improve the performance, we want them to spread around different blockades.

The states contains two components : 
* Whether there's a reachable blockade for the agent.
* The number of other agents that 

We give an individual reward to the agents as follows:

* A null reward for random moves.
* A reward equal to -1 when the agent tries to clear a blockade meanwhile there's none.
* A reward equal to 1 / the number of the agents clearing the same blockade (1 if the agent is alone & decreases as the number of agents on the same blockade increases)


## How to test it
You should clone or download this project & place it in the *modules* folder of *rcrs server*. You sould not forget to add the source root & the build tasks of this module to the *build.gradle* file of the project.

Then you should be able to use the *qlpd.SampleQPoliceForce* to implements the police force agents.


### Contact

* [Tarek CHOUAKI](mailto:tkchouaki@icloud.com)

* [Pierre DUBAILLAY](mailto:pierredubaillay@outlook.fr)

* [Tristan DE BLAUWE](mailto:tdb.work@outlook.fr)
