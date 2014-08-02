
import java.util.Objects;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ettore
 */
public class Vote
{
	/** The party this vote is for. */
	private final Party party;
	/** The voter's choice number for this party. */
	private final int choiceNumber;

	/**
	 * Constructs a new vote for a party with the given choice number.
	 * @param party the party the vote is for.
	 * @param choiceNumber the choice number for the party.
	 */
	public Vote(Party party, int choiceNumber)
	{
		this.party = party;
		this.choiceNumber = choiceNumber;
	}

	/**
	 * Returns the party the vote is for.
	 * @return the party the vote is for.
	 */
	public Party getParty()
	{
		return party;
	}

	/**
	 * Returns the vote's choice number for this party.
	 * @return the vote's choice number for this party.
	 */
	public int getChoiceNumber()
	{
		return choiceNumber;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof Vote))
			return false;

		return choiceNumber == ((Vote)obj).choiceNumber && party.equals(((Vote)obj).party);
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 53 * hash + Objects.hashCode(this.party);
		hash = 53 * hash + this.choiceNumber;
		return hash;
	}
}
