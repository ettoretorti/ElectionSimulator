public class Party
{
	private final String name;
	private final int socPol;
	private final int econPol;
	private final int competence;

	public Party(String name, int socPol, int econPol, int competence)
	{
		this.name = name;
		this.socPol = socPol;
		this.econPol = econPol;
		this.competence = competence;
	}

	public String getName()
	{
		return name;
	}

	public int getSocialPolicy()
	{
		return socPol;
	}

	public int getEconomicPolicy()
	{
		return econPol;
	}

	public int getCompetence()
	{
		return competence;
	}
}