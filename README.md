Librarian
=========

Android App for listing your pubs file in dropbox

Steps: 
1. Interfaces

	..* Using Fragments
	..* Login View 
		... The view was done before adding the Dropbox API SDK. The dropbox SDK already provides a login, so this view is unused. If I have enough time, i will see how to login without SDKs and using this interface. But In my opinion if the user have to give their user and pw of dropbox they will trust more the SDK login.
	..* List view  
		... For load items in the list, we use an AsyncLoader, and for display items a custom adapter. For now the data is shown in a list, with enough time I will see a custom gridview adapter.
	..* Item View ( ePub item view) 
		... For each ePub we show an icon, the ePub title, and the data the user added this ePub in dropbox.
	..* Menu 
	..* Navigation through fragments
		...When the login is performed successfully the ePub list is show automatically.


2. Communication
	..* Login communication
		Success: go to ePub list
		Failure: show error dialog
		Store User and PW ?
	..* Retrieve ePubs
	...	The first approach were try the metadata method in the API, but although we made a  search recursive function for navigate between folders, it didn't work. In the second approach we used the search method with the query .ePub, and worked. But this approach will
    ..* Read ePub for title and cover image
	...	We are going to use epublib(http://www.siegmann.nl/epublib/android)  to read metadata and get the cover image. It takes a lot of time download a huge epub, thats why we use a progress indicator in each list row.
	..* Doble click
