import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Voter
{
	//The voter's social and economic preferences. Both integers from 0-100.
	//Lower values represent left-leaning preferences and vice versa.
	private final int socPref;
	private final int econPref;
	
	//Ratios for how much importance is placed between the two preferences and
	//a party's competence. This results in a linear combination.
	private final int prefRatio;
	private final int compRatio;
	
	/**
	 * Constructs a voter with the given preferences and ratios.
	 * @param socPref the voter's social preference.
	 * @param econPref the voter's economic preference.
	 * @param prefRatio the voter's preference ratio.
	 * @param compRatio the voter's competence ratio.
	 * @throws IllegalArgumentException if any of the preferences or inputs are out of range.
	 */
	public Voter(int socPref, int econPref, int prefRatio, int compRatio) throws IllegalArgumentException
	{
		//ensure everything is in range or throw an exception
		if(socPref   < 0 || socPref   > 100)
			throw new IllegalArgumentException("The social preference is out of range");
		if(econPref  < 0 || econPref  > 100)
			throw new IllegalArgumentException("The economic preference is out of range");
		if(prefRatio < 0 || prefRatio > 100)
			throw new IllegalArgumentException("The preference ratio is out of range");
		if(compRatio < 0 || compRatio > 100)
			throw new IllegalArgumentException("The competence ratio is out of range");
	
		//initialize the values
		this.socPref = socPref;
		this.econPref = econPref;
		this.prefRatio = prefRatio;
		this.compRatio = compRatio;
	}
	
	/**
	 * Constructs a voter with the given preferences. The voter will have
	 * preference and competence ratios of 50.
	 * @param socPref the voter's social preference.
	 * @param econPref the voter's economic preference.
	 */
	public Voter(int socPref, int econPref)
	{
		this(socPref, econPref, 50, 50);
	}

	/**
	 * Returns the voter's social preference.
	 * @return the voter's social preference.
	 */
	public int getSocialPreference()
	{
		return socPref;
	}
	
	/**
	 * Returns the voter's economic preference.
	 * @return the voter's economic preference.
	 */
	public int getEconomicPreference()
	{
		return econPref;
	}

	/**
	 * Returns the voter's preference ratio.
	 * @return the voter's preference ratio.
	 */
	public int getPreferenceRatio()
	{
		return prefRatio;
	}

	/**
	 * Returns the voter's competence ratio.
	 * @return the voter's competence ratio.
	 */
	public int getCompetenceRatio()
	{
		return compRatio;
	}

	/**
	 * Returns the social difference between the voter and a given party.
	 * @param p the party from which the difference is to be calculated.
	 * @return the social difference between the voter and a given party
	 */
	public int getSocialDifference(Party p)
	{
		return Math.abs(p.getSocialPolicy() - socPref);
	}

	/**
	 * Returns the economic difference between the voter and a given party.
	 * @param p the party from which the difference is to be calculated.
	 * @return the economic difference between the voter and a given party.
	 */
	public int getEconomicDifference(Party p)
	{
		return Math.abs(p.getEconomicPolicy() - econPref);
	}

	/**
	 * Returns the euclidian difference between the voter's preferences and a 
	 * given party's policies.
	 * @param p the party from which the difference is to be calculated.
	 * @return the euclidian difference between the voter's preferences and a 
	 * given party's policies.
	 */
	public double getTotalDistance(Party p)
	{
		int socDis = getSocialDifference(p);
		int econDis = getEconomicDifference(p); 
		return Math.sqrt(socDis*socDis + econDis*econDis);
	}
	
	/**
	 * Takes a set of parties and returns a list of the same parties ordered by
	 * descending preference.
	 * @param parties the set of parties to consider.
	 * @return a list of the same parties ordered by
	 * descending preference.
	 */
	public List<Party> getPartyPreferences(Set<Party> parties)
	{
		List<Party> partyList = new ArrayList<>(parties);
		
		//shuffle the list of parties
		Collections.shuffle(partyList);
		
		//Create a list of parties with utility values
		List<PartyWithIntValue> valueList = new ArrayList<>(parties.size());
		
		//find the utility value of each party and add it to the list
		for(Party p : partyList)
			valueList.add(new PartyWithIntValue(p, utilityValue(p)));

		//sort the parties by value, highest first
		Collections.sort(valueList, Collections.reverseOrder());

		//create a new list with the parties in order of preference
		List<Party> voteList = new ArrayList<>(parties.size());
		
		//insert the parties into it
		for(PartyWithIntValue p : valueList)
			voteList.add(p.getParty());

		return Collections.unmodifiableList(voteList);

	}
	
	/**
	 * Determines the utility value a given party would have for this voter. 
	 * @param p the party.
	 * @return the utility value the given party would have for this voter.
	 */
	public int utilityValue(Party p)
	{
		int policyValue = (getEconomicDifference(p) * prefRatio
		                 + getSocialDifference(p) * (100 - prefRatio)) / 100;

		int finalValue = (p.getCompetence() * compRatio - policyValue * (100 - compRatio)) / 100;

		return finalValue;
	}
}