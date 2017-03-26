# hyperqueue-task
One can start the Server by using the gradle command "gradlew appRun".
The application will start on port 4430 with SSL, using the plugin created keystore which is self signed by default can modify the gretty properties to include our own keystore.

The base url would be https://localhost:4430/ which would open the index.html

To access the broker as a producer, use POST method with url as https://localhost:4430/broker/<topicName>

To access the broker as a consumer, use GET method with url as https://localhost:4430/broker/<topicName>.
When the url with topic name is access with topic name the user will get the first message from the topic along with a "SessionID" added to the header.
If the user sends the "SessionID" he will get the next message, if present in the broker for the topic otherwise it will wait and try again once, if not found will return null.
