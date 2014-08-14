public class PartyWithDoubleValue implements Comparable<PartyWithDoubleValue>
{
	private final Party party;
	private final double value;

	public PartyWithDoubleValue(Party p, double val)
	{
		party = p;
		value = val;
	}

	@Override
	public int compareTo(PartyWithDoubleValue other)
	{
		double comparison = value - other.value;

		if(comparison < 0) return -1;
		if(comparison > 0) return 1;

		return 0;
	}

	public Party getParty()
	{
		return party;
	}

	public double getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return "Party " + party.getName() + ": " + value;
	}
}