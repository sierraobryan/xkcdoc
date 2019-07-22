# xkcdoc

- general Clean up 
  - <s>Stop passing around comic tag</s>, <s>use favorite comic for everything</s>, do some renaming because the name sucks, a lot of similar code between those lists and adpapers so combine some of that 
- <b>ignore case on tag</b> 
- <s>app bar things, need some title,</s> maybe a back button, <s>maybe an icon</s>
- <b>Add search bar to the browse fragment to search for tags </b>
- clear history 
- <s>save favorites locally</s>
- Use PageViewer (2!) in single comic view - now sure how well this will work with fast swiping since it has to make a service call each time
- Could be fun (and prettier) to do a grid recyclerview layout instead of linear (would need to add the link to the ComicShort and Comic Tag objects) 
- History should be ordered by date
- Maybe need to refactor with ComicShort, ComicShortWithFavorite, and ComicShortWithTag
- Might have gone a little heavy handed with the observing 
- <s>Add error screens (ie, no favorites images)</s>
- Add loading screen for images 
