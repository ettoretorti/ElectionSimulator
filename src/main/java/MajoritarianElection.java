
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MajoritarianElection extends Election
{
	private List<PartyWithDoubleValue> seatResults;

	@Override
	public void run()
	{
		super.run();

		ImmutableList<PartyWithVotes> results = results();

		int totalVotes = noVoters();
		int remainingVotes = totalVotes;

		double fivePercent = totalVotes * 0.05;
		double thirtyFivePercent = totalVotes * 0.35;

		List<PartyWithVotes> finalResults = new ArrayList<>(results);

		//remove tiny parties and adjust remaining votes
		Iterator<PartyWithVotes> itr = finalResults.iterator();
		while(itr.hasNext())
		{
			PartyWithVotes p = itr.next();
			if(p.getVotes(1) < fivePercent)
			{
				itr.remove();
				remainingVotes -= p.getVotes(1);
			}
		}

		//start assigning seat percentages
		List<PartyWithDoubleValue> seatPercentages = new ArrayList<>(finalResults.size());
		double percentageRemaining = 100;
		boolean winnerTaken = false;

		for(PartyWithVotes p : finalResults)
		{
			PartyWithDoubleValue partyWithSeats;

			if(!winnerTaken && p.getVotes(1) >= thirtyFivePercent)
			{
				double percentageEarned;
				if(p.getVotes(1) <= 0.51*totalVotes)
					percentageEarned = 51;
				else
					percentageEarned = 100*(double)p.getVotes(1)/totalVotes;

				partyWithSeats = new PartyWithDoubleValue(p.getParty(), percentageEarned);
				remainingVotes -= p.getVotes(1);
				percentageRemaining -= percentageEarned;
				winnerTaken = true;
			}
			else
			{
				double percentageEarned = percentageRemaining * p.getVotes(1)/remainingVotes;
				partyWithSeats = new PartyWithDoubleValue(p.getParty(), percentageEarned);
			}

			seatPercentages.add(partyWithSeats);
		}

		seatResults = seatPercentages;
	}

	@Override
	public String toString()
	{
		if(!isComplete())
			return "This election isn't complete.";

		String buffer = "Majoritarian Election Results\n"
				      + "=============================\n";

		buffer += "     Party     | Seats | Votes\n"
				+ "---------------+-------+------\n";

		for(PartyWithDoubleValue p : seatResults)
			buffer += String.format("%-15s| %-5.2f | %-5.2f%n", p.getParty().getName(), p.getValue(),percentageVotesForParty(p.getParty(), 1));

		buffer += "\n";
		buffer += voterStats();

		Party winningParty = results().get(0).getParty();
		buffer += "Average distance from winning party: " + avgDistanceFromParty(winningParty) + "\n";

		return buffer;
	}
}
