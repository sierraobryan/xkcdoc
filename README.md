# xkcdoc

- general Clean up 
  - Stop passing around comic tag, use favorite comic for everything, do some renaming because the name sucks, a lot of similar code between those lists and adpapers so combine some of that, 
- app bar things, need some title, maybe a back button, maybe an icon
- Add search bar to the browse fragment to search for tags 
- save favorites locally (doneish - not tested : feature/save_favorites)
- Use PageViewer (2!) in single comic view - now sure how well this will work with fast swiping since it has to make a service call each time
- Add error screens (ie, no favorites images) 
- Add loading screen for images 
