
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent a Proportional Representation election system
 * @author Ettore
 */
public class PRElection extends Election
{
	/**
	 * Constructs a new Proportional Representation election.
	 */
	public PRElection()
	{
		super();
	}

	@Override
	public String results()
	{
		if(!isComplete())
			return "This election isn't complete.";
		
		
		//get the election results
		List<PartyWithVotePreferences> results = getElectionResults();
		
		//create a list of parties and the first preference votes they got
		List<PartyWithIntValue> firstVotes = new ArrayList<>(results.size());
		//add all the parties and votes to the list
		for(PartyWithVotePreferences p : results)
			firstVotes.add(new PartyWithIntValue(p.getParty(), p.getVotes(1)));
		//sort it by votes in descending order
		Collections.sort(firstVotes, Collections.reverseOrder());
		
		
		//create a header
		String buffer = "Proportional Representation Election Results\n"
				      + "============================================\n";
		
		//print the info for each party
		for(PartyWithIntValue p : firstVotes)
			buffer += p + "\n";
		
		//print some voter statistics
		buffer += "\n" + voterStats();
		Party winningParty = firstVotes.get(0).getParty();
		buffer += "Average distance from winning party: " + avgDistanceFromParty(winningParty) + "\n";
		
		return buffer;
	}
}