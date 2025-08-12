# 🚨 Sahajjo - Emergency Reporting App

<div align="center">
  <img src="app/src/main/res/drawable/logo.png" alt="Sahajjo Logo" width="150" height="150"/>
  
  [![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://android.com)
  [![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white)](https://firebase.google.com)
  [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
  
  *An intuitive emergency reporting application connecting citizens with local authorities*
</div>

## 📖 Overview

**Sahajjo** is a comprehensive emergency reporting Android application designed to bridge the communication gap between citizens and local authorities. The app provides a dual-interface system where citizens can report emergencies and specific problems while administrators can efficiently manage and respond to these reports.

### 🌟 Key Features

- **🔐 Dual Authentication System**: Separate registration and login for citizens and administrators
- **🚨 Emergency Reporting**: Quick emergency alert system for urgent situations
- **📝 Detailed Problem Reporting**: Citizens can submit specific problems with detailed descriptions
- **👨‍💼 Admin Dashboard**: Real-time problem management interface for authorities
- **📱 User-Friendly Interface**: Intuitive design with custom UI components
- **🔥 Real-time Sync**: Firebase integration for instant data synchronization
- **👤 Profile Management**: Comprehensive user profile system with navigation drawer

## 🏗️ Architecture

### Technology Stack

- **Frontend**: Android (Java)
- **Backend**: Firebase Firestore
- **Authentication**: Firebase Authentication
- **UI Framework**: Material Design Components
- **Architecture Pattern**: MVVM with Firebase integration

### Project Structure

```
app/
├── src/main/
│   ├── java/com/sahajjoapp/sahajjo/
│   │   ├── MainActivity.java                    # Entry point with role selection
│   │   ├── splash_screen_activity.java         # Splash screen with auto-login
│   │   ├── Login_page_activity.java            # Login functionality
│   │   ├── person_register_activity.java       # Citizen registration
│   │   ├── Admin_Register_Activity.java        # Admin registration
│   │   ├── Person_Main_Interface_Activity.java # Citizen dashboard
│   │   ├── Admin_main_Interface_Activity.java  # Admin dashboard
│   │   ├── specific_problem_activity.java      # Problem reporting
│   │   ├── Problem.java                        # Problem data model
│   │   └── problemAdapter.java                 # RecyclerView adapter
│   ├── res/
│   │   ├── layout/                              # UI layouts
│   │   ├── drawable/                            # Images and custom drawables
│   │   └── values/                              # Strings, colors, styles
│   └── AndroidManifest.xml
```

## 🚀 Features Deep Dive

### For Citizens 👥

#### 1. **Registration & Authentication**

- Secure email/password registration
- Profile creation with personal details
- Gender selection and location tracking
- Automatic role assignment as "Normal" user

#### 2. **Emergency Reporting**

- Quick emergency button for urgent situations
- Immediate alert system to authorities

#### 3. **Specific Problem Reporting**

- Detailed problem submission form
- Fields: Title, Description, Location
- Real-time submission to Firebase
- Problem tracking and management

#### 4. **Profile Management**

- Navigation drawer with user information
- Display of name, email, mobile, and location
- Logout functionality

### For Administrators 👨‍💼

#### 1. **Admin Registration**

- Enhanced registration with office credentials
- NID number verification
- Office ID and location tracking
- Automatic role assignment as "Admin"

#### 2. **Problem Management Dashboard**

- Real-time problem feed using RecyclerView
- Live updates from Firebase Firestore
- Problem details with citizen information
- Efficient problem resolution workflow

#### 3. **Enhanced Profile System**

- Complete admin information display
- Office location and credentials
- Administrative role indicators

## 📱 Screenshots

_Add your app screenshots here to showcase the UI_

## 🛠️ Installation & Setup

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK (minimum API level 24)
- Firebase project setup
- Java Development Kit (JDK) 8 or later

### Setup Instructions

1. **Clone the Repository**

   ```bash
   git clone https://github.com/munnaa0/Sahajjo--An-Emergency-Reporting-App.git
   cd Sahajjo--An-Emergency-Reporting-App
   ```

2. **Firebase Configuration**

   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com)
   - Add your Android app to Firebase
   - Download `google-services.json` and place it in `app/` directory
   - Enable Authentication and Firestore Database

3. **Open in Android Studio**

   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

4. **Sync Project**

   - Let Gradle sync the project dependencies
   - Ensure all dependencies are properly downloaded

5. **Run the Application**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio

## 🔧 Configuration

### Firebase Setup

1. **Authentication**

   - Enable Email/Password authentication
   - Configure sign-in methods

2. **Firestore Database**

   - Create collections: `users`, `problems`
   - Set appropriate security rules

3. **Database Structure**

   ```
   users/
   ├── {userId}/
   │   ├── username: String
   │   ├── email: String
   │   ├── mobile: String
   │   ├── location: String
   │   ├── role: "Normal" | "Admin"
   │   └── (admin fields: office details, NID)

   problems/
   ├── {problemId}/
   │   ├── problemTitle: String
   │   ├── problemDescription: String
   │   ├── problemLocation: String
   │   └── userId: String
   ```

## 🎨 UI/UX Design

### Design Philosophy

- **Material Design**: Following Google's Material Design guidelines
- **Accessibility**: User-friendly interface for all age groups
- **Responsiveness**: Optimized for various screen sizes
- **Custom Components**: Tailored UI elements for better user experience

### Color Scheme

- Primary colors inspired by emergency services
- High contrast for better visibility
- Consistent color palette throughout the app

## 🔒 Security Features

- **Firebase Authentication**: Secure user authentication
- **Role-based Access**: Different access levels for citizens and admins
- **Data Validation**: Input validation on both client and server side
- **Secure Communication**: HTTPS communication with Firebase

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java coding conventions
- Write meaningful commit messages
- Test your changes thoroughly
- Update documentation as needed

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Munna Ahmed**

- GitHub: [@munnaa0](https://github.com/munnaa0)
- Email: [Your Email]

## 🙏 Acknowledgments

- Firebase team for excellent backend services
- Material Design team for UI guidelines
- Android development community for resources and support

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/munnaa0/Sahajjo--An-Emergency-Reporting-App/issues) section
2. Create a new issue if your problem isn't already reported
3. Contact the maintainer directly

## 🗺️ Roadmap

### Upcoming Features

- [ ] Real-time location tracking
- [ ] Push notifications for emergency alerts
- [ ] Image upload for problem reports
- [ ] Admin analytics dashboard
- [ ] Multi-language support
- [ ] Dark mode theme
- [ ] Emergency contact integration

---

<div align="center">
  <p><strong>Made with ❤️ for community safety</strong></p>
  <p>Star ⭐ this repo if you find it helpful!</p>
</div>
