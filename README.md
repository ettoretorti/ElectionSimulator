ElectionSimulator
=================

This project simulates various types of electoral systems. We take into account
simple models of voter preferences and behaviour, together with party
characteristics. Each voter is thus mapped to a party (unless the voter decides
to abstain from voting). The various electoral systems then map percentage votes
to percentage seats in Parliament. 

Aim
---

To explore the workings of various electoral systems, and to establish which
systems are the most representative (i.e. which systems distort percentage votes
least when transforming them into percentage seats).

Outline
-------

The following sections introduce the key elements of the election simulator. 
More thorough explanations are available in the specifications.


Voters
------
Voters choose which party to vote according to their preferences and the 
characteristics of available parties. Voters prefer parties which are closer to
them on the left-right social and economic preferences scales. Most voters also
prefer parties which are perceived to be more competent. Finally, voters can
care about other characteristics, such as whether a party supports their region
or whether the party has a strong view on environmental issues. 

Parties
-------
Parties are the entities voters elect.

A party possesses the following characteristics:

 * **Social Policy**: an integer from 0-100 inclusive which reflects the
     party's standing on social issues. A value of 0 indicates completely left
     wing policy and a value of 100 indicates completely right wing policy.

 * **Economic Policy**: an integer from 0-100 inclusive which reflects the
     party's standing on economic issues. A value of 0 indicates completely left
     wing policy and a value of 100 indicates completely right wing policy.

 * **Perceived Competence**: an integer from 0-100 inclusive which reflects how
     competent the voters believe this party to be. A higher value indicates
     more competence.


Coalitions
----------
If no party manages to establish a governing majority at the end of an election
the parties will attempt to form coalitions to gain the majority. Coalitions
will be formed under the following assumptions:

 * A party will prefer to form a coalition with the smallest amount of other
   parties required to give it a governing majority.

 * A party will prefer to form a coalition in which is has the largest
   proportion of the total representation.

 * A party will prefer to form coalitions with parties that are near it in terms
   of social and economic policy.

 * A party will prefer to form coalitions with parties with a high perceived
   competence.


Elections
---------
Elections are the process through which the voters select parties to represent
them. This project aims to deliver different sorts of elections of varying
complexity.

The main types of electoral systems to be supported are:

 * **Proportional representation**: all parties obtain a percentage of seats
     which is directly proportional to the percentage of votes received. No
     party is over-represented or under-represented. The only exception to this
     rule occurs if there is a lower boundary to the percentage of votes
     necessary to enter parliament. 

 * **Majoritarian**: the party which gets the most votes is over-represented, 
     while all other parties are under-represented.

 * **N rounds**: consists of N rounds, with the lowest polling parties being
     eliminated at each round and the rest entering the next until only one
     remains. The largest party/parties are over-represented, while the smaller
     ones are under-represented.
     
 * **First past the post**: the country is divided into districts. The party
     which gets the most votes in a district gets all the seats reserved for
     that district. 