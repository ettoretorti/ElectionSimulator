
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Ettore Torti
 */
public class PartyWithVotePreferences implements Comparable<PartyWithVotePreferences>
{
	//keys are the preference, values are the votes
	private Map<Integer, Integer> preferenceMap;
	private Party party;
	
	public PartyWithVotePreferences(Party p, Map<Integer, Integer> prefMap)
	{
		party = p;
		preferenceMap = prefMap;
	}
	
	public PartyWithVotePreferences(Party p)
	{
		party = p;
		preferenceMap = new HashMap<>();
	}
	
	public int getVotes(int preference)
	{
		if(!preferenceMap.containsKey(preference))
			return 0;
		else
			return preferenceMap.get(preference);
	}

	@Override
	public int compareTo(PartyWithVotePreferences o)
	{
		int voteDifference = 0;
		int pref = 1;
		
		while(preferenceMap.get(pref) != null && o.preferenceMap.get(pref) != null && voteDifference == 0)
		{
			voteDifference = preferenceMap.get(pref) - o.preferenceMap.get(pref);
			pref++;
		}
		
		return voteDifference;
	}
	
	@Override
	public String toString()
	{
		String buffer = "Party " + party.getName() + ":\n";
		
		int pref=1;
		while(preferenceMap.containsKey(pref))
		{
			buffer += "Choice " + pref + ": " + preferenceMap.get(pref) + "\n";
			pref++;
		}
		
		return buffer;
	}
	
	public Party getParty()
	{
		return party;
	}
}
