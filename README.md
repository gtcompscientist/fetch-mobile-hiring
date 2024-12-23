# fetch-mobile-hiring
Fetch Rewards Coding Exercise - Software Engineering - Mobile

Build and run details will be added here, along with ongoing decisions and development notes.

## Build and Run
- Clone the repository
- Open the project in Android Studio
- Run the project on an emulator or device
- The app will open to the main screen, which will display a list of items from the API.
- Click the refresh button or swipe down to refresh the list.
  + The refresh button will always try to download from the API
  + Swipe to refresh will only refresh from the API if the last refresh was more than 5 minutes ago. (See: [FetchHiringApiImpl](src/main/java/co/csadev/fetchhiring/networking/FetchHiringApiImpl.kt))
- Click the Display Type button to switch among:
  + List: A simple list of items
  + Grid: A grid of items, sorted and separated by group.
  + Tabs: Each group will be displayed in a separate tab.
- In the list and grid format, the group header is clickable to collapse or expand that set of items.
- Filtering is not implemented in this version.


## Development Notes
- The [Item] object returned by the API will be used as-is in an immutable data class.
- ~~The search stack will also be used for local search, filter, etc. This will allow for a more consistent and predictable user experience.~~
- ~~Need to determine how the search function will affect the tabbed view. Will it reset the view, or will it remain on the current tab, just filtered? (And what if the filter returns nothing for that group?)~~

## Decision Registry
- **Language**: Kotlin
- **Architecture**: MVVM
- **Networking**: Retrofit
- **UI**: Jetpack Compose
- Search will be case-insensitive.