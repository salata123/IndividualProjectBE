
# Individual project Backend

A flight searching app that is based on Amadeus API.

## Set-up

To be able to use Amadeus API, You need to:
1. Create an account on Amadeus site for Developers: https://developers.amadeus.com/
2. Go to https://developers.amadeus.com/my-apps
3. Click button "Create new app", fill the fields and click "Create" button
4. Open the created app tab, go to App Keys section, copy API Key and API Secret and add them into application.properties file inside my project
5. If You are already in the application.properties file You can also add datasource url, username and password to connect to the database (the same for Frontend application database parameters).

Note: for this project I am using SDK: Eclipse Temurin version 17.0.6
## Visual layer
Run this app to be able to use the visual layer crafted for backend: https://github.com/salata123/IndividualProjectFE

## Running the app

Firstly, run the backend application and then the frontend application.

1. Main Page

The main page is set to http://localhost:8081/flights. From there, you can perform a flight search without adding them to the cart, just for research purposes. Clicking on the Login button would transfer you to a login screen. If you are logged in, you are able to add the searched flights to your cart and also remove them from it. When you are satisfied with your cart status, you can go to the Profile page to complete an order.

2. Login Screen
Here you can create an account or log into an existing one. After filling the fields with correct credentials, you will be transferred back to the Main Page.

3. Profile Page
Here you can see your cart and order list. The "Buy" button will add every flight from the cart and create an order. After that, the cart should be cleared, and you should be able to see a new position in the order list. Click "Search flights" to transfer back to the Main page.

Disclaimer:

- There is still a lot of debug code residuals I left just to be able to check functionalities (i.e., "Is the user logged in?" button that checks if the user still has a valid Login Token).
- A Login Token has a timer to expire in 1 minute from logging in. The method that checks the login expiration doesn't work well when changing the views, probably linked to Vaadin session handling or some new Beans creating along the way.
## Lessons Learned

Some of the most valuable lessons that I've learned throught this project among neverending debugging and trying to connect everything together:
- @Mapper - an annotation that helps to create mappers automatically.
- Singleton pattern - helps optimize application work thanks to instancing. In this example, instead of requesting a new authentication token every time I need to create Flight objects, I could create a new instance for the token and use its instance whenever I would need to.
- A lot of session-related concepts in Vaadin, tree dependencies, beans, etc.
- Also that CSS formatting doesn't work very well with Vaadin

## Plans for the future
- Learn security to operate between sessions, creating passwords and user data, store it, and secure it. (Prioritize Spring Security)
- Learn more about session persistance, view creating and optimization
- Learn how to embed AI assisstance for users
- Last but not least - things that come with experience, so, of course, cleaner code, more useful plugins, and annotations, etc.
## Thank You for checking out my project :)