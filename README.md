# dicksSporting

Although i created this repository on 12th Jan, I mostly worked on this on 14th Jan.

Make sure you have google play services 11.0.4 version in your local sdk folder, since app level build.gradle is using location services from play services. Or else change version to match your local version.
Except that you probably don't need any extra set up. I included everything else in this repo.

To show the photos i used grid view and used picasso api to load the picures into each imageview.

I couldn't implement the favorites venue due to time contrain. 
And there are some areas where i can improve my code like sorting venues in a better way without using too many variables but at this point i concentrated on timeline.
I didn't use dependency injection before so didn't implement here. but i'm open to learn that.

Most challenging part for me was sorting venues w.r.t the distance from user and passing the right information to details activity when clicked on a venue name.
Implemented on click listeners wherever it is needed in details activity and used intents to properly route them to right resolving app.

I might have missed few nullpointer exceptions.
And should have used layout-land folder to load a different view in landscape.
Sorry for not putting comments in the code.
Most of them i couldn't complete are due to time constrain.

Either way, It's a great experience to build an app like this in this short period of time.
