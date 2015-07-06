package com.lr.util;

import java.util.UUID;

// Run it locally to generate the random api key
public class ApiKeyGenerator {	
	public static void main(String args[]) {
		final String apiKey;
		apiKey = UUID.randomUUID().toString();
		System.out.println("Api key :" + apiKey);		
	}
}


