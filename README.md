ElectionSimulator
=================

A project to simulate various types of elections from individual voter level. It works by providing giving each voter a list of available parties having it return a list with the same parties ordered by preference. At this point all the votes are run through a certain electoral system and results for that election are displayed.

A brief outline of the interacing parts and their inner workings can be found below.


Voters
------
Voters are individuals that participate in elections. They take their preferences and compare them to parties' policies to build their list of preferences.

A voter possesses the following characteristics:

 * **Social Preference**: an integer from 0-100 inclusive which reflects the voter's opinion on social issues. A value of 0 would indicate completely left wing policy and a value of 100 would indicate completely right wing policy.

 * **Economic Preference**: an integer from 0-100 inclusive which reflects the voter's opinion on economic issues. A value of 0 would indicate completely left wing policy and a value of 100 would indicate completely right wing policy.

 * **Preference Ratio**: an integer from 0-100 inclusive used to determine how much weight the voter gives to his social and economic preferences. A value of 0 would indicate the voter only cares about social policy, while a value of 100 would indicate the voter only cares about economic policy.

 * **Competence Ratio**: an integer from 0-100 inclusive used to determine how much weight the voter places on parties' perceived competences compared to their social and economic policies. A value of 0 would indicate the voter does not care about competence at all, while a value of 100 would indicate the voter only cares about competence.


Parties
-------
Parties are the entities voters elect.

A party possesses the following characteristics:

  * **Social Policy**: an integer from 0-100 inclusive which reflects the party's standing on social issues. A value of 0 indicates completely left wing policy and a value of 100 indicates completely right wing policy.

  * **Economic Policy**: an integer from 0-100 inclusive which reflects the party's standing on economic issues. A value of 0 indicates completely left wing policy and a value of 100 indicates completely right wing policy.

  * **Perceived Competence**: an integer from 0-100 inclusive which reflects how competent the voters believe this party to be. A higher value indicates more competence.


Coalitions
----------
If no party manages to establish a governing majority at the end of an election the parties will attempt to form coalitions to gain the majority. Coalitions will be formed under the following assumptions:

  * A party will prefer to form a coalition with the smallest amount of other parties required to give it a governing majority.

  * A party will prefer to form a coalition in which is has the largest proportion of the total representation.

  * A party will prefer to form coalitions with parties that are near it in terms of social and economic policy.

  * A party will prefer to form coalitions with parties with a high perceived competence.


Elections
---------
Elections are the process through which the voters select parties to represent them. This project aims to deliver different sorts of elections of varying simplicity.

The list of different electoral systems to be supported is as follows:

  * **Proportional Representation**: an electoral system in which parties get a representation in government proportional to the amount of votes received.

  * **Majoritarian**: an electoral system in which the party with the majority gets complete representation while others get none.

  * **N rounds**: an electoral system that consists of N rounds, with the lowest polling parties being eliminated at each round and the rest entering the next until only one remains.


 These ideas can be merged together to come up with many sorts of unique electoral systems.