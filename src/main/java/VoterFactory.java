
import java.util.Random;

public class VoterFactory
{
	/** The random number generator used to create new voters */
	private static Random randGen = new Random();

	/**
	 * Prevent instances of this class from being created by making the
	 * constructor private
	 */
	private VoterFactory() {}

	/**
	 * Returns a new randomized voter.
	 * @return a new randomized voter.
	 */
	public static Voter randomVoter()
	{
		int socPref  = discreteBivariateNormal(30, 10, 70, 10, 0.5, 0, 100);

		//the amount to shift the mean for economic preference based on social
		//preference
		double shiftValue = (socPref - 50) * 4.0/5;
		int econPref  = discreteNormal(50 + shiftValue, 10, 0, 100);

		int prefRatio = discreteNormal(50, 16, 0, 100);
		int compRatio = discreteNormal(25, 12.5, 0, 100);

		return new Voter(socPref, econPref, prefRatio, compRatio);
	}

	/**
	 * Fills an array with newly created randomized voters.
	 * @param array the array to be filled.
	 */
	public static void fillVoterArray(Voter[] array)
	{
		for(int i=0; i<array.length; i++)
			array[i] = VoterFactory.randomVoter();
	}

	/**
	 * Samples a value from a normal distribution with the given mean and
	 * standard deviation.
	 * @param mean the mean of the normal distribution to be sampled.
	 * @param dev the standard deviation of the normal distribution to be sampled.
	 * @return a normally distributed value with given mean and standard deviation.
	 */
	private static double normalDistribution(double mean, double dev)
	{
		return dev * randGen.nextGaussian() + mean;
	}

	/**
	 * Returns an integer sampled from a normal distribution with the given parameters
	 * and truncates it to a range of values.
	 * @param mean the mean of the normal distribution to be sampled.
	 * @param dev the standard deviation of the normal distribution to be sampled.
	 * @param lowerBound The lowest value that can be returned, all lower values
	 * will return this value.
	 * @param upperBound The highest value that can be returned, all higher
	 * values will return this value.
	 * @return a normally distributed value with given mean and standard deviation.
	 */
	private static int discreteNormal(double mean, double dev,
			                          int lowerBound, int upperBound)
	{
		double result = normalDistribution(mean, dev);

		if(result < lowerBound) result = lowerBound;
		if(result > upperBound) result = upperBound;

		return (int) Math.round(result);
	}

	private static double bivariateNormalDistribution(double mean1,
	                                                  double dev1, double mean2,
	                                                  double dev2, double mixFactor)
	{
		double random = randGen.nextDouble();

		if(random < mixFactor)
			return normalDistribution(mean1, dev1);
		else
			return normalDistribution(mean2, dev2);
	}

	private static int discreteBivariateNormal(double mean1, double dev1,
	                                           double mean2, double dev2,
	                                           double mixFactor, int lowerBound,
	                                           int upperBound)
	{
		double result = bivariateNormalDistribution(mean1, dev1, mean2, dev2, mixFactor);

		if(result < lowerBound) result = lowerBound;
		if(result > upperBound) result = upperBound;

		return (int) Math.round(result);
	}
}
