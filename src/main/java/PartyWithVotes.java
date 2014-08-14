
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import java.util.Collection;

public class PartyWithVotes implements Comparable<PartyWithVotes>
{
	private final ImmutableMultiset<Vote> votes;
	private final Party party;
	private final int minimumPreference;

	public PartyWithVotes(final Party party, Multiset<Vote> votes)
	{
		this.party = party;

		//sort out the votes for this party from all the rest
		Predicate<Vote> isForThisParty = new Predicate<Vote>()
		{
			@Override
			public boolean apply(Vote v)
			{
				return v.getParty() == party;
			}
		};
		Collection<Vote> votesForThisParty = Collections2.filter(votes, isForThisParty);

		//create an immutable multiset with those votes
		this.votes = ImmutableMultiset.<Vote>builder().addAll(votesForThisParty).build();

		//check for the lowest preference vote avaiable
		int minPref = 0;
		for(Vote v : this.votes.elementSet())
			minPref = v.getChoiceNumber() > minPref ? v.getChoiceNumber() : minPref;

		minimumPreference = minPref;
	}

	public int getVotes(int preference)
	{
		return votes.count(new Vote(party, preference));
	}

	public Party getParty()
	{
		return party;
	}

	@Override
	public String toString()
	{
		String buffer = "Party " + party.getName() + ":\n";

		for(int pref=1; pref<=minimumPreference; pref++)
		{
			buffer += "Choice " + pref + ": " + getVotes(pref) + "\n";
		}

		return buffer;
	}

	@Override
	public int compareTo(PartyWithVotes o)
	{
		int voteDifference = 0;

		for(int pref=1; pref<=minimumPreference && pref<=o.minimumPreference && voteDifference==0; pref++)
		{
			voteDifference = getVotes(pref) - o.getVotes(pref);
		}

		return voteDifference;
	}

}
