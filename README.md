# Televisioner
##### Cesar Cano de Oliveira

Televisioner is an app developed for the Jobsity Android Challenge

The universal apk is located at the .distribution folder, located at the root of the project.

## Implemented features
I will briefly comment on and describe my thought process while implementing each of one of them.
### Mandatory features
- List all of the series contained in the API used by the paging scheme provided by the
API.
- Allow users to search series by name.
- The listing and search views must show at least the name and poster image of the
series.
- After clicking on a series, the application should show the details of the series, showing
the following information:
    - Name
    - Poster
    - Days and time during which the series airs
    - Genres
    - Summary
    - List of episodes separated by season
- After clicking on an episode, the application should show the episode’s information, including:
    - Name
    - Number
    - Season
    - Summary
    - Image, if there is one

### Bonus feature implemented

##### Allow the user to save a series as a favorite.
The user can save a series as favorite by tapping the star button inside the series details screen
#### Allow the user to delete a series from the favorites list.
The user can remove the series from their favorite list by tapping the star button again, 'toggling the star off'
#### Allow the user to browse their favorite series in alphabetical order, and click on one to see its details.
The user can navigate to the favorite series tab in the bottom navigation bar and check their favorite series in alphabetical order, if any series is tapped, the user is taken to its detail page

#### Observations
I thought the favorite series 'package' would be a good feature to implement, since I could use Room as a local persistence database in order to simplify how the series are stored, avoiding complicated combinations of saving ids/multiple requests to the API

I wanted to implement the PIN/biometrics authentication feature as well but I didn't have the time :(

#### Architecture

I've decided to use MVVM with LiveData and sealed classes for the UI states.

I've also chose to implement a somewhat clean architecture, even though I didn't defined a lot of interfaces, because it was probably overengineering for this kind of project, since it almost have no business rules between the layers, but you can see that it made it a lot easier to test and to deal with errors in the most suitable place (for example, dealing with http errors in the datasource). It also helped converting from the Dao 'FavoriteSeries' model to the Domain Series model between the datasource and all the other layers, this way only FavoriteSeriesDataSource needs to know convert FavoriteSeries to Series, but I could use an Adapter between those two as well.

I didn't went full clean architecture because I think most of the times it is easy to overengineer when using this approach, I think it is about the balance between separating your layers properly and understanding the pros and cons of how much are you layering and abstracting.

#### UI
##### Improvements

#### Other

##### Git/CI
Since I was working by myself and I didn't have much time, I started the repository but didn't follow good commit/PR practices, but I enjoy having small-medium PRs with context and commits following the Karma commit style (http://karma-runner.github.io/6.3/dev/git-commit-msg.html) or similar, with a trunk based development even though I have no problems with git flow. I've used GitHub private repo and and for the CI I've decided to use Bitrise just to demonstrante a simple continuous integration pipeline with a trigger for PRs merging into main where the app is built and the linter and unit tests run. I've also configured a git hook for the repo to avoid commits that do not follow the style.


