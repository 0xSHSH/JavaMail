<div align="center">

# ✉️ JavaMail Application

**A full-stack email web app built with Java Servlets, JSP, JDBC and MySQL**

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Tomcat](https://img.shields.io/badge/Tomcat-10.x-yellow?style=flat-square&logo=apachetomcat)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

</div>

---

## 📸 Features at a Glance

| 📥 Inbox | 📝 Compose | 👤 Profile |
|----------|-----------|-----------|
| Unread count badge | CC / BCC support | Edit username, DOB, contact |
| Star / Important toggle | Reply & Forward | Change password securely |
| Bulk select & delete | Draft autosave (Ctrl+S) | Session-based auth |
| Search by subject/body/sender | Recipient validation | SHA-256 password hashing |

---

## 🗂️ Project Structure

```
JavaMailProject/
├── pom.xml                          ← Maven build file
├── sql/
│   └── schema.sql                   ← Database schema + sample data
└── src/main/
    ├── java/com/javamail/
    │   ├── model/
    │   │   ├── User.java
    │   │   ├── Mail.java
    │   │   └── Attachment.java
    │   ├── dao/
    │   │   ├── UserDAO.java          ← register, login, profile, password
    │   │   └── MailDAO.java          ← send, draft, inbox, trash, search
    │   ├── servlet/
    │   │   ├── UserServlet.java      ← /user/* routes
    │   │   └── MailServlet.java      ← /mail/* routes
    │   └── util/
    │       ├── DBConnection.java     ← JDBC connection
    │       └── PasswordUtil.java     ← SHA-256 hashing
    └── webapp/
        ├── index.jsp                 ← Root redirect
        ├── login.jsp
        ├── register.jsp
        ├── css/style.css             ← Dark theme stylesheet
        ├── js/app.js                 ← Toast notifications, AJAX
        └── WEB-INF/
            ├── web.xml
            ├── mailbox.jsp           ← Inbox / folder view
            ├── viewmail.jsp          ← Single mail view
            ├── compose.jsp           ← Compose / Reply / Forward
            ├── sidebar.jsp           ← Shared sidebar
            ├── profile.jsp
            ├── error404.jsp
            └── error500.jsp
```

---

## ⚙️ Prerequisites

Make sure you have all of these installed before starting:

| Tool | Version | Download |
|------|---------|----------|
| JDK | 17+ | https://adoptium.net |
| Apache Maven | 3.8+ | https://maven.apache.org/download.cgi |
| MySQL | 8.0+ | https://dev.mysql.com/downloads/mysql |
| Apache Tomcat | 10.x | https://tomcat.apache.org/download-10.cgi |

> ⚠️ **Tomcat 10.x is required** — Tomcat 9 or below will NOT work because this project uses Jakarta EE 6.

---

## 🚀 Setup & Run (Step by Step)

### Step 1 — Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/JavaMail-Application.git
cd JavaMail-Application
```

### Step 2 — Create the database

Open your MySQL terminal and run:

```bash
# Mac / Linux
mysql -u root -p < sql/schema.sql

# Windows CMD
mysql -u root -p < sql\schema.sql
```

This will:
- Create the `javamail_db` database
- Create all 6 tables (users, mails, labels, mail_labels, attachments, contacts)
- Insert 3 sample users with test emails

### Step 3 — Configure your database password

Open this file:
```
src/main/java/com/javamail/util/DBConnection.java
```

Update line 10:
```java
private static final String PASSWORD = "root"; // ← change to your MySQL password
```

### Step 4 — Build the project

```bash
mvn clean package
```

You should see:
```
BUILD SUCCESS
target/JavaMailApp.war created
```

### Step 5 — Deploy to Tomcat

**Mac / Linux:**
```bash
cp target/JavaMailApp.war /path/to/tomcat/webapps/
```

**Windows:**
```cmd
copy target\JavaMailApp.war C:\path\to\tomcat\webapps\
```

### Step 6 — Start Tomcat

**Mac / Linux:**
```bash
/path/to/tomcat/bin/startup.sh
```

**Windows:**
```cmd
C:\path\to\tomcat\bin\startup.bat
```

### Step 7 — Open in your browser

```
http://localhost:8080/JavaMailApp
```

---

## 🧪 Test Accounts

These accounts are inserted automatically by `schema.sql`:

| Email | Password | Role |
|-------|----------|------|
| admin@javamail.com | admin123 | Admin |
| alice@javamail.com | alice123 | User |
| bob@javamail.com | bob123 | User |

> 💡 **Quick test:** Log in as `admin@javamail.com` — you'll already have 2 unread mails from Alice and Bob.

---

## 📬 What You Can Test

Once logged in, try these:

- **Send a mail** → Compose → enter `alice@javamail.com` as recipient → Send
- **Reply** → Open any mail → click Reply
- **Star a mail** → Click the ⭐ icon in the inbox
- **Save a draft** → Start composing → press `Ctrl+S`
- **Search** → Type any word in the search bar
- **Move to trash** → Select a mail → Delete
- **Restore from trash** → Go to Trash → Restore
- **Change password** → Profile → Change Password tab

---

## 🗄️ Database Schema

```sql
users        (id, username, email, password[SHA2], dob, contact, is_active, created_at)
mails        (id, from_email, to_email, cc, bcc, subject, body, status, is_read, is_starred, is_important, sent_at)
labels       (id, user_email, label_name, color)
mail_labels  (mail_id ↔ label_id)
attachments  (id, mail_id, file_name, file_path, file_size, mime_type)
contacts     (id, owner_email, contact_name, contact_email)
```

---

## 🔒 Security

- Passwords stored as **SHA-256 hashes** via MySQL `SHA2()` — never plain text
- All SQL queries use **PreparedStatements** — fully protected against SQL injection
- JSP files inside `WEB-INF/` are not directly accessible via URL
- Sessions validated on every protected route with 30-minute timeout

---

## 🐛 Troubleshooting

| Problem | Fix |
|---------|-----|
| `404` on all pages | Make sure you're using **Tomcat 10.x**, not Tomcat 9 |
| `Communications link failure` | MySQL is not running — start it first |
| `Access denied for user root` | Wrong password in `DBConnection.java` |
| `BUILD FAILURE` | Make sure JDK 17+ is installed and `JAVA_HOME` is set |
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Run `mvn clean package` again to download dependencies |
| Page loads but login fails | Run `schema.sql` again to make sure sample users exist |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Jakarta Servlets, JDBC |
| Frontend | JSP, JSTL, HTML5, CSS3, JavaScript |
| Database | MySQL 8.0 |
| Server | Apache Tomcat 10.x |
| Build | Apache Maven 3.8+ |
| Security | SHA-256 (MySQL SHA2), PreparedStatements |

---

## 📄 License

MIT License — free to use, modify and distribute.
