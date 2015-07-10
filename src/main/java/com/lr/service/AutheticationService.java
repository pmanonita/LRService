package com.lr.service;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.lr.exceptions.AuthException;
import com.lr.key.ApiKey;


public class AutheticationService {
	
	private static  AutheticationService authService = null;
	
	//To-do : Move to db
    private final Map<String, String> usersStorage              = new HashMap();
    private final Map<String, String> serviceKeysStorage        = new HashMap();
    
    //To-do : Store in cache ? Investigate
    private final Map<String, String> authorizationTokensStorage = new HashMap();
 
    private AutheticationService() {
        usersStorage.put( "praja", "praja" );
        usersStorage.put( "admin", "admin" );        
        serviceKeysStorage.put( "824bb1e8-de0c-401c-9f83-8b1d18a0ca9d", "praja" );
        serviceKeysStorage.put( "e94c0841-927c-4985-bdf0-85015ab063ab", "admin" );
    }
 
    public static AutheticationService getInstance() {
        if ( authService == null ) {
        	authService = new AutheticationService();
        } 
        return authService;
    }
 
    //login
    public String login( String serviceKey, String username, String password ) throws AuthException {
        if ( serviceKeysStorage.containsKey( serviceKey ) ) {
            String usernameMatch = serviceKeysStorage.get( serviceKey ); 
            if ( usernameMatch.equals( username ) && usersStorage.containsKey( username ) ) {
                String passwordMatch = usersStorage.get( username ); 
                if ( passwordMatch.equals( password ) ) {                   
                    String authToken = UUID.randomUUID().toString();
                    authorizationTokensStorage.put( authToken, username ); 
                    return authToken;
                }
            }
        }
        
        throw new AuthException("Username and Password is not correct");
    }
 
    /**
     * The method that pre-validates if the client which invokes the REST API is
     * from a authorized and authenticated source.
     *
     * @param serviceKey The service key
     * @param authToken The authorization token generated after login
     * @return TRUE for acceptance and FALSE for denied.
     */
    public boolean isAuthTokenValid( String serviceKey, String authToken ) {
        if ( isServiceKeyValid( serviceKey ) ) {
            String userName = serviceKeysStorage.get( serviceKey ); 
            if ( authorizationTokensStorage.containsKey( authToken ) ) { 
                if ( userName.equals( authorizationTokensStorage.get( authToken ) ) ) {
                    return true;
                }
            }
        }
        
        return false;
    }
 
    /**
     * This method checks is the service key is valid
     *
     * @param serviceKey
     * @return TRUE if service key matches the pre-generated ones in service key
     * storage. FALSE for otherwise.
     */
    public boolean isServiceKeyValid( String serviceKey ) {
        return serviceKeysStorage.containsKey( serviceKey );
    }
 
    //logout
    public void logout( String serviceKey, String authToken ) throws GeneralSecurityException {
        if ( serviceKeysStorage.containsKey( serviceKey ) ) {
            String usernameMatch1 = serviceKeysStorage.get( serviceKey ); 
            if ( authorizationTokensStorage.containsKey( authToken ) ) {
                String usernameMatch2 = authorizationTokensStorage.get( authToken ); 
                if ( usernameMatch1.equals( usernameMatch2 ) ) {                   
                    authorizationTokensStorage.remove( authToken );
                    return;
                }
            }
        }
        
        throw new GeneralSecurityException( "Invalid service key and authorization token match." );
    }

    //Not needed, get it from db once ready and move it to user service
	public String getUserName(String serviceKey) {
		if (null == serviceKey || serviceKey.equals("")) {
			return "";
		}
		
		String userName = serviceKeysStorage.get(serviceKey);
		if (userName != null && !userName.equals("")) {
		    return userName;
		}
		return "";
	}

}
