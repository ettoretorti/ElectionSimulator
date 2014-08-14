
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;


public class TwoRoundElection extends Election
{
	private List<PartyWithDoubleValue> seatResults;
	private Election firstRound;
	private Election secondRound;

	@Override
	public void run()
	{
		//run the first round normally
		firstRound = new Election();
		firstRound.addVoters(getVoters());
		firstRound.addParties(getParties());
		firstRound.run();

		ImmutableList<PartyWithVotes> results = firstRound.results();

		//create a new election for the 2nd round
		secondRound = new Election();
		secondRound.addVoters(getVoters());

		//add the two top performing parties and run the second round
		for(int i=0; i<2; i++)
			secondRound.addParty(results.get(i).getParty());
		secondRound.run();
		//determine the second round's winner
		Party winner = secondRound.results().get(0).getParty();

		//determine the percentage of seats for each party
		List<PartyWithDoubleValue> seatPercentages = new ArrayList<>(results.size());

		//give the winning party its seats
		double winnerSeats;
		if(percentageVotesForParty(winner, 1) > 51)
			winnerSeats = firstRound.percentageVotesForParty(winner, 1);
		else
			winnerSeats = 51;

		seatPercentages.add(new PartyWithDoubleValue(winner, winnerSeats));

		//calculate remaining seats and votes
		double percentSeatsLeft = 100 - winnerSeats;
		double votesRemaining = firstRound.noVoters() - firstRound.votesForParty(winner, 1);

		//calculate the seats for the remaining parties
		for(PartyWithVotes p : results)
		{
			if(p.getParty() == winner)
				continue;

			double percentageSeats = percentSeatsLeft * p.getVotes(1)/votesRemaining;
			seatPercentages.add(new PartyWithDoubleValue(p.getParty(), percentageSeats));
		}

		seatResults = seatPercentages;
		isComplete = true;
	}

	/**
	 * Returns the results of the first round of this election.
	 * @return A list of parties with votes sorted in descending order.
	 * @throws IncompleteElectionException if the election is not complete.
	 */
	@Override
	public ImmutableList<PartyWithVotes> results() throws IncompleteElectionException
	{
		return firstRound.results();
	}

	/**
	 * Returns the results of a chosen round of this election.
	 * @param round the round of the election that results will be returned for.
	 * @return A list of parties with votes sorted in descending order.
	 * @throws IncompleteElectionException if the election is not complete.
	 * @throws IllegalArgumentException if the round provided does not exist.
	 */
	public ImmutableList<PartyWithVotes> electionResults(int round) throws IncompleteElectionException, IllegalArgumentException
	{
		if(round < 1 || round > 2)
			throw new IllegalArgumentException("Invalid round: " + round);

		if(round == 1)
			return firstRound.results();

		return secondRound.results();
	}

	@Override
	public String toString()
	{
		if(!isComplete())
			return "This election isn't complete.";

		String buffer = "Two Round Election Results\n"
				      + "==========================\n";

		buffer += "     Party     | Seats | 1st Rnd | 2nd Rnd\n"
				+ "---------------+-------+---------+--------\n";

		for(PartyWithDoubleValue p : seatResults)
		{
			buffer += String.format("%-15s| %-5.2f |  %-5.2f  |  %-5.2f%n", p.getParty().getName(), p.getValue(),percentageVotesForParty(p.getParty(), 1), secondRound.percentageVotesForParty(p.getParty(), 1));
		}
		buffer += "\n";
		buffer += voterStats();

		Party winningParty = results().get(0).getParty();
		buffer += "Average distance from winning party: " + avgDistanceFromParty(winningParty) + "\n";

		return buffer;
	}

	@Override
	public ImmutableList<PartyWithDoubleValue> seatResults()
	{
		return ImmutableList.<PartyWithDoubleValue>builder().addAll(seatResults).build();
	}
}
