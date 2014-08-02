public class Party
{
	/** The party's name */
	private final String name;
	/** The party's social policy value */
	private final int socPol;
	/** The party's economic policy value */
	private final int econPol;
	/** The party's competence value */
	private final int competence;

	/**
	 * Constructs a new party with the given parameters.
	 * @param name the desired name for this party.
	 * @param socPol the desired social policy value for this party.
	 * @param econPol the desired economic policy value for this party.
	 * @param competence the desired competence value for this party.
	 */
	public Party(String name, int socPol, int econPol, int competence)
	{
		//ensure everything is in range or throw an exception
		if(name == null)
			throw new IllegalArgumentException("Null names are not allowed");
		if(socPol   < 0 || socPol    > 100)
			throw new IllegalArgumentException("The social policy is out of range");
		if(econPol  < 0 || econPol  > 100)
			throw new IllegalArgumentException("The economic policy is out of range");
		if(competence < 0 || competence > 100)
			throw new IllegalArgumentException("The competence is out of range");

		this.name = name;
		this.socPol = socPol;
		this.econPol = econPol;
		this.competence = competence;
	}

	/**
	 * Returns the party's name.
	 * @return the party's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the party's social policy value.
	 * @return the party's social policy value.
	 */
	public int getSocialPolicy()
	{
		return socPol;
	}

	/**
	 * Returns the party's economic policy value.
	 * @return the party's economic policy value.
	 */
	public int getEconomicPolicy()
	{
		return econPol;
	}

	/**
	 * Returns the party's competence value.
	 * @return the party's competence value.
	 */
	public int getCompetence()
	{
		return competence;
	}
}