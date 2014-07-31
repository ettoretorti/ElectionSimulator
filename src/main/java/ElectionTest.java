public class ElectionTest
{
	/** The number of voters to randomize */
	private static final int NUM_VOTERS = 100_000;
	/** The party's participating in the election */
	private static final Party[] parties = { new Party("L-L", 25, 25, 50),
	                                         new Party("L-R", 25, 75, 50),
	                                         new Party("R-L", 75, 25, 50),
	                                         new Party("R-R", 75, 75, 50)};
	
	public static void main(String[] args)
	{
		Voter[] vArray = new Voter[NUM_VOTERS]; 
    	VoterFactory.fillVoterArray(vArray);

		Election election = new Election();

		for(Voter v : vArray)
			election.addVoter(v);
		for(Party p : parties)
			election.addParty(p);

		election.runElection();

		System.out.println(election.results());
	}
}