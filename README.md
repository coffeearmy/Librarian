Librarian
=========

Android App for listing your ePubs file in dropbox


**1. Interfaces**

- We use Fragments
- Login View 

The view was done before adding the Dropbox API SDK. The dropbox SDK already provides a login, so this view is unused. With enough time, i will see how to login without SDKs and using this interface. But In my opinion if the user have to give their user and pw of dropbox they will trust more the SDK login.

- List view  

For load items in the list, we use an AsyncLoader, and for display items a custom adapter. For now the data is shown in a list, with enough time I will see a custom gridview adapter.

- Item View ( ePub item view) 

For each ePub we show an icon, the ePub title, and the data the user added this ePub in dropbox.

- Grid View

Once I check the app works property we changed the list view for a gridview. Like the android grid view had a bug when scrolling, we finally changed with StaggeredGrid (https://github.com/etsy/AndroidStaggeredGrid). For now this grid works well.

- Prompt Login

Finally we change all the Dropbox logic to another fragment and added the possibility to prompt the Dropbox SDK login. We also store the credentials and keep login in Dropbox, with this the user doesn't have to login every time the app is open.
- Menu 

- Navigation through fragments

When the login is performed successfully the ePub list is show automatically.



**2. Communication**
- Login communication

Dropbox SDK takes care of the login with Oauth2. In the app we store the keys. 
- Retrieve ePubs metadata

The first approach were try the metadata method in the API, but although we made a  search recursive function for navigate between folders, it didn't work. In the second approach we used the search method with the query .ePub, and worked. 
- Download ePub

We used a AsyncTask for download the first time the ePub, once is downloaded the adapter doesn't downloader anymore.
   - Read ePub for title and cover image

We are going to use epublib(http://www.siegmann.nl/epublib/android)  to read metadata and get the cover image. It takes a lot of time download a huge ePub, thats why we use a progress indicator in each list row.
- Doble click

For show the cover when the user double click in a book, we used a GestureDetector.SimpleOnGestureListener, and show the image in a custom dialog with only the cover.
- Order the grid/list

For order the items we provided comparators, one for the title and one for the date. If the ePub is already downloading we use the filename instead of the book title.

