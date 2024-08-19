# BugIt Android

BugIt Android is a bug-tracking application developed for Android using modern Android development practices. This app allows users to capture and report bugs, including uploading images to a third-party service (Imgur) and creating bug reports in Notion.

## Features

- **Jetpack Compose**: UI development using Jetpack Compose.
- **Dependency Injection**: Managed using Hilt.
- **Reactive Programming**: Leveraging Kotlin Flows and Coroutines for asynchronous operations.
- **Networking**: API communication using Retrofit and OkHttp.
- **Local Data Storage**: Implemented with Room.
- **Navigation**: Managed with Navigation Compose.
- ** Clean architecture and MVI pattern.

## How It Works

1. **Upload Bug Images**: The app allows users to upload images of bugs to [Imgur](https://imgur.com/) via their API and retrieves the image URL.
2. **Report Bugs in Notion**: The app creates new bug reports in a Notion database. The report includes a description, the date, and the uploaded image URL.
3. **Local Bug Storage**: All reported bugs are also saved locally in a Room database and displayed on the home screen.

## Prerequisites

### Imgur Setup

1. **Create an Imgur Account**: Sign up at [Imgur](https://imgur.com/).
2. **Get the Client ID**:
   - Navigate to the [Imgur API documentation](https://apidocs.imgur.com/) and create a new application to obtain your `client_id`.
3. **Use the Imgur Upload API**: The app uses the `/upload` endpoint to upload images.

### Notion Setup

1. **Create a Notion Integration**:
   - Follow the [Notion API documentation](https://developers.notion.com/docs/getting-started) to create a new integration.
2. **Create a Notion Database**:
   - Add the following columns to your database: `Description`, `Date`, and `ImageURL`.
3. **Link the Database to Your Integration**:
   - Obtain the `secret key` and `database ID` required for API calls to create pages.

## Storing API Keys

Create a `keys.properties` file in the root directory of your project with the following content:

```properties
NOTION_SECRET_KEY="your_notion_secret_key"
NOTION_DATABASE_ID="your_notion_database_id"
IMGUR_CLIENT_ID="your_imgur_client_id"
```

## Building and Running the Project

1. Clone the repository.
2. Add the keys.properties file as described above.
3. Sync the project with Gradle.
4. Build and run the application on your Android device or emulator.

