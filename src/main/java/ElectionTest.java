public class ElectionTest
{
	/** The number of voters to randomize */
	private static final int NUM_VOTERS = 1_000_000;
	/** The parties participating in the election */
	private static final Party[] parties = { new Party("L-L", 25, 25, 50),
	                                         new Party("L-R", 25, 75, 50),
	                                         new Party("R-L", 75, 25, 50),
	                                         new Party("R-R", 75, 75, 50)};

	public static void main(String[] args)
	{
		//create a new election
		Election election = new Election();

		//add the desired amount of randomly generated voters
		for(int i=0; i<NUM_VOTERS; i++)
			election.addVoter(VoterFactory.randomVoter());
		//add the desired parties
		election.addParties(parties);

		//run the election and print its results
		election.run();
		System.out.println(election.toString());
	}
}