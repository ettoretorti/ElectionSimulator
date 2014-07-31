
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Election
{
	/** A variable to keep track of whether the election has been run. */
	private boolean isComplete;
	/** The set of parties participating in this election. */
	private Set<Party> parties;
	/** The set of voters participating in this election. */
	private Set<Voter> voters;
	/**  */
	private Map<Party, Map<Integer, Integer>> voteMap;
	private List<PartyWithVotePreferences> electionResults;

	/** 
	 * Constructs a new election.
	 */
	public Election()
	{
		parties = new HashSet<>();
		voters  = new HashSet<>();
		voteMap = new HashMap<>();
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
		boolean added;
		
		//if the party isn't already in the party set
		if(added = parties.add(p))
			voteMap.put(p, new HashMap<Integer, Integer>());
		
		return added;
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
		boolean added;
		
		//if any party is added then add them to the vote map
		if(added = parties.addAll(pCol))
			for(Party p : pCol)
				if(!voteMap.containsKey(p))
					voteMap.put(p, new HashMap<Integer, Integer>());
							
		return added;
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
	public void runElection()
	{
		//reset the votes
		for(Party p : voteMap.keySet())
		{
			voteMap.put(p, new HashMap<Integer, Integer>());
			for(int i=1; i<=parties.size(); i++)
				voteMap.get(p).put(i, 0);
		}
			
		
		for(Voter v : voters)
		{
			//take the voter's preferences
			List<Party> partyPrefs = v.getPartyPreferences(parties);
			
			//add those preferences to each party's preference vote map
			for(int i=0; i<partyPrefs.size(); i++)
			{
				Party  p = partyPrefs.get(i);
				
				Map<Integer, Integer> prefMap = voteMap.get(p);
				
				prefMap.put(i+1, prefMap.get(i+1) + 1);
			}
		}

		List<PartyWithVotePreferences> partyResults = new ArrayList<>(parties.size());

		for(Party p : voteMap.keySet())
		{
			partyResults.add( new PartyWithVotePreferences(p,voteMap.get(p)));
		}

		Collections.sort(partyResults, Collections.reverseOrder());

		electionResults = partyResults;
		
		isComplete = true;
	}
	
	/**
	 * Returns the results of this election.
	 * @return A list of parties with vote preferences sorted in descending order.
	 * @throws IncompleteElectionException if the election is not complete.
	 */
	public List<PartyWithVotePreferences> getElectionResults() throws IncompleteElectionException
	{
		if(!isComplete) throw new IncompleteElectionException();
		
		return Collections.unmodifiableList(electionResults);
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
	public String results()
	{
		if(!isComplete())
			return "This election isn't complete.";
		
		String buffer = "Generic Election Results\n"
				      + "========================\n";
		for(PartyWithVotePreferences p : getElectionResults())
			buffer += p + "\n";
		
		buffer += voterStats();
		
		Party winningParty = getElectionResults().get(0).getParty();
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
}