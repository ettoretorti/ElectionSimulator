import java.util.Random;

public class ElectionTest
{
	public static void main(String[] args)
	{
		Voter[] vArray = new Voter[100000]; 
    	
    	createRandomVoters(vArray);


		Party[] parties = { new Party("L-L", 25, 25, 50),
		                    new Party("L-R", 25, 75, 50),
		                    new Party("R-L", 75, 25, 50),
		                    new Party("R-R", 75, 75, 50)};

		Election election = new PRElection();

		for(Voter v : vArray)
			election.addVoter(v);
		for(Party p : parties)
			election.addParty(p);

		election.runElection();

		System.out.println(election.results());
	}

	private static void createRandomVoters(Voter[] voters)
	{
		for(int i=0; i<voters.length; i++)
		{
			int socPref  = (int) (Math.random() * 101);
			int econPref = (int) (Math.random() * 101);
			int prefRatio = discreteNormal(50, 16, 0, 100);
			int compRatio = discreteNormal(32, 16, 0, 100);

			voters[i] = new Voter(socPref, econPref, prefRatio, compRatio);
		}
	}

	private static int generateSocPref()
	{
		return 0;
	}

	private static double normalDistribution(double mean, double dev)
	{
		Random generator = new Random();

		return dev * generator.nextGaussian() + mean;
	}

	//bounds are inclusive
	private static int discreteNormal(double mean, double dev, int lowerBound, int upperBound)
	{
		double result = normalDistribution(mean, dev);

		if(result < lowerBound) result = lowerBound;
		if(result > upperBound) result = upperBound;

		return (int) Math.round(result);
	}
}