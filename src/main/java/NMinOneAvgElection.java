
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class NMinOneAvgElection extends Election
{
	Election[] electionRounds;
	private List<PartyWithDoubleValue> seatResults;

	@Override
	public void run()
	{
		electionRounds = new Election[noParties()-1];

		Set<Party> partiesLeft = new HashSet<>(getParties());

		int curRound = 0;

		while(curRound < noParties()-1)
		{
			Election thisRound = new Election();
			electionRounds[curRound] = thisRound;

			thisRound.addParties(partiesLeft);
			thisRound.addVoters(getVoters());

			thisRound.run();

			//find the lowest scoring party and remove it
			List<PartyWithVotes> results = thisRound.results();
			partiesLeft.remove(results.get(results.size()-1).getParty());

			//increment the current round and repeat
			curRound++;
		}

		seatResults = new ArrayList<>(noParties());
		//calculate the seats for each party
		for(Party p : getParties())
		{
			int sumOfVotes = 0;
			for(Election e : electionRounds)
				sumOfVotes += e.votesForParty(p, 1);

			double percentageVotes = 100.0 * sumOfVotes / electionRounds.length / noVoters();

			seatResults.add(new PartyWithDoubleValue(p, percentageVotes));
		}

		//sort the seat results into descending order
		Collections.sort(seatResults, Collections.reverseOrder());

		isComplete = true;
	}

	/**
	 * Returns the results of the last round of this election.
	 * @return A list of parties with votes sorted in descending order.
	 * @throws IncompleteElectionException if the election is not complete.
	 */
	@Override
	public ImmutableList<PartyWithVotes> results() throws IncompleteElectionException
	{
		if(!isComplete)
			throw new IncompleteElectionException();

		return electionRounds[electionRounds.length-1].results();
	}

	/**
	 * Returns the results of a chosen round of this election.
	 * @param round the round of the election that results will be returned for.
	 * @return A list of parties with votes sorted in descending order.
	 * @throws IncompleteElectionException if the election is not complete.
	 * @throws IllegalArgumentException if the round provided does not exist.
	 */
	public ImmutableList<PartyWithVotes> results(int round) throws IncompleteElectionException, IllegalArgumentException
	{
		if(!isComplete)
			throw new IncompleteElectionException();

		if(round < 1 || round > electionRounds.length)
			throw new IllegalArgumentException("Invalid round: " + round);

		return electionRounds[round-1].results();
	}

	@Override
	public ImmutableList<PartyWithDoubleValue> seatResults()
	{
		if(!isComplete)
			throw new IncompleteElectionException();

		return ImmutableList.<PartyWithDoubleValue>builder().addAll(seatResults).build();
	}

	@Override
	public String toString()
	{
		if(!isComplete())
			return "This election isn't complete.";

		String buffer = "N-minus-one Round Election Results\n"
				      + "==================================\n";

		for(int i=0; i<electionRounds.length; i++)
		{
			buffer += "Round " + (i+1) + "\n";

			buffer += "     Party     | Votes\n"
					+ "---------------+------\n";

			for(PartyWithVotes p : electionRounds[i].results())
				buffer += String.format("%-15s| %5.2f%n", p.getParty().getName(), electionRounds[i].percentageVotesForParty(p.getParty(), 1));

			buffer += "\n";
		}

		//print the seat results
		buffer += "Seat Results\n";
		buffer += "     Party     | Seats\n"
				+ "---------------+------\n";

		for(PartyWithDoubleValue p : seatResults)
			buffer += String.format("%-15s| %5.2f%n", p.getParty().getName(), p.getValue());
		buffer += "\n";

		buffer += voterStats();

		Party winningParty = results().get(0).getParty();
		buffer += "Average distance from winning party: " + avgDistanceFromParty(winningParty) + "\n";

		return buffer;
	}
}
