package Security;

import org.springframework.samples.petclinic.user.User;

public class MyUserPrincipal implements UserDetails {

	private User user;
	
	public MyUserPrincipal(User user)
	{
		this.user = user;
	}
	
	
	
	
}
