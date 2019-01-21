/*
  This example depends on the following jar files
  commons-codec.jar from http://commons.apache.org/proper/commons-codec/
  json-smart.jar from https://code.google.com/p/json-smart/
  nimbus-jose-jwt.jar from https://bitbucket.org/connect2id/nimbus-jose-jwt/downloads
  
  Use json-smart with or above 1.0.9v
  Use nimbus-jose-jwt with or above 3.9v
*/


package com.engagebay.sso;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

public class EngagebaySSOServlet extends HttpServlet {

    private static final String SHARED_SECRET_KEY = "6m6uorcegab67r778r72ho6jj4";
    private static final String DOMAIN = "harry";

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws IOException {

	// Given a user instance
	// Compose the JWT claims set
	JWTClaimsSet jwtClaims = new JWTClaimsSet();
	jwtClaims.setCustomClaim("email", "harry@engagebay.com");

	// Create JWS header with HS256 algorithm
	JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
	// header.setContentType("text/plain");

	// Create JWS object
	JWSObject jwsObject = new JWSObject(header, new Payload(
		jwtClaims.toJSONObject()));

	// Create HMAC signer
	JWSSigner signer = new MACSigner(SHARED_SECRET_KEY.getBytes());

	try {
	    jwsObject.sign(signer);
	} catch (com.nimbusds.jose.JOSEException e) {
	    System.err.println("Error signing JWT: " + e.getMessage());
	    return;
	}

	// Serialise to JWT compact form
	String jwtString = jwsObject.serialize();

	String redirectUrl = "https://" + DOMAIN
		+ ".engagebay.com/access/sso?jwt="
		+ jwtString;

	resp.sendRedirect(redirectUrl);
    }
}