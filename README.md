## Setting up single sign-on with JWT (JSON web token)

[EngageBay](https://www.engagebay.com)  is an affordable all-in-one marketing, sales and service software for growing companies to engage web visitors and convert to happy customers.

 ### SSO introduction : 

Single sign-on is a mechanism that allows you to authenticate users in your systems and subsequently tell EngageBay that the user has been authenticated. 
The user is then allowed to access EngageBay without being prompted to enter separate login credentials. 

At the core of single sign-on is a security mechanism that allows EngageBay to trust the login requests it gets from your systems. EngageBay only grants access to the users that have been authenticated by you. 
EngageBay SSO relies on a technology called JSON web token (JWT) for securing the exchange of user authentication data.

### JWT implementation code examples : 

The files in this repository are examples and not guaranteed to run or be correct. They should explain you how you can make EngageBay SSO work with JWT from your stack.

### The single sign-on authentication process : 

Once you enable single sign-on, login requests are routed to a remote login URL (a login page that is external to your EngageBay).

Here are the steps of the single sign-on authentication process:

1. An unauthenticated user (not already logged in) navigates to your EngageBay URL (for example, https:// mycompany.engagebay.com/).

2. The EngageBay SSO mechanism recognizes that SSO is enabled and that the user is not authenticated.

3. The user is redirected to the remote login URL configured for the SSO settings (for example, https:// mycompany.com/engagebay/ssologin).

4. A script on your side authenticates the user using your proprietary login process.

5. Your script builds a JWT request that contains the relevant user data.

6. You redirect the customer to the EngageBay endpoint at https:// mycompany.engagebay.com/access/ssologin with the JWT payload.

7. EngageBay parses the user detail from the JWT payload and then grants the user a session.

As you can see, this process relies on browser redirects and passing signed messages using JWT. The redirects happen entirely in the browser and there is no direct connection between EngageBay and your systems, so you can keep your authentication scripts safely behind your corporate firewall.

### Configuring your JWT implementation : 

To perform SSO for a user, you need to send several required user attributes to EngageBay as a base64-encoded hash (hash table, dictionary). Most importantly, EngageBay requires an email address to uniquely identify the user. Beyond the required attributes, which are shown in the table below, you may optionally send additional user profile data. This data is synced between your user management system and your EngageBay.

The JWT payload must be sent to your EngageBay domain using the https protocol. Example: https://mycompany.engagebay.com/ssologin/jwt?jwt={payload}

#### Table 1. Supported attributes

|Attribute|Mandatory|Description|
|:----|:----------|:----------|
|email|yes|Email of the user being signed in, used to uniquely identify the user record in EngageBay.|

### Error handling : 

1. If EngageBay encounters an error while processing a JWT login request, it will report a message that explains what the issue is at the page https://mycompany.engagebay.com/login. Either you can login from here or correct the payload and try again SSO.

#### Enabling JWT single sign-on in your EngageBay : 

1. Click on Admin Settings Preferences tabs.
2. Click on Single Sign-On tab
3. Enter Remote Login URL, the url where EngageBay SSO will redirect once SSO enable.
4. Submit form and hence SSO is enable.
5. You can desable SSO by deleting above configuration.


### Important information : 

https://mycompany.engagebay.com/login is the alternative URL to login to EngageBay in case SSO settings is not working from user end.