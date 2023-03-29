# People application

This is application created during Java course using Spring Framework.

In other repositories I use this application as a base for writing automated tests.

Repositories with tests to this app:

1. UI tests with Selenium - https://github.com/alazarska/people-application-ui-tests
2. RestApi tests with HttpClient - https://github.com/alazarska/people-application-api-tests

## Configuration

| Property       | Description                                                                                                                                           |
|----------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| storage.folder | This is path to directory, where people's avatars will be stored. <br/> Default image named "default-avatar.jpg" must be provided in the selected folder. |

Property can be set through environment variables, where property name is written with upper case separated by underscore (`STORAGE_FOLDER`).