public class PartyWithIntValue implements Comparable<PartyWithIntValue>
{
	private final Party party;
	private final int value;

	public PartyWithIntValue(Party p, int val)
	{
		party = p;
		value = val;
	}

	@Override
	public int compareTo(PartyWithIntValue other)
	{
		return value - other.value;
	}

	public Party getParty()
	{
		return party;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return "Party " + party.getName() + ": " + value;
	}
}