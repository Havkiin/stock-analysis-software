<<<<<<< HEAD
/**User class done by Clement Hennebelle.
 * 
 */
=======
/**
 * Created by C. Hennebelle.
 * Stores the user information such as usernames and passwords that have been created by previous users.
 * Last update: November 4, 2016.
 */

>>>>>>> 8bef7debad0f51d0a1b74712a3869c60b4e1fcb1
public class User {
	
	private String login;
	private String password;
	
	//In case someone tries to call it.
	public User(){}
	
	//Constructor takes in the user name and password.
	public User (String log, String pass) {
		
		login = log;
		password = pass;
	}
	
	//Checks if two users are the same.
	public boolean equals(User user) {
		
		return (this.login.equals(user.login) && this.password.equals(user.password));
	}
	
	//Getters to access user login and password.
	public String getLogin(){
		return login;
	}
	
	public String getPassword(){
		return password;
	}
	
	//String of user's login information.
	public String toString () {
		
		return "Login: " + login + ", Password: " + password;
	}
}
