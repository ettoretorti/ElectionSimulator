/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ettore
 */
class IncompleteElectionException extends RuntimeException
{

	public IncompleteElectionException()
	{
		super();
	}
	
	public IncompleteElectionException(String message)
	{
		super(message);
	}
	
	public IncompleteElectionException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public IncompleteElectionException(Throwable cause)
	{
		super(cause);
	}
	
}
