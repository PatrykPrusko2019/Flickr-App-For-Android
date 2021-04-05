# Flickr-App-For-Android
An application that uses the flickr.com portal to download photos and data from it, and the ability to change the search word.
 
The exact operation of the application:
1. The main activity begins, searches for the last entered word, and displays the results on the screen. It uses an object of the SharedPreferences type, which saves the searched word, and after turning off and restarting the application, restores the previous search word.

2. The data displayed on the main activity are:
- thumbnail (urlImage)
- title
which are downloaded as follows:
Then, when the resultSearch value has a word, it uses the GetFlickrJsonData class object, in which a new URI + parameters are created. Then, in the GetRawData class object, we retrieve new data from the URL, which is then retrieved in the GetFlickrJsonData class using JSONArray, creating records of the Photo class and saving it in the photoList list.

3. Then, when the list with data is created, load the RecyclerView object, set up the manager layout, create the adapter and link with RecyclerView. RecyclerView is responsible for displaying the list of items from photoList.

4. In the FlickrRecyclerViewAdapter, you create a new list item view, then create a ViewHolder object that finds and stores view references -> thumbnail and title. Loads data (photoList) from the specified position into views whose references are stored in the given view holder (the FlickrImageViewHolder object).

5. The RecyclerItemClickListener class creates a listener that uses the GestureDetector object, thanks to which it supports gesture detection:
- single click
- longer click

6. Summary of operation:
 - If the user makes the gesture of a long click on a record from the photoList, then he goes to the new PhotoDetailActivity, thanks to which detailed information about the given photo record is displayed:
- author
- title
- photoLarge
- tag

- You can enter the button search button in the toolbar, which goes to the SearchActivity search activity, where you can enter a new word that will be searched. If there is too much words, e.g. android, green, cars, Poland, boys, women, white, nothing will be found, it will only show an empty photo. If it finds new objects, it will display them in the main activity.

7. An additional option, if the user has not selected anything (in the SearchActivity activity), will be displaying the previous user's selection.

8. Then I used www.materialpalette.com and www.material.io to change the graphics of the application.

9. Adding the UML_project_image folder with the project photo in UML.
