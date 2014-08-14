
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Election
{
	/** A variable to keep track of whether the election has been run. */
	protected boolean isComplete;
	/** The set of parties participating in this election. */
	private Set<Party> parties;
	/** The set of voters participating in this election. */
	private Set<Voter> voters;

	private ImmutableList<PartyWithVotes> electionResults;

	private Multiset<Vote> allVotes;

	/**
	 * Constructs a new election.
	 */
	public Election()
	{
		parties = new HashSet<>();
		voters  = new HashSet<>();
		allVotes = HashMultiset.create();
	}

	/**
	 * Adds a voter to the set of voters.
	 * @param v the voter to be added.
	 * @return true if the set of voters was changed by this addition, false otherwise.
	 */
	public boolean addVoter(Voter v)
	{
		return voters.add(v);
	}

	/**
	 * Returns an immutable set containing the voters currently participating in this election.
	 * @return an immutable set containing the voters current participating in this election.
	 */
	public ImmutableSet<Voter> getVoters()
	{
		return ImmutableSet.<Voter>builder().addAll(voters).build();
	}

	/**
	 * Returns an immutable set containing the parties currently participating in this election.
	 * @return an immutable set containing the parties currently participating in this election.
	 */
	public ImmutableSet<Party> getParties()
	{
		return ImmutableSet.<Party>builder().addAll(parties).build();
	}

	/**
	 * Adds the voters in an array to the set of voters.
	 * @param vArr the array of voters to be added.
	 * @return true if the set of voters was changed by this addition, false otherwise.
	 */
	public boolean addVoters(Voter[] vArr)
	{
		return voters.addAll(Arrays.asList(vArr));
	}

	/**
	 * Adds the voters in a collection to the set of voters.
	 * @param vCol the collection of voters to be added.
	 * @return true if the set of voters was changed by this addition, false otherwise.
	 */
	public boolean addVoters(Collection<Voter> vCol)
	{
		return voters.addAll(vCol);
	}

	/**
	 * Adds a party to the set of parties.
	 * @param p the party to be added.
	 * @return true if the set of parties was changed by this addition, false otherwise.
	 */
	public boolean addParty(Party p)
	{
		return parties.add(p);
	}

	/**
	 * Adds the parties in an array to the set of parties.
	 * @param pArr the array of parties to be added.
	 * @return true if the set of parties was changed by this addition, false otherwise.
	 */
	public boolean addParties(Party[] pArr)
	{
		return addParties(Arrays.asList(pArr));
	}

	/**
	 * Adds the parties in a collection to the set of parties.
	 * @param pCol the collection of parties to be added.
	 * @return true if the set of parties was changed by this addition, false otherwise.
	 */
	public boolean addParties(Collection<Party> pCol)
	{
		return parties.addAll(pCol);
	}

	/**
	 * Returns the average social preference of the voters in the voter set.
	 * @return the average social preference of the voters in the voter set.
	 */
	public double avgSocialPreference()
	{
		int sum = 0;
		for(Voter v : voters)
			sum += v.getSocialPreference();

		return sum / (double) voters.size();
	}

	/**
	 * Returns the average Economic preference of the voters in the voter set.
	 * @return the average Economic preference of the voters in the voter set.
	 */
	public double avgEconomicPreference()
	{
		int sum = 0;
		for(Voter v : voters)
			sum += v.getEconomicPreference();

		return sum / (double) voters.size();
	}

	/**
	 * Returns the average euclidian preference distance of the voters in the voter set to the provided party.
	 * @param p the party from which the average distance is calculated.
	 * @return the average euclidian preference distance of the voters in the voter set to the provided party.
	 */
	public double avgDistanceFromParty(Party p)
	{
		double sum = 0;
		for(Voter v : voters)
			sum += v.getTotalDistance(p);

		return sum / voters.size();
	}

	/**
	 * Returns the average social policy of the parties in the party set.
	 * @return the average social policy of the parties in the party set.
	 */
	public double avgSocialPolicy()
	{
		int sum = 0;
		for(Party p : parties)
			sum += p.getSocialPolicy();

		return sum / (double) parties.size();
	}

	/**
	 * Returns the average economic policy of the parties in the party set.
	 * @return the average economic policy of the parties in the party set.
	 */
	public double avgEconomicPolicy()
	{
		int sum = 0;
		for(Party p : parties)
			sum += p.getEconomicPolicy();

		return sum / (double) parties.size();
	}

	/**
	 * Runs the election, stores the results, and sets the complete flag to true.
	 */
	public void run()
	{
		//reset the votes
		allVotes.clear();

		//add each voter's votes
		for(Voter v : voters)
		{
			ImmutableSet<Vote> votes = v.getPartyPreferences(parties);
			allVotes.addAll(votes);
		}

		//create a list to hold the results of all parties
		List<PartyWithVotes> partyResults = new ArrayList<>(parties.size());

		//add all the party results to the list
		for(Party p : parties)
			partyResults.add(new PartyWithVotes(p, allVotes));

		//sort it into ascending order
		Collections.sort(partyResults, Collections.reverseOrder());

		//return an immutable copy of it
		electionResults = ImmutableList.
				<PartyWithVotes>builder().addAll(partyResults)
				                                   .build();

		isComplete = true;
	}

	/**
	 * Returns the results of this election.
	 * @return A list of parties with votes sorted in descending order.
	 * @throws IncompleteElectionException if the election is not complete.
	 */
	public ImmutableList<PartyWithVotes> results() throws IncompleteElectionException
	{
		if(!isComplete) throw new IncompleteElectionException();

		return electionResults;
	}

	/**
	 * Returns the number of votes for this party with a certain choice.
	 * @param p the party whose votes to count.
	 * @param choice count only votes representing this choice.
	 * @return the number of votes for this party as a first choice.
	 * @throws IncompleteElectionException if the election hasn't been run.
	 */
	public int votesForParty(Party p, int choice) throws IncompleteElectionException
	{
		for(PartyWithVotes party : results())
			if(party.getParty() == p) return party.getVotes(choice);

		return 0;
	}

	/**
	 * Returns the percentage of votes for this party with a certain choice out of
	 * all the votes for that choice.
	 * @param p the party whose votes to count.
	 * @param choice count only votes representing this choice.
	 * @return a number from 0-100 representing the percentage of votes.
	 * @throws IncompleteElectionException if the election hasn't been run.
	 */
	public double percentageVotesForParty(Party p, int choice) throws IncompleteElectionException
	{
		for(PartyWithVotes party : results())
			if(party.getParty() == p) return 100.0*party.getVotes(choice)/noVoters();

		return 0;
	}

	public ImmutableList<PartyWithDoubleValue> seatResults() throws IncompleteElectionException
	{
		ImmutableList.Builder<PartyWithDoubleValue> listBuilder = ImmutableList.builder();

		for(PartyWithVotes p : results())
			listBuilder.add(new PartyWithDoubleValue(p.getParty(), 100.0*p.getVotes(1)/noVoters()));

		return listBuilder.build();
	}

	/**
	 * Returns whether the election is complete.
	 * @return true if the election is complete, false otherwise.
	 */
	public boolean isComplete()
	{
		return isComplete;
	}

	/**
	 * Returns a string with the results of this election.
	 * @return a string with the results of this election.
	 */
	@Override
	public String toString()
	{
		if(!isComplete())
			return "This election isn't complete.";

		String buffer = "Generic Election Results\n"
				      + "========================\n";
		for(PartyWithVotes p : results())
			buffer += p + "\n";

		buffer += voterStats();

		Party winningParty = results().get(0).getParty();
		buffer += "Average distance from winning party: " + avgDistanceFromParty(winningParty) + "\n";

		return buffer;
	}

	/**
	 * Returns a string with statistics about the voters in this election.
	 * @return a string with statistics about the voters in this election.
	 */
	public String voterStats()
	{
		String buffer = "Voter statistics:\n";

		buffer += "Average Economic Pref: " + avgEconomicPreference() + "\n";
		buffer += "Average Social Pref: " + avgSocialPreference() + "\n";

		return buffer;
	}

	/**
	 * Returns the number of voters participating in the election.
	 * @return the number of voters participating in the election.
	 */
	public int noVoters()
	{
		return voters.size();
	}

	/**
	 * Returns the number of parties participating in the election.
	 * @return the number of parties participating in the election.
	 */
	public int noParties()
	{
		return parties.size();
	}
}