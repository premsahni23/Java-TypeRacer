# 🧠 Java TypeRacer

A fully functional **Monkeytype-style typing game** built in **JavaFX**, featuring real-time WPM & accuracy tracking, random word generation, color-coded feedback, and a database-powered leaderboard.

---

## 🚀 Features

- 🎯 **Real-Time Typing Metrics**
  - Live WPM (Words Per Minute)
  - Accuracy %
- 🌈 **Color-coded Typing Feedback**
  - Correct letters in green ✅
  - Incorrect letters in red ❌
- ⏱️ **Timer Modes**
  - 15s, 30s, 60s typing challenges
- 🎲 **Random Paragraph Generator**
- 📊 **Leaderboard**
  - Stores `player_name`, `WPM`, `accuracy`, and `play_date`
  - Sorted by highest WPM
- 🔄 **Restart & Game Over Flow**
- 🎨 **Modern UI inspired by [Monkeytype](https://monkeytype.com)**

---

## 🛠️ Technologies Used

- **Java** (JDK 17)
- **JavaFX** for UI
- **MySQL** for storing leaderboard data
- **JDBC** for database connection
- **Maven** for dependency management

---
## 🧰 Built With

- [JavaFX](https://openjfx.io/)
- [MySQL](https://www.mysql.com/)
- [Maven](https://maven.apache.org/)
- [SceneBuilder](https://gluonhq.com/products/scene-builder/) (for UI design)
  
---

## 🗄️ Database Schema

**Database:** `typeracerdb`  
**Table:** `leaderboard`

| Column       | Type         |
|--------------|--------------|
| player_name  | VARCHAR(100) |
| wpm          | INT          |
| accuracy     | DOUBLE       |
| play_date    | DATE         |

---

## ⚙️ Setup Instructions

1. **Clone this repo**:
   ```bash
   git clone https://github.com/premsahni23/Java-TypeRacer.git
2. Import into Eclipse as a Maven project

3. Set up MySQL database:

   - Create database typeracerdb

   - Run the provided leaderboard.sql script (or create the table as shown above)

4. Update DB credentials in the Java code
   - String url = "jdbc:mysql://localhost:3306/typeracerdb";
   - String user = "root";
   - String password = "your_password";
     
5. Run the application! 💻

   👨‍💻 Contributors
Prem Sahni – [GitHub](https://github.com/premsahni23)

Katherin Binu – [GitHub](https://github.com/kathyyyy498)


